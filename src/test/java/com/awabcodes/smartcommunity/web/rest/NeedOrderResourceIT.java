package com.awabcodes.smartcommunity.web.rest;

import com.awabcodes.smartcommunity.SmartcommunityApp;
import com.awabcodes.smartcommunity.domain.NeedOrder;
import com.awabcodes.smartcommunity.domain.User;
import com.awabcodes.smartcommunity.domain.Need;
import com.awabcodes.smartcommunity.repository.NeedOrderRepository;
import com.awabcodes.smartcommunity.service.NeedOrderService;
import com.awabcodes.smartcommunity.service.dto.NeedOrderDTO;
import com.awabcodes.smartcommunity.service.mapper.NeedOrderMapper;

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
 * Integration tests for the {@link NeedOrderResource} REST controller.
 */
@SpringBootTest(classes = SmartcommunityApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NeedOrderResourceIT {

    private static final String DEFAULT_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private NeedOrderRepository needOrderRepository;

    @Autowired
    private NeedOrderMapper needOrderMapper;

    @Autowired
    private NeedOrderService needOrderService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNeedOrderMockMvc;

    private NeedOrder needOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NeedOrder createEntity(EntityManager em) {
        NeedOrder needOrder = new NeedOrder()
            .quantity(DEFAULT_QUANTITY)
            .note(DEFAULT_NOTE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        needOrder.setUser(user);
        // Add required entity
        Need need;
        if (TestUtil.findAll(em, Need.class).isEmpty()) {
            need = NeedResourceIT.createEntity(em);
            em.persist(need);
            em.flush();
        } else {
            need = TestUtil.findAll(em, Need.class).get(0);
        }
        needOrder.setNeed(need);
        return needOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NeedOrder createUpdatedEntity(EntityManager em) {
        NeedOrder needOrder = new NeedOrder()
            .quantity(UPDATED_QUANTITY)
            .note(UPDATED_NOTE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        needOrder.setUser(user);
        // Add required entity
        Need need;
        if (TestUtil.findAll(em, Need.class).isEmpty()) {
            need = NeedResourceIT.createUpdatedEntity(em);
            em.persist(need);
            em.flush();
        } else {
            need = TestUtil.findAll(em, Need.class).get(0);
        }
        needOrder.setNeed(need);
        return needOrder;
    }

    @BeforeEach
    public void initTest() {
        needOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createNeedOrder() throws Exception {
        int databaseSizeBeforeCreate = needOrderRepository.findAll().size();
        // Create the NeedOrder
        NeedOrderDTO needOrderDTO = needOrderMapper.toDto(needOrder);
        restNeedOrderMockMvc.perform(post("/api/need-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the NeedOrder in the database
        List<NeedOrder> needOrderList = needOrderRepository.findAll();
        assertThat(needOrderList).hasSize(databaseSizeBeforeCreate + 1);
        NeedOrder testNeedOrder = needOrderList.get(needOrderList.size() - 1);
        assertThat(testNeedOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testNeedOrder.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createNeedOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = needOrderRepository.findAll().size();

        // Create the NeedOrder with an existing ID
        needOrder.setId(1L);
        NeedOrderDTO needOrderDTO = needOrderMapper.toDto(needOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNeedOrderMockMvc.perform(post("/api/need-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NeedOrder in the database
        List<NeedOrder> needOrderList = needOrderRepository.findAll();
        assertThat(needOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = needOrderRepository.findAll().size();
        // set the field null
        needOrder.setQuantity(null);

        // Create the NeedOrder, which fails.
        NeedOrderDTO needOrderDTO = needOrderMapper.toDto(needOrder);


        restNeedOrderMockMvc.perform(post("/api/need-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needOrderDTO)))
            .andExpect(status().isBadRequest());

        List<NeedOrder> needOrderList = needOrderRepository.findAll();
        assertThat(needOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNeedOrders() throws Exception {
        // Initialize the database
        needOrderRepository.saveAndFlush(needOrder);

        // Get all the needOrderList
        restNeedOrderMockMvc.perform(get("/api/need-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(needOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getNeedOrder() throws Exception {
        // Initialize the database
        needOrderRepository.saveAndFlush(needOrder);

        // Get the needOrder
        restNeedOrderMockMvc.perform(get("/api/need-orders/{id}", needOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(needOrder.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingNeedOrder() throws Exception {
        // Get the needOrder
        restNeedOrderMockMvc.perform(get("/api/need-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNeedOrder() throws Exception {
        // Initialize the database
        needOrderRepository.saveAndFlush(needOrder);

        int databaseSizeBeforeUpdate = needOrderRepository.findAll().size();

        // Update the needOrder
        NeedOrder updatedNeedOrder = needOrderRepository.findById(needOrder.getId()).get();
        // Disconnect from session so that the updates on updatedNeedOrder are not directly saved in db
        em.detach(updatedNeedOrder);
        updatedNeedOrder
            .quantity(UPDATED_QUANTITY)
            .note(UPDATED_NOTE);
        NeedOrderDTO needOrderDTO = needOrderMapper.toDto(updatedNeedOrder);

        restNeedOrderMockMvc.perform(put("/api/need-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needOrderDTO)))
            .andExpect(status().isOk());

        // Validate the NeedOrder in the database
        List<NeedOrder> needOrderList = needOrderRepository.findAll();
        assertThat(needOrderList).hasSize(databaseSizeBeforeUpdate);
        NeedOrder testNeedOrder = needOrderList.get(needOrderList.size() - 1);
        assertThat(testNeedOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testNeedOrder.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingNeedOrder() throws Exception {
        int databaseSizeBeforeUpdate = needOrderRepository.findAll().size();

        // Create the NeedOrder
        NeedOrderDTO needOrderDTO = needOrderMapper.toDto(needOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNeedOrderMockMvc.perform(put("/api/need-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(needOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NeedOrder in the database
        List<NeedOrder> needOrderList = needOrderRepository.findAll();
        assertThat(needOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNeedOrder() throws Exception {
        // Initialize the database
        needOrderRepository.saveAndFlush(needOrder);

        int databaseSizeBeforeDelete = needOrderRepository.findAll().size();

        // Delete the needOrder
        restNeedOrderMockMvc.perform(delete("/api/need-orders/{id}", needOrder.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NeedOrder> needOrderList = needOrderRepository.findAll();
        assertThat(needOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
