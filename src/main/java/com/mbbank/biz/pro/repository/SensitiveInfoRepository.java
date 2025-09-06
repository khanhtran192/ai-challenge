package com.mbbank.biz.pro.repository;

import com.mbbank.biz.pro.domain.SensitiveInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SensitiveInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensitiveInfoRepository extends JpaRepository<SensitiveInfo, String>, JpaSpecificationExecutor<SensitiveInfo> {}
