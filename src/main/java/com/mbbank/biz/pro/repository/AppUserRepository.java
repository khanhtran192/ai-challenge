package com.mbbank.biz.pro.repository;

import com.mbbank.biz.pro.domain.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String>, JpaSpecificationExecutor<AppUser> {
    /**
     * Find AppUser by email.
     *
     * @param email the email to search for
     * @return optional AppUser
     */
    Optional<AppUser> findOneByEmail(String email);
}
