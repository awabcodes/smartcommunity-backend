package com.awabcodes.smartcommunity.web.rest;

import com.awabcodes.smartcommunity.service.NeedService;
import com.awabcodes.smartcommunity.web.rest.errors.BadRequestAlertException;
import com.awabcodes.smartcommunity.service.dto.NeedDTO;

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
 * REST controller for managing {@link com.awabcodes.smartcommunity.domain.Need}.
 */
@RestController
@RequestMapping("/api")
public class NeedResource {

    private final Logger log = LoggerFactory.getLogger(NeedResource.class);

    private static final String ENTITY_NAME = "need";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NeedService needService;

    public NeedResource(NeedService needService) {
        this.needService = needService;
    }

    /**
     * {@code POST  /needs} : Create a new need.
     *
     * @param needDTO the needDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new needDTO, or with status {@code 400 (Bad Request)} if the need has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/needs")
    public ResponseEntity<NeedDTO> createNeed(@Valid @RequestBody NeedDTO needDTO) throws URISyntaxException {
        log.debug("REST request to save Need : {}", needDTO);
        if (needDTO.getId() != null) {
            throw new BadRequestAlertException("A new need cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NeedDTO result = needService.save(needDTO);
        return ResponseEntity.created(new URI("/api/needs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /needs} : Updates an existing need.
     *
     * @param needDTO the needDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated needDTO,
     * or with status {@code 400 (Bad Request)} if the needDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the needDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/needs")
    public ResponseEntity<NeedDTO> updateNeed(@Valid @RequestBody NeedDTO needDTO) throws URISyntaxException {
        log.debug("REST request to update Need : {}", needDTO);
        if (needDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NeedDTO result = needService.save(needDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, needDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /needs} : get all the needs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of needs in body.
     */
    @GetMapping("/needs")
    public ResponseEntity<List<NeedDTO>> getAllNeeds(Pageable pageable) {
        log.debug("REST request to get a page of Needs");
        Page<NeedDTO> page = needService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /needs/:id} : get the "id" need.
     *
     * @param id the id of the needDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the needDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/needs/{id}")
    public ResponseEntity<NeedDTO> getNeed(@PathVariable Long id) {
        log.debug("REST request to get Need : {}", id);
        Optional<NeedDTO> needDTO = needService.findOne(id);
        return ResponseUtil.wrapOrNotFound(needDTO);
    }

    /**
     * {@code DELETE  /needs/:id} : delete the "id" need.
     *
     * @param id the id of the needDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/needs/{id}")
    public ResponseEntity<Void> deleteNeed(@PathVariable Long id) {
        log.debug("REST request to delete Need : {}", id);
        needService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
