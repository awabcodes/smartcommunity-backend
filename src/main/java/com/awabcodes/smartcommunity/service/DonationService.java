package com.awabcodes.smartcommunity.service;

import com.awabcodes.smartcommunity.domain.Donation;
import com.awabcodes.smartcommunity.repository.DonationRepository;
import com.awabcodes.smartcommunity.service.dto.DonationDTO;
import com.awabcodes.smartcommunity.service.mapper.DonationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Donation}.
 */
@Service
@Transactional
public class DonationService {

    private final Logger log = LoggerFactory.getLogger(DonationService.class);

    private final DonationRepository donationRepository;

    private final DonationMapper donationMapper;

    public DonationService(DonationRepository donationRepository, DonationMapper donationMapper) {
        this.donationRepository = donationRepository;
        this.donationMapper = donationMapper;
    }

    /**
     * Save a donation.
     *
     * @param donationDTO the entity to save.
     * @return the persisted entity.
     */
    public DonationDTO save(DonationDTO donationDTO) {
        log.debug("Request to save Donation : {}", donationDTO);
        Donation donation = donationMapper.toEntity(donationDTO);
        donation = donationRepository.save(donation);
        return donationMapper.toDto(donation);
    }

    /**
     * Get all the donations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DonationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Donations");
        return donationRepository.findAll(pageable)
            .map(donationMapper::toDto);
    }


    /**
     * Get one donation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DonationDTO> findOne(Long id) {
        log.debug("Request to get Donation : {}", id);
        return donationRepository.findById(id)
            .map(donationMapper::toDto);
    }

    /**
     * Delete the donation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Donation : {}", id);
        donationRepository.deleteById(id);
    }
}
