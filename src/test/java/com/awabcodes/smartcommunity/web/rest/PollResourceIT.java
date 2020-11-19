package com.awabcodes.smartcommunity.web.rest;

import com.awabcodes.smartcommunity.SmartcommunityApp;
import com.awabcodes.smartcommunity.domain.Poll;
import com.awabcodes.smartcommunity.domain.PollChoice;
import com.awabcodes.smartcommunity.repository.PollRepository;
import com.awabcodes.smartcommunity.service.PollService;
import com.awabcodes.smartcommunity.service.dto.PollDTO;
import com.awabcodes.smartcommunity.service.mapper.PollMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PollResource} REST controller.
 */
@SpringBootTest(classes = SmartcommunityApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PollResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PollMapper pollMapper;

    @Autowired
    private PollService pollService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPollMockMvc;

    private Poll poll;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poll createEntity(EntityManager em) {
        Poll poll = new Poll()
            .question(DEFAULT_QUESTION)
            .active(DEFAULT_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .creationDate(DEFAULT_CREATION_DATE);
        // Add required entity
        PollChoice pollChoice;
        if (TestUtil.findAll(em, PollChoice.class).isEmpty()) {
            pollChoice = PollChoiceResourceIT.createEntity(em);
            em.persist(pollChoice);
            em.flush();
        } else {
            pollChoice = TestUtil.findAll(em, PollChoice.class).get(0);
        }
        poll.getChoices().add(pollChoice);
        return poll;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poll createUpdatedEntity(EntityManager em) {
        Poll poll = new Poll()
            .question(UPDATED_QUESTION)
            .active(UPDATED_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        // Add required entity
        PollChoice pollChoice;
        if (TestUtil.findAll(em, PollChoice.class).isEmpty()) {
            pollChoice = PollChoiceResourceIT.createUpdatedEntity(em);
            em.persist(pollChoice);
            em.flush();
        } else {
            pollChoice = TestUtil.findAll(em, PollChoice.class).get(0);
        }
        poll.getChoices().add(pollChoice);
        return poll;
    }

    @BeforeEach
    public void initTest() {
        poll = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoll() throws Exception {
        int databaseSizeBeforeCreate = pollRepository.findAll().size();
        // Create the Poll
        PollDTO pollDTO = pollMapper.toDto(poll);
        restPollMockMvc.perform(post("/api/polls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollDTO)))
            .andExpect(status().isCreated());

        // Validate the Poll in the database
        List<Poll> pollList = pollRepository.findAll();
        assertThat(pollList).hasSize(databaseSizeBeforeCreate + 1);
        Poll testPoll = pollList.get(pollList.size() - 1);
        assertThat(testPoll.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testPoll.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testPoll.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPoll.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createPollWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pollRepository.findAll().size();

        // Create the Poll with an existing ID
        poll.setId(1L);
        PollDTO pollDTO = pollMapper.toDto(poll);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPollMockMvc.perform(post("/api/polls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poll in the database
        List<Poll> pollList = pollRepository.findAll();
        assertThat(pollList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollRepository.findAll().size();
        // set the field null
        poll.setQuestion(null);

        // Create the Poll, which fails.
        PollDTO pollDTO = pollMapper.toDto(poll);


        restPollMockMvc.perform(post("/api/polls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollDTO)))
            .andExpect(status().isBadRequest());

        List<Poll> pollList = pollRepository.findAll();
        assertThat(pollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollRepository.findAll().size();
        // set the field null
        poll.setActive(null);

        // Create the Poll, which fails.
        PollDTO pollDTO = pollMapper.toDto(poll);


        restPollMockMvc.perform(post("/api/polls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollDTO)))
            .andExpect(status().isBadRequest());

        List<Poll> pollList = pollRepository.findAll();
        assertThat(pollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollRepository.findAll().size();
        // set the field null
        poll.setCreatedBy(null);

        // Create the Poll, which fails.
        PollDTO pollDTO = pollMapper.toDto(poll);


        restPollMockMvc.perform(post("/api/polls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollDTO)))
            .andExpect(status().isBadRequest());

        List<Poll> pollList = pollRepository.findAll();
        assertThat(pollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollRepository.findAll().size();
        // set the field null
        poll.setCreationDate(null);

        // Create the Poll, which fails.
        PollDTO pollDTO = pollMapper.toDto(poll);


        restPollMockMvc.perform(post("/api/polls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollDTO)))
            .andExpect(status().isBadRequest());

        List<Poll> pollList = pollRepository.findAll();
        assertThat(pollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPolls() throws Exception {
        // Initialize the database
        pollRepository.saveAndFlush(poll);

        // Get all the pollList
        restPollMockMvc.perform(get("/api/polls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poll.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPoll() throws Exception {
        // Initialize the database
        pollRepository.saveAndFlush(poll);

        // Get the poll
        restPollMockMvc.perform(get("/api/polls/{id}", poll.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(poll.getId().intValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPoll() throws Exception {
        // Get the poll
        restPollMockMvc.perform(get("/api/polls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoll() throws Exception {
        // Initialize the database
        pollRepository.saveAndFlush(poll);

        int databaseSizeBeforeUpdate = pollRepository.findAll().size();

        // Update the poll
        Poll updatedPoll = pollRepository.findById(poll.getId()).get();
        // Disconnect from session so that the updates on updatedPoll are not directly saved in db
        em.detach(updatedPoll);
        updatedPoll
            .question(UPDATED_QUESTION)
            .active(UPDATED_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .creationDate(UPDATED_CREATION_DATE);
        PollDTO pollDTO = pollMapper.toDto(updatedPoll);

        restPollMockMvc.perform(put("/api/polls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollDTO)))
            .andExpect(status().isOk());

        // Validate the Poll in the database
        List<Poll> pollList = pollRepository.findAll();
        assertThat(pollList).hasSize(databaseSizeBeforeUpdate);
        Poll testPoll = pollList.get(pollList.size() - 1);
        assertThat(testPoll.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testPoll.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testPoll.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPoll.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPoll() throws Exception {
        int databaseSizeBeforeUpdate = pollRepository.findAll().size();

        // Create the Poll
        PollDTO pollDTO = pollMapper.toDto(poll);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPollMockMvc.perform(put("/api/polls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poll in the database
        List<Poll> pollList = pollRepository.findAll();
        assertThat(pollList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoll() throws Exception {
        // Initialize the database
        pollRepository.saveAndFlush(poll);

        int databaseSizeBeforeDelete = pollRepository.findAll().size();

        // Delete the poll
        restPollMockMvc.perform(delete("/api/polls/{id}", poll.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Poll> pollList = pollRepository.findAll();
        assertThat(pollList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
