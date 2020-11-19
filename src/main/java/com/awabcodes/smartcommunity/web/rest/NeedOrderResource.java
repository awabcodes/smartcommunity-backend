package com.awabcodes.smartcommunity.web.rest;

import com.awabcodes.smartcommunity.service.NeedOrderService;
import com.awabcodes.smartcommunity.web.rest.errors.BadRequestAlertException;
import com.awabcodes.smartcommunity.service.dto.NeedOrderDTO;

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
 * REST controller for managing {@link com.awabcodes.smartcommunity.domain.NeedOrder}.
 */
@RestController
@RequestMapping("/api")
public class NeedOrderResource {

    private final Logger log = LoggerFactory.getLogger(NeedOrderResource.class);

    private static final String ENTITY_NAME = "needOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NeedOrderService needOrderService;

    public NeedOrderResource(NeedOrderService needOrderService) {
        this.needOrderService = needOrderService;
    }

    /**
     * {@code POST  /need-orders} : Create a new needOrder.
     *
     * @param needOrderDTO the needOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new needOrderDTO, or with status {@code 400 (Bad Request)} if the needOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/need-orders")
    public ResponseEntity<NeedOrderDTO> createNeedOrder(@Valid @RequestBody NeedOrderDTO needOrderDTO) throws URISyntaxException {
        log.debug("REST request to save NeedOrder : {}", needOrderDTO);
        if (needOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new needOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NeedOrderDTO result = needOrderService.save(needOrderDTO);
        return ResponseEntity.created(new URI("/api/need-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /need-orders} : Updates an existing needOrder.
     *
     * @param needOrderDTO the needOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated needOrderDTO,
     * or with status {@code 400 (Bad Request)} if the needOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the needOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/need-orders")
    public ResponseEntity<NeedOrderDTO> updateNeedOrder(@Valid @RequestBody NeedOrderDTO needOrderDTO) throws URISyntaxException {
        log.debug("REST request to update NeedOrder : {}", needOrderDTO);
        if (needOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NeedOrderDTO result = needOrderService.save(needOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, needOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /need-orders} : get all the needOrders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of needOrders in body.
     */
    @GetMapping("/need-orders")
    public ResponseEntity<List<NeedOrderDTO>> getAllNeedOrders(Pageable pageable) {
        log.debug("REST request to get a page of NeedOrders");
        Page<NeedOrderDTO> page = needOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /need-orders/:id} : get the "id" needOrder.
     *
     * @param id the id of the needOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the needOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/need-orders/{id}")
    public ResponseEntity<NeedOrderDTO> getNeedOrder(@PathVariable Long id) {
        log.debug("REST request to get NeedOrder : {}", id);
        Optional<NeedOrderDTO> needOrderDTO = needOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(needOrderDTO);
    }

    /**
     * {@code DELETE  /need-orders/:id} : delete the "id" needOrder.
     *
     * @param id the id of the needOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/need-orders/{id}")
    public ResponseEntity<Void> deleteNeedOrder(@PathVariable Long id) {
        log.debug("REST request to delete NeedOrder : {}", id);
        needOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
