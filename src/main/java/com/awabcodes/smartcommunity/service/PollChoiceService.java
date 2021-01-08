package com.awabcodes.smartcommunity.service;

import com.awabcodes.smartcommunity.domain.PollChoice;
import com.awabcodes.smartcommunity.repository.PollChoiceRepository;
import com.awabcodes.smartcommunity.service.dto.PollChoiceDTO;
import com.awabcodes.smartcommunity.service.mapper.PollChoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PollChoice}.
 */
@Service
@Transactional
public class PollChoiceService {

    private final Logger log = LoggerFactory.getLogger(PollChoiceService.class);

    private final PollChoiceRepository pollChoiceRepository;

    private final PollChoiceMapper pollChoiceMapper;

    public PollChoiceService(PollChoiceRepository pollChoiceRepository, PollChoiceMapper pollChoiceMapper) {
        this.pollChoiceRepository = pollChoiceRepository;
        this.pollChoiceMapper = pollChoiceMapper;
    }

    /**
     * Save a pollChoice.
     *
     * @param pollChoiceDTO the entity to save.
     * @return the persisted entity.
     */
    public PollChoiceDTO save(PollChoiceDTO pollChoiceDTO) {
        log.debug("Request to save PollChoice : {}", pollChoiceDTO);
        PollChoice pollChoice = pollChoiceMapper.toEntity(pollChoiceDTO);
        pollChoice = pollChoiceRepository.save(pollChoice);
        return pollChoiceMapper.toDto(pollChoice);
    }

    /**
     * Get all the pollChoices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PollChoiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PollChoices");
        return pollChoiceRepository.findAll(pageable)
            .map(pollChoiceMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PollChoiceDTO> findAllByPollId(Pageable pageable, Long id) {
        log.debug("Request to get all PollChoices for poll");
        return pollChoiceRepository.findAllByPollId(pageable, id)
                .map(pollChoiceMapper::toDto);
    }

    /**
     * Get one pollChoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PollChoiceDTO> findOne(Long id) {
        log.debug("Request to get PollChoice : {}", id);
        return pollChoiceRepository.findById(id)
            .map(pollChoiceMapper::toDto);
    }

    /**
     * Delete the pollChoice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PollChoice : {}", id);
        pollChoiceRepository.deleteById(id);
    }
}
