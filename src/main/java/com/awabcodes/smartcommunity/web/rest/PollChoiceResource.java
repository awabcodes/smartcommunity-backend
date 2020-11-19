package com.awabcodes.smartcommunity.web.rest;

import com.awabcodes.smartcommunity.service.PollChoiceService;
import com.awabcodes.smartcommunity.web.rest.errors.BadRequestAlertException;
import com.awabcodes.smartcommunity.service.dto.PollChoiceDTO;

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
 * REST controller for managing {@link com.awabcodes.smartcommunity.domain.PollChoice}.
 */
@RestController
@RequestMapping("/api")
public class PollChoiceResource {

    private final Logger log = LoggerFactory.getLogger(PollChoiceResource.class);

    private static final String ENTITY_NAME = "pollChoice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PollChoiceService pollChoiceService;

    public PollChoiceResource(PollChoiceService pollChoiceService) {
        this.pollChoiceService = pollChoiceService;
    }

    /**
     * {@code POST  /poll-choices} : Create a new pollChoice.
     *
     * @param pollChoiceDTO the pollChoiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pollChoiceDTO, or with status {@code 400 (Bad Request)} if the pollChoice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/poll-choices")
    public ResponseEntity<PollChoiceDTO> createPollChoice(@Valid @RequestBody PollChoiceDTO pollChoiceDTO) throws URISyntaxException {
        log.debug("REST request to save PollChoice : {}", pollChoiceDTO);
        if (pollChoiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new pollChoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PollChoiceDTO result = pollChoiceService.save(pollChoiceDTO);
        return ResponseEntity.created(new URI("/api/poll-choices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /poll-choices} : Updates an existing pollChoice.
     *
     * @param pollChoiceDTO the pollChoiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pollChoiceDTO,
     * or with status {@code 400 (Bad Request)} if the pollChoiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pollChoiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/poll-choices")
    public ResponseEntity<PollChoiceDTO> updatePollChoice(@Valid @RequestBody PollChoiceDTO pollChoiceDTO) throws URISyntaxException {
        log.debug("REST request to update PollChoice : {}", pollChoiceDTO);
        if (pollChoiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PollChoiceDTO result = pollChoiceService.save(pollChoiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pollChoiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /poll-choices} : get all the pollChoices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pollChoices in body.
     */
    @GetMapping("/poll-choices")
    public ResponseEntity<List<PollChoiceDTO>> getAllPollChoices(Pageable pageable) {
        log.debug("REST request to get a page of PollChoices");
        Page<PollChoiceDTO> page = pollChoiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /poll-choices/:id} : get the "id" pollChoice.
     *
     * @param id the id of the pollChoiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pollChoiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/poll-choices/{id}")
    public ResponseEntity<PollChoiceDTO> getPollChoice(@PathVariable Long id) {
        log.debug("REST request to get PollChoice : {}", id);
        Optional<PollChoiceDTO> pollChoiceDTO = pollChoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pollChoiceDTO);
    }

    /**
     * {@code DELETE  /poll-choices/:id} : delete the "id" pollChoice.
     *
     * @param id the id of the pollChoiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/poll-choices/{id}")
    public ResponseEntity<Void> deletePollChoice(@PathVariable Long id) {
        log.debug("REST request to delete PollChoice : {}", id);
        pollChoiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
