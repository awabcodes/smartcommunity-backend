package com.awabcodes.smartcommunity.repository;

import com.awabcodes.smartcommunity.domain.Vote;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Vote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("select vote from Vote vote where vote.user.login = ?#{principal.username}")
    List<Vote> findByUserIsCurrentUser();
}
