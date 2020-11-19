package com.awabcodes.smartcommunity.service;

import com.awabcodes.smartcommunity.domain.DonationRequest;
import com.awabcodes.smartcommunity.repository.DonationRequestRepository;
import com.awabcodes.smartcommunity.service.dto.DonationRequestDTO;
import com.awabcodes.smartcommunity.service.mapper.DonationRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DonationRequest}.
 */
@Service
@Transactional
public class DonationRequestService {

    private final Logger log = LoggerFactory.getLogger(DonationRequestService.class);

    private final DonationRequestRepository donationRequestRepository;

    private final DonationRequestMapper donationRequestMapper;

    public DonationRequestService(DonationRequestRepository donationRequestRepository, DonationRequestMapper donationRequestMapper) {
        this.donationRequestRepository = donationRequestRepository;
        this.donationRequestMapper = donationRequestMapper;
    }

    /**
     * Save a donationRequest.
     *
     * @param donationRequestDTO the entity to save.
     * @return the persisted entity.
     */
    public DonationRequestDTO save(DonationRequestDTO donationRequestDTO) {
        log.debug("Request to save DonationRequest : {}", donationRequestDTO);
        DonationRequest donationRequest = donationRequestMapper.toEntity(donationRequestDTO);
        donationRequest = donationRequestRepository.save(donationRequest);
        return donationRequestMapper.toDto(donationRequest);
    }

    /**
     * Get all the donationRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DonationRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DonationRequests");
        return donationRequestRepository.findAll(pageable)
            .map(donationRequestMapper::toDto);
    }


    /**
     * Get one donationRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DonationRequestDTO> findOne(Long id) {
        log.debug("Request to get DonationRequest : {}", id);
        return donationRequestRepository.findById(id)
            .map(donationRequestMapper::toDto);
    }

    /**
     * Delete the donationRequest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DonationRequest : {}", id);
        donationRequestRepository.deleteById(id);
    }
}
