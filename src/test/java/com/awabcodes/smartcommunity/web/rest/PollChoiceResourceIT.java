package com.awabcodes.smartcommunity.web.rest;

import com.awabcodes.smartcommunity.SmartcommunityApp;
import com.awabcodes.smartcommunity.domain.PollChoice;
import com.awabcodes.smartcommunity.domain.Poll;
import com.awabcodes.smartcommunity.repository.PollChoiceRepository;
import com.awabcodes.smartcommunity.service.PollChoiceService;
import com.awabcodes.smartcommunity.service.dto.PollChoiceDTO;
import com.awabcodes.smartcommunity.service.mapper.PollChoiceMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PollChoiceResource} REST controller.
 */
@SpringBootTest(classes = SmartcommunityApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PollChoiceResourceIT {

    private static final String DEFAULT_CHOICE = "AAAAAAAAAA";
    private static final String UPDATED_CHOICE = "BBBBBBBBBB";

    @Autowired
    private PollChoiceRepository pollChoiceRepository;

    @Autowired
    private PollChoiceMapper pollChoiceMapper;

    @Autowired
    private PollChoiceService pollChoiceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPollChoiceMockMvc;

    private PollChoice pollChoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PollChoice createEntity(EntityManager em) {
        PollChoice pollChoice = new PollChoice()
            .choice(DEFAULT_CHOICE);
        // Add required entity
        Poll poll;
        if (TestUtil.findAll(em, Poll.class).isEmpty()) {
            poll = PollResourceIT.createEntity(em);
            em.persist(poll);
            em.flush();
        } else {
            poll = TestUtil.findAll(em, Poll.class).get(0);
        }
        pollChoice.setPoll(poll);
        return pollChoice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PollChoice createUpdatedEntity(EntityManager em) {
        PollChoice pollChoice = new PollChoice()
            .choice(UPDATED_CHOICE);
        // Add required entity
        Poll poll;
        if (TestUtil.findAll(em, Poll.class).isEmpty()) {
            poll = PollResourceIT.createUpdatedEntity(em);
            em.persist(poll);
            em.flush();
        } else {
            poll = TestUtil.findAll(em, Poll.class).get(0);
        }
        pollChoice.setPoll(poll);
        return pollChoice;
    }

    @BeforeEach
    public void initTest() {
        pollChoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createPollChoice() throws Exception {
        int databaseSizeBeforeCreate = pollChoiceRepository.findAll().size();
        // Create the PollChoice
        PollChoiceDTO pollChoiceDTO = pollChoiceMapper.toDto(pollChoice);
        restPollChoiceMockMvc.perform(post("/api/poll-choices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollChoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the PollChoice in the database
        List<PollChoice> pollChoiceList = pollChoiceRepository.findAll();
        assertThat(pollChoiceList).hasSize(databaseSizeBeforeCreate + 1);
        PollChoice testPollChoice = pollChoiceList.get(pollChoiceList.size() - 1);
        assertThat(testPollChoice.getChoice()).isEqualTo(DEFAULT_CHOICE);
    }

    @Test
    @Transactional
    public void createPollChoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pollChoiceRepository.findAll().size();

        // Create the PollChoice with an existing ID
        pollChoice.setId(1L);
        PollChoiceDTO pollChoiceDTO = pollChoiceMapper.toDto(pollChoice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPollChoiceMockMvc.perform(post("/api/poll-choices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollChoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PollChoice in the database
        List<PollChoice> pollChoiceList = pollChoiceRepository.findAll();
        assertThat(pollChoiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkChoiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = pollChoiceRepository.findAll().size();
        // set the field null
        pollChoice.setChoice(null);

        // Create the PollChoice, which fails.
        PollChoiceDTO pollChoiceDTO = pollChoiceMapper.toDto(pollChoice);


        restPollChoiceMockMvc.perform(post("/api/poll-choices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollChoiceDTO)))
            .andExpect(status().isBadRequest());

        List<PollChoice> pollChoiceList = pollChoiceRepository.findAll();
        assertThat(pollChoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPollChoices() throws Exception {
        // Initialize the database
        pollChoiceRepository.saveAndFlush(pollChoice);

        // Get all the pollChoiceList
        restPollChoiceMockMvc.perform(get("/api/poll-choices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pollChoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].choice").value(hasItem(DEFAULT_CHOICE)));
    }
    
    @Test
    @Transactional
    public void getPollChoice() throws Exception {
        // Initialize the database
        pollChoiceRepository.saveAndFlush(pollChoice);

        // Get the pollChoice
        restPollChoiceMockMvc.perform(get("/api/poll-choices/{id}", pollChoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pollChoice.getId().intValue()))
            .andExpect(jsonPath("$.choice").value(DEFAULT_CHOICE));
    }
    @Test
    @Transactional
    public void getNonExistingPollChoice() throws Exception {
        // Get the pollChoice
        restPollChoiceMockMvc.perform(get("/api/poll-choices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePollChoice() throws Exception {
        // Initialize the database
        pollChoiceRepository.saveAndFlush(pollChoice);

        int databaseSizeBeforeUpdate = pollChoiceRepository.findAll().size();

        // Update the pollChoice
        PollChoice updatedPollChoice = pollChoiceRepository.findById(pollChoice.getId()).get();
        // Disconnect from session so that the updates on updatedPollChoice are not directly saved in db
        em.detach(updatedPollChoice);
        updatedPollChoice
            .choice(UPDATED_CHOICE);
        PollChoiceDTO pollChoiceDTO = pollChoiceMapper.toDto(updatedPollChoice);

        restPollChoiceMockMvc.perform(put("/api/poll-choices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollChoiceDTO)))
            .andExpect(status().isOk());

        // Validate the PollChoice in the database
        List<PollChoice> pollChoiceList = pollChoiceRepository.findAll();
        assertThat(pollChoiceList).hasSize(databaseSizeBeforeUpdate);
        PollChoice testPollChoice = pollChoiceList.get(pollChoiceList.size() - 1);
        assertThat(testPollChoice.getChoice()).isEqualTo(UPDATED_CHOICE);
    }

    @Test
    @Transactional
    public void updateNonExistingPollChoice() throws Exception {
        int databaseSizeBeforeUpdate = pollChoiceRepository.findAll().size();

        // Create the PollChoice
        PollChoiceDTO pollChoiceDTO = pollChoiceMapper.toDto(pollChoice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPollChoiceMockMvc.perform(put("/api/poll-choices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pollChoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PollChoice in the database
        List<PollChoice> pollChoiceList = pollChoiceRepository.findAll();
        assertThat(pollChoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePollChoice() throws Exception {
        // Initialize the database
        pollChoiceRepository.saveAndFlush(pollChoice);

        int databaseSizeBeforeDelete = pollChoiceRepository.findAll().size();

        // Delete the pollChoice
        restPollChoiceMockMvc.perform(delete("/api/poll-choices/{id}", pollChoice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PollChoice> pollChoiceList = pollChoiceRepository.findAll();
        assertThat(pollChoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
