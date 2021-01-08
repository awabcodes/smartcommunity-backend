package com.awabcodes.smartcommunity.repository;

import com.awabcodes.smartcommunity.domain.Poll;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Poll entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    Optional<Poll> findByChoicesId(Long id);
}
