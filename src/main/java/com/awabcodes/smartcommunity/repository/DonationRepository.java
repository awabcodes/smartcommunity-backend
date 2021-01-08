package com.awabcodes.smartcommunity.repository;

import com.awabcodes.smartcommunity.domain.Donation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Donation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("select donation from Donation donation where donation.user.login = ?#{principal.username}")
    List<Donation> findByUserIsCurrentUser();

    Page<Donation> findAllByUserLogin(Pageable pageable, String login);

    Optional<Donation> findByIdAndUserLogin(Long id, String login);
}
