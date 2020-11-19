package com.awabcodes.smartcommunity.repository;

import com.awabcodes.smartcommunity.domain.DonationRequest;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DonationRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonationRequestRepository extends JpaRepository<DonationRequest, Long> {
}
