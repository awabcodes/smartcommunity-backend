package com.awabcodes.smartcommunity.repository;

import com.awabcodes.smartcommunity.domain.Poll;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Poll entity.
 */
@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    Optional<Poll> findByChoicesId(Long id);

    @Query(value = "select distinct poll from Poll poll left join fetch poll.users",
        countQuery = "select count(distinct poll) from Poll poll")
    Page<Poll> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct poll from Poll poll left join fetch poll.users")
    List<Poll> findAllWithEagerRelationships();

    @Query("select poll from Poll poll left join fetch poll.users where poll.id =:id")
    Optional<Poll> findOneWithEagerRelationships(@Param("id") Long id);

    Page<Poll> findAllByUsersIdNotOrUsersIsNull(Pageable pageable, Long userId);
}
