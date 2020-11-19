package com.awabcodes.smartcommunity.service;

import com.awabcodes.smartcommunity.domain.Need;
import com.awabcodes.smartcommunity.repository.NeedRepository;
import com.awabcodes.smartcommunity.service.dto.NeedDTO;
import com.awabcodes.smartcommunity.service.mapper.NeedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Need}.
 */
@Service
@Transactional
public class NeedService {

    private final Logger log = LoggerFactory.getLogger(NeedService.class);

    private final NeedRepository needRepository;

    private final NeedMapper needMapper;

    public NeedService(NeedRepository needRepository, NeedMapper needMapper) {
        this.needRepository = needRepository;
        this.needMapper = needMapper;
    }

    /**
     * Save a need.
     *
     * @param needDTO the entity to save.
     * @return the persisted entity.
     */
    public NeedDTO save(NeedDTO needDTO) {
        log.debug("Request to save Need : {}", needDTO);
        Need need = needMapper.toEntity(needDTO);
        need = needRepository.save(need);
        return needMapper.toDto(need);
    }

    /**
     * Get all the needs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NeedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Needs");
        return needRepository.findAll(pageable)
            .map(needMapper::toDto);
    }


    /**
     * Get one need by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NeedDTO> findOne(Long id) {
        log.debug("Request to get Need : {}", id);
        return needRepository.findById(id)
            .map(needMapper::toDto);
    }

    /**
     * Delete the need by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Need : {}", id);
        needRepository.deleteById(id);
    }
}
