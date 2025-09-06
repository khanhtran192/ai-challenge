package com.mbbank.biz.pro.service;

import com.mbbank.biz.pro.domain.AppUser;
import com.mbbank.biz.pro.domain.User;
import com.mbbank.biz.pro.domain.enumeration.Role;
import com.mbbank.biz.pro.repository.AppUserRepository;
import com.mbbank.biz.pro.security.AuthoritiesConstants;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for synchronizing User (jhi_user) data with AppUser (users) data.
 */
@Service
@Transactional
public class UserSyncService {

    private static final Logger LOG = LoggerFactory.getLogger(UserSyncService.class);

    private final AppUserRepository appUserRepository;

    public UserSyncService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    /**
     * Synchronize a JHipster User with AppUser table.
     * Creates or updates an AppUser record based on the User data.
     *
     * @param user the JHipster user to sync
     * @return the created/updated AppUser
     */
    public AppUser syncUserToAppUser(User user) {
        LOG.debug("Syncing User {} to AppUser table", user.getLogin());

        // Check if AppUser already exists by email
        Optional<AppUser> existingAppUser = appUserRepository.findOneByEmail(user.getEmail());

        AppUser appUser;
        if (existingAppUser.isPresent()) {
            appUser = existingAppUser.get();
            LOG.debug("Updating existing AppUser with email: {}", user.getEmail());
        } else {
            appUser = new AppUser();
            LOG.debug("Creating new AppUser for email: {}", user.getEmail());
        }

        // Map User data to AppUser
        appUser.setFullName(buildFullName(user.getFirstName(), user.getLastName()));
        appUser.setEmail(user.getEmail().toLowerCase());
        appUser.setPasswordHash(user.getPassword());
        appUser.setRole(mapAuthoritiesToRole(user));

        // Set timestamps
        Instant now = Instant.now();
        if (appUser.getCreatedAt() == null) {
            appUser.setCreatedAt(user.getCreatedDate() != null ? user.getCreatedDate() : now);
        }
        appUser.setUpdatedAt(user.getLastModifiedDate() != null ? user.getLastModifiedDate() : now);

        // Save AppUser
        appUser = appUserRepository.save(appUser);
        LOG.debug("Successfully synced User {} to AppUser with ID: {}", user.getLogin(), appUser.getId());

        return appUser;
    }

    /**
     * Delete AppUser when corresponding User is deleted.
     *
     * @param userEmail the email of the deleted user
     */
    public void deleteAppUserByEmail(String userEmail) {
        LOG.debug("Deleting AppUser with email: {}", userEmail);
        appUserRepository
            .findOneByEmail(userEmail)
            .ifPresent(appUser -> {
                appUserRepository.delete(appUser);
                LOG.debug("Successfully deleted AppUser with ID: {}", appUser.getId());
            });
    }

    /**
     * Build full name from first name and last name.
     */
    private String buildFullName(String firstName, String lastName) {
        StringBuilder fullName = new StringBuilder();

        if (firstName != null && !firstName.trim().isEmpty()) {
            fullName.append(firstName.trim());
        }

        if (lastName != null && !lastName.trim().isEmpty()) {
            if (fullName.length() > 0) {
                fullName.append(" ");
            }
            fullName.append(lastName.trim());
        }

        // If no name provided, use a default
        if (fullName.length() == 0) {
            fullName.append("User");
        }

        return fullName.toString();
    }

    /**
     * Map JHipster authorities to AppUser Role.
     */
    private Role mapAuthoritiesToRole(User user) {
        if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
            return Role.user;
        }

        // Check for admin authority first
        boolean hasAdminAuthority = user
            .getAuthorities()
            .stream()
            .anyMatch(authority -> AuthoritiesConstants.ADMIN.equals(authority.getName()));

        if (hasAdminAuthority) {
            return Role.admin;
        }

        // Check for manager authority
        boolean hasManagerAuthority = user.getAuthorities().stream().anyMatch(authority -> "ROLE_MANAGER".equals(authority.getName()));

        if (hasManagerAuthority) {
            return Role.manager;
        }

        // Default to user role
        return Role.user;
    }

    /**
     * Check if sync is needed by comparing last modified dates.
     */
    public boolean isSyncNeeded(User user, AppUser appUser) {
        if (appUser == null) {
            return true; // AppUser doesn't exist, sync needed
        }

        // Compare last modified dates
        Instant userLastModified = user.getLastModifiedDate();
        Instant appUserLastModified = appUser.getUpdatedAt();

        if (userLastModified == null) {
            return false; // No changes in User
        }

        if (appUserLastModified == null) {
            return true; // AppUser never updated, sync needed
        }

        // Sync if User was modified after AppUser
        return userLastModified.isAfter(appUserLastModified);
    }

    /**
     * Sync user data only if needed (based on modification dates).
     */
    public AppUser syncIfNeeded(User user) {
        Optional<AppUser> existingAppUser = appUserRepository.findOneByEmail(user.getEmail());

        if (existingAppUser.isPresent() && !isSyncNeeded(user, existingAppUser.get())) {
            LOG.debug("Sync not needed for user: {}", user.getLogin());
            return existingAppUser.get();
        }

        return syncUserToAppUser(user);
    }
}
