package com.awabcodes.smartcommunity.repository;

import com.awabcodes.smartcommunity.domain.Need;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Need entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NeedRepository extends JpaRepository<Need, Long> {
}
