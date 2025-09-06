package com.mbbank.biz.pro.web.rest;

import com.mbbank.biz.pro.domain.AppUser;
import com.mbbank.biz.pro.domain.User;
import com.mbbank.biz.pro.repository.UserRepository;
import com.mbbank.biz.pro.service.UserSyncService;
import com.mbbank.biz.pro.service.dto.ApiResponseDTO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing user synchronization between jhi_user and users tables.
 * This is mainly for admin/testing purposes.
 */
@RestController
@RequestMapping("/api/admin")
public class UserSyncController {

    private final Logger log = LoggerFactory.getLogger(UserSyncController.class);

    private final UserSyncService userSyncService;
    private final UserRepository userRepository;

    public UserSyncController(UserSyncService userSyncService, UserRepository userRepository) {
        this.userSyncService = userSyncService;
        this.userRepository = userRepository;
    }

    /**
     * POST /api/admin/user-sync/sync-all : Sync all users from jhi_user to users table
     *
     * @return the ResponseEntity with status 200 (OK) and sync results
     */
    @PostMapping("/user-sync/sync-all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> syncAllUsers() {
        log.debug("REST request to sync all users from jhi_user to users table");

        try {
            List<User> allUsers = userRepository.findAll();
            int totalUsers = allUsers.size();
            int syncedCount = 0;
            int errorCount = 0;

            for (User user : allUsers) {
                try {
                    userSyncService.syncUserToAppUser(user);
                    syncedCount++;
                } catch (Exception e) {
                    log.error("Failed to sync user: {}", user.getLogin(), e);
                    errorCount++;
                }
            }

            Map<String, Object> result = Map.of(
                "totalUsers",
                totalUsers,
                "syncedCount",
                syncedCount,
                "errorCount",
                errorCount,
                "message",
                String.format("Synced %d out of %d users successfully", syncedCount, totalUsers)
            );

            return ResponseEntity.ok(ApiResponseDTO.success(result));
        } catch (Exception e) {
            log.error("Error during bulk user sync", e);
            return ResponseEntity.internalServerError().body(ApiResponseDTO.error("SYNC_ERROR", "Failed to sync users: " + e.getMessage()));
        }
    }

    /**
     * POST /api/admin/user-sync/sync-user/{login} : Sync specific user by login
     *
     * @param login the login of the user to sync
     * @return the ResponseEntity with status 200 (OK) and sync result
     */
    @PostMapping("/user-sync/sync-user/{login}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseDTO<AppUser>> syncSpecificUser(@PathVariable String login) {
        log.debug("REST request to sync user: {}", login);

        try {
            return userRepository
                .findOneByLogin(login)
                .map(user -> {
                    AppUser syncedAppUser = userSyncService.syncUserToAppUser(user);
                    return ResponseEntity.ok(ApiResponseDTO.success(syncedAppUser, "User synced successfully"));
                })
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error syncing user: {}", login, e);
            return ResponseEntity.internalServerError().body(ApiResponseDTO.error("SYNC_ERROR", "Failed to sync user: " + e.getMessage()));
        }
    }

    /**
     * GET /api/admin/user-sync/status : Get sync status for all users
     *
     * @return the ResponseEntity with status 200 (OK) and sync status
     */
    @GetMapping("/user-sync/status")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> getSyncStatus() {
        log.debug("REST request to get user sync status");

        try {
            List<User> allUsers = userRepository.findAll();

            List<Map<String, Object>> userStatus = allUsers
                .stream()
                .map(user -> {
                    boolean syncNeeded = userSyncService.isSyncNeeded(user, null); // This will check if AppUser exists
                    return Map.<String, Object>of(
                        "login",
                        user.getLogin(),
                        "email",
                        user.getEmail(),
                        "activated",
                        user.isActivated(),
                        "syncNeeded",
                        syncNeeded,
                        "lastModified",
                        user.getLastModifiedDate() != null ? user.getLastModifiedDate().toString() : "Never"
                    );
                })
                .collect(Collectors.toList());

            long syncNeededCount = userStatus.stream().mapToLong(status -> (Boolean) status.get("syncNeeded") ? 1 : 0).sum();

            Map<String, Object> result = Map.of(
                "totalUsers",
                allUsers.size(),
                "syncNeededCount",
                syncNeededCount,
                "userStatus",
                userStatus
            );

            return ResponseEntity.ok(ApiResponseDTO.success(result));
        } catch (Exception e) {
            log.error("Error getting sync status", e);
            return ResponseEntity.internalServerError()
                .body(ApiResponseDTO.error("STATUS_ERROR", "Failed to get sync status: " + e.getMessage()));
        }
    }
}
