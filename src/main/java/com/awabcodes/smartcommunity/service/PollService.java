package com.awabcodes.smartcommunity.service;

import com.awabcodes.smartcommunity.domain.Poll;
import com.awabcodes.smartcommunity.domain.Vote;
import com.awabcodes.smartcommunity.repository.PollRepository;
import com.awabcodes.smartcommunity.repository.VoteRepository;
import com.awabcodes.smartcommunity.service.dto.PollDTO;
import com.awabcodes.smartcommunity.service.mapper.PollMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Poll}.
 */
@Service
@Transactional
public class PollService {

    private final Logger log = LoggerFactory.getLogger(PollService.class);

    private final PollRepository pollRepository;

    private final PollMapper pollMapper;

    private final VoteRepository voteRepository;

    public PollService(PollRepository pollRepository, PollMapper pollMapper, VoteRepository voteRepository) {
        this.pollRepository = pollRepository;
        this.pollMapper = pollMapper;
        this.voteRepository = voteRepository;
    }

    /**
     * Save a poll.
     *
     * @param pollDTO the entity to save.
     * @return the persisted entity.
     */
    public PollDTO save(PollDTO pollDTO) {
        log.debug("Request to save Poll : {}", pollDTO);
        Poll poll = pollMapper.toEntity(pollDTO);
        poll = pollRepository.save(poll);
        return pollMapper.toDto(poll);
    }

    /**
     * Get all the polls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PollDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Polls");
        return pollRepository.findAll(pageable)
            .map(pollMapper::toDto);
    }


    /**
     * Get all the polls with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PollDTO> findAllWithEagerRelationships(Pageable pageable) {
        return pollRepository.findAllWithEagerRelationships(pageable).map(pollMapper::toDto);
    }

    /**
     * Get one poll by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PollDTO> findOne(Long id) {
        log.debug("Request to get Poll : {}", id);
        return pollRepository.findOneWithEagerRelationships(id)
            .map(pollMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<PollDTO> findOneByVote(Long id) {
        log.debug("Request to get Poll by Vote: {}", id);
        Optional<Vote> vote = voteRepository.findById(id);
        return pollRepository.findByChoicesId(vote.get().getChoice().getId())
            .map(pollMapper::toDto);
    }

    /**
     * Delete the poll by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Poll : {}", id);
        pollRepository.deleteById(id);
    }
}
