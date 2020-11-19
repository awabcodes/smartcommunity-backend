package com.awabcodes.smartcommunity.repository;

import com.awabcodes.smartcommunity.domain.Donation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Donation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("select donation from Donation donation where donation.user.login = ?#{principal.username}")
    List<Donation> findByUserIsCurrentUser();
}
