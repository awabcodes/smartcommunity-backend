package com.awabcodes.smartcommunity.web.rest;

import com.awabcodes.smartcommunity.SmartcommunityApp;
import com.awabcodes.smartcommunity.domain.Need;
import com.awabcodes.smartcommunity.repository.NeedRepository;
import com.awabcodes.smartcommunity.service.NeedService;
import com.awabcodes.smartcommunity.service.dto.NeedDTO;
import com.awabcodes.smartcommunity.service.mapper.NeedMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NeedResource} REST controller.
 */
@SpringBootTest(classes = SmartcommunityApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NeedResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AVAILABLE = false;
    private static final Boolean UPDATED_AVAILABLE = true;

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY = "BBBBBBBBBB";

    @Autowired
    private NeedRepository needRepository;

    @Autowired
    private NeedMapper needMapper;

    @Autowired
    private NeedService needService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNeedMockMvc;

    private Need need;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Need createEntity(EntityManager em) {
        Need need = new Need()
            .name(DEFAULT_NAME)
            .info(DEFAULT_INFO)
            .available(DEFAULT_AVAILABLE)
            .contact(DEFAULT_CONTACT)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .quantity(DEFAULT_QUANTITY);
        return need;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Need createUpdatedEntity(EntityManager em) {
        Need need = new Need()
            .name(UPDATED_NAME)
            .info(UPDATED_INFO)
            .available(UPDATED_AVAILABLE)
            .contact(UPDATED_CONTACT)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .quantity(UPDATED_QUANTITY);
        return need;
    }

    @BeforeEach
    public void initTest() {
        need = createEntity(em);
    }

    @Test
    @Transactional
    public void createNeed() throws Exception {
        int databaseSizeBeforeCreate = needRepository.findAll().size();
        // Create the Need
        NeedDTO needDTO = needMapper.toDto(need);
        restNeedMockMvc.perform(post("/api/needs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needDTO)))
            .andExpect(status().isCreated());

        // Validate the Need in the database
        List<Need> needList = needRepository.findAll();
        assertThat(needList).hasSize(databaseSizeBeforeCreate + 1);
        Need testNeed = needList.get(needList.size() - 1);
        assertThat(testNeed.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNeed.getInfo()).isEqualTo(DEFAULT_INFO);
        assertThat(testNeed.isAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testNeed.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testNeed.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testNeed.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testNeed.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createNeedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = needRepository.findAll().size();

        // Create the Need with an existing ID
        need.setId(1L);
        NeedDTO needDTO = needMapper.toDto(need);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNeedMockMvc.perform(post("/api/needs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Need in the database
        List<Need> needList = needRepository.findAll();
        assertThat(needList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = needRepository.findAll().size();
        // set the field null
        need.setName(null);

        // Create the Need, which fails.
        NeedDTO needDTO = needMapper.toDto(need);


        restNeedMockMvc.perform(post("/api/needs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needDTO)))
            .andExpect(status().isBadRequest());

        List<Need> needList = needRepository.findAll();
        assertThat(needList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = needRepository.findAll().size();
        // set the field null
        need.setInfo(null);

        // Create the Need, which fails.
        NeedDTO needDTO = needMapper.toDto(need);


        restNeedMockMvc.perform(post("/api/needs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needDTO)))
            .andExpect(status().isBadRequest());

        List<Need> needList = needRepository.findAll();
        assertThat(needList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = needRepository.findAll().size();
        // set the field null
        need.setAvailable(null);

        // Create the Need, which fails.
        NeedDTO needDTO = needMapper.toDto(need);


        restNeedMockMvc.perform(post("/api/needs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needDTO)))
            .andExpect(status().isBadRequest());

        List<Need> needList = needRepository.findAll();
        assertThat(needList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = needRepository.findAll().size();
        // set the field null
        need.setContact(null);

        // Create the Need, which fails.
        NeedDTO needDTO = needMapper.toDto(need);


        restNeedMockMvc.perform(post("/api/needs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needDTO)))
            .andExpect(status().isBadRequest());

        List<Need> needList = needRepository.findAll();
        assertThat(needList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNeeds() throws Exception {
        // Initialize the database
        needRepository.saveAndFlush(need);

        // Get all the needList
        restNeedMockMvc.perform(get("/api/needs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(need.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }
    
    @Test
    @Transactional
    public void getNeed() throws Exception {
        // Initialize the database
        needRepository.saveAndFlush(need);

        // Get the need
        restNeedMockMvc.perform(get("/api/needs/{id}", need.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(need.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }
    @Test
    @Transactional
    public void getNonExistingNeed() throws Exception {
        // Get the need
        restNeedMockMvc.perform(get("/api/needs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNeed() throws Exception {
        // Initialize the database
        needRepository.saveAndFlush(need);

        int databaseSizeBeforeUpdate = needRepository.findAll().size();

        // Update the need
        Need updatedNeed = needRepository.findById(need.getId()).get();
        // Disconnect from session so that the updates on updatedNeed are not directly saved in db
        em.detach(updatedNeed);
        updatedNeed
            .name(UPDATED_NAME)
            .info(UPDATED_INFO)
            .available(UPDATED_AVAILABLE)
            .contact(UPDATED_CONTACT)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .quantity(UPDATED_QUANTITY);
        NeedDTO needDTO = needMapper.toDto(updatedNeed);

        restNeedMockMvc.perform(put("/api/needs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needDTO)))
            .andExpect(status().isOk());

        // Validate the Need in the database
        List<Need> needList = needRepository.findAll();
        assertThat(needList).hasSize(databaseSizeBeforeUpdate);
        Need testNeed = needList.get(needList.size() - 1);
        assertThat(testNeed.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNeed.getInfo()).isEqualTo(UPDATED_INFO);
        assertThat(testNeed.isAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testNeed.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testNeed.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testNeed.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testNeed.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingNeed() throws Exception {
        int databaseSizeBeforeUpdate = needRepository.findAll().size();

        // Create the Need
        NeedDTO needDTO = needMapper.toDto(need);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNeedMockMvc.perform(put("/api/needs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Need in the database
        List<Need> needList = needRepository.findAll();
        assertThat(needList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNeed() throws Exception {
        // Initialize the database
        needRepository.saveAndFlush(need);

        int databaseSizeBeforeDelete = needRepository.findAll().size();

        // Delete the need
        restNeedMockMvc.perform(delete("/api/needs/{id}", need.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Need> needList = needRepository.findAll();
        assertThat(needList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
