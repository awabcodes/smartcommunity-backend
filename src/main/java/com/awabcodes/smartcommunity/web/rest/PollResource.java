package com.awabcodes.smartcommunity.web.rest;

import com.awabcodes.smartcommunity.service.PollService;
import com.awabcodes.smartcommunity.web.rest.errors.BadRequestAlertException;
import com.awabcodes.smartcommunity.service.dto.PollDTO;

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
 * REST controller for managing {@link com.awabcodes.smartcommunity.domain.Poll}.
 */
@RestController
@RequestMapping("/api")
public class PollResource {

    private final Logger log = LoggerFactory.getLogger(PollResource.class);

    private static final String ENTITY_NAME = "poll";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PollService pollService;

    public PollResource(PollService pollService) {
        this.pollService = pollService;
    }

    /**
     * {@code POST  /polls} : Create a new poll.
     *
     * @param pollDTO the pollDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pollDTO, or with status {@code 400 (Bad Request)} if the poll has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/polls")
    public ResponseEntity<PollDTO> createPoll(@Valid @RequestBody PollDTO pollDTO) throws URISyntaxException {
        log.debug("REST request to save Poll : {}", pollDTO);
        if (pollDTO.getId() != null) {
            throw new BadRequestAlertException("A new poll cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PollDTO result = pollService.save(pollDTO);
        return ResponseEntity.created(new URI("/api/polls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /polls} : Updates an existing poll.
     *
     * @param pollDTO the pollDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pollDTO,
     * or with status {@code 400 (Bad Request)} if the pollDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pollDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/polls")
    public ResponseEntity<PollDTO> updatePoll(@Valid @RequestBody PollDTO pollDTO) throws URISyntaxException {
        log.debug("REST request to update Poll : {}", pollDTO);
        if (pollDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PollDTO result = pollService.save(pollDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pollDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /polls} : get all the polls.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of polls in body.
     */
    @GetMapping("/polls")
    public ResponseEntity<List<PollDTO>> getAllPolls(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Polls");
        Page<PollDTO> page;
        if (eagerload) {
            page = pollService.findAllWithEagerRelationships(pageable);
        } else {
            page = pollService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /polls/:id} : get the "id" poll.
     *
     * @param id the id of the pollDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pollDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/polls/{id}")
    public ResponseEntity<PollDTO> getPoll(@PathVariable Long id) {
        log.debug("REST request to get Poll : {}", id);
        Optional<PollDTO> pollDTO = pollService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pollDTO);
    }

    @GetMapping("/polls/votes/{id}")
    public ResponseEntity<PollDTO> getPollByVote(@PathVariable Long id) {
        log.debug("REST request to get Poll by Vote : {}", id);
        Optional<PollDTO> pollDTO = pollService.findOneByVote(id);
        return ResponseUtil.wrapOrNotFound(pollDTO);
    }
    
    /**
     * {@code DELETE  /polls/:id} : delete the "id" poll.
     *
     * @param id the id of the pollDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/polls/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable Long id) {
        log.debug("REST request to delete Poll : {}", id);
        pollService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
