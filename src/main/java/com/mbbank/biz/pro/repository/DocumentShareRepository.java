package com.mbbank.biz.pro.repository;

import com.mbbank.biz.pro.domain.DocumentShare;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentShare entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentShareRepository extends JpaRepository<DocumentShare, Long>, JpaSpecificationExecutor<DocumentShare> {}
