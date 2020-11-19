package com.awabcodes.smartcommunity.web.rest;

import com.awabcodes.smartcommunity.service.DonationRequestService;
import com.awabcodes.smartcommunity.web.rest.errors.BadRequestAlertException;
import com.awabcodes.smartcommunity.service.dto.DonationRequestDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.awabcodes.smartcommunity.domain.DonationRequest}.
 */
@RestController
@RequestMapping("/api")
public class DonationRequestResource {

    private final Logger log = LoggerFactory.getLogger(DonationRequestResource.class);

    private static final String ENTITY_NAME = "donationRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonationRequestService donationRequestService;

    public DonationRequestResource(DonationRequestService donationRequestService) {
        this.donationRequestService = donationRequestService;
    }

    /**
     * {@code POST  /donation-requests} : Create a new donationRequest.
     *
     * @param donationRequestDTO the donationRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donationRequestDTO, or with status {@code 400 (Bad Request)} if the donationRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donation-requests")
    public ResponseEntity<DonationRequestDTO> createDonationRequest(@Valid @RequestBody DonationRequestDTO donationRequestDTO) throws URISyntaxException {
        log.debug("REST request to save DonationRequest : {}", donationRequestDTO);
        if (donationRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new donationRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonationRequestDTO result = donationRequestService.save(donationRequestDTO);
        return ResponseEntity.created(new URI("/api/donation-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /donation-requests} : Updates an existing donationRequest.
     *
     * @param donationRequestDTO the donationRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donationRequestDTO,
     * or with status {@code 400 (Bad Request)} if the donationRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donationRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/donation-requests")
    public ResponseEntity<DonationRequestDTO> updateDonationRequest(@Valid @RequestBody DonationRequestDTO donationRequestDTO) throws URISyntaxException {
        log.debug("REST request to update DonationRequest : {}", donationRequestDTO);
        if (donationRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DonationRequestDTO result = donationRequestService.save(donationRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donationRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /donation-requests} : get all the donationRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donationRequests in body.
     */
    @GetMapping("/donation-requests")
    public ResponseEntity<List<DonationRequestDTO>> getAllDonationRequests(Pageable pageable) {
        log.debug("REST request to get a page of DonationRequests");
        Page<DonationRequestDTO> page = donationRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /donation-requests/:id} : get the "id" donationRequest.
     *
     * @param id the id of the donationRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donation-requests/{id}")
    public ResponseEntity<DonationRequestDTO> getDonationRequest(@PathVariable Long id) {
        log.debug("REST request to get DonationRequest : {}", id);
        Optional<DonationRequestDTO> donationRequestDTO = donationRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donationRequestDTO);
    }

    /**
     * {@code DELETE  /donation-requests/:id} : delete the "id" donationRequest.
     *
     * @param id the id of the donationRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donation-requests/{id}")
    public ResponseEntity<Void> deleteDonationRequest(@PathVariable Long id) {
        log.debug("REST request to delete DonationRequest : {}", id);
        donationRequestService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
