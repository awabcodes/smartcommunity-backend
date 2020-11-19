package com.awabcodes.smartcommunity.repository;

import com.awabcodes.smartcommunity.domain.Feedback;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Feedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("select feedback from Feedback feedback where feedback.user.login = ?#{principal.username}")
    List<Feedback> findByUserIsCurrentUser();
}
