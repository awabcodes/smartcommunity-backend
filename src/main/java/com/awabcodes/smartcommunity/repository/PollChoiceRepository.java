package com.awabcodes.smartcommunity.repository;

import com.awabcodes.smartcommunity.domain.PollChoice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PollChoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PollChoiceRepository extends JpaRepository<PollChoice, Long> {
}
