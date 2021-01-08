package com.awabcodes.smartcommunity.service;

import com.awabcodes.smartcommunity.domain.Vote;
import com.awabcodes.smartcommunity.repository.VoteRepository;
import com.awabcodes.smartcommunity.security.AuthoritiesConstants;
import com.awabcodes.smartcommunity.security.SecurityUtils;
import com.awabcodes.smartcommunity.service.dto.VoteDTO;
import com.awabcodes.smartcommunity.service.mapper.VoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Vote}.
 */
@Service
@Transactional
public class VoteService {

    private final Logger log = LoggerFactory.getLogger(VoteService.class);

    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;

    private final PollService pollService;

    public VoteService(VoteRepository voteRepository, VoteMapper voteMapper, PollService pollService) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.pollService = pollService;
    }

    /**
     * Save a vote.
     *
     * @param voteDTO the entity to save.
     * @return the persisted entity.
     */
    public VoteDTO save(VoteDTO voteDTO) {
        log.debug("Request to save Vote : {}", voteDTO);
        Vote vote = voteMapper.toEntity(voteDTO);
        vote = voteRepository.save(vote);

        pollService.addUserToPoll(vote);
        return voteMapper.toDto(vote);
    }

    /**
     * Get all the votes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Votes");

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return voteRepository.findAll(pageable)
                .map(voteMapper::toDto);
        }

        return voteRepository.findAllByUserLogin(pageable, SecurityUtils.getCurrentUserLogin().get())
            .map(voteMapper::toDto);
    }


    /**
     * Get one vote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VoteDTO> findOne(Long id) {
        log.debug("Request to get Vote : {}", id);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return voteRepository.findById(id)
                .map(voteMapper::toDto);
        }

        return voteRepository.findByIdAndUserLogin(id, SecurityUtils.getCurrentUserLogin().get())
            .map(voteMapper::toDto);
    }

    /**
     * Delete the vote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Vote : {}", id);
        voteRepository.deleteById(id);
    }
}
