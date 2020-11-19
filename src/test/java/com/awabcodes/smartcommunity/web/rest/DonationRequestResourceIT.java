package com.awabcodes.smartcommunity.web.rest;

import com.awabcodes.smartcommunity.SmartcommunityApp;
import com.awabcodes.smartcommunity.domain.DonationRequest;
import com.awabcodes.smartcommunity.repository.DonationRequestRepository;
import com.awabcodes.smartcommunity.service.DonationRequestService;
import com.awabcodes.smartcommunity.service.dto.DonationRequestDTO;
import com.awabcodes.smartcommunity.service.mapper.DonationRequestMapper;

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
 * Integration tests for the {@link DonationRequestResource} REST controller.
 */
@SpringBootTest(classes = SmartcommunityApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DonationRequestResourceIT {

    private static final String DEFAULT_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT_RAISED = 1D;
    private static final Double UPDATED_AMOUNT_RAISED = 2D;

    @Autowired
    private DonationRequestRepository donationRequestRepository;

    @Autowired
    private DonationRequestMapper donationRequestMapper;

    @Autowired
    private DonationRequestService donationRequestService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonationRequestMockMvc;

    private DonationRequest donationRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationRequest createEntity(EntityManager em) {
        DonationRequest donationRequest = new DonationRequest()
            .cause(DEFAULT_CAUSE)
            .paymentInfo(DEFAULT_PAYMENT_INFO)
            .info(DEFAULT_INFO)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .contact(DEFAULT_CONTACT)
            .amountRaised(DEFAULT_AMOUNT_RAISED);
        return donationRequest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationRequest createUpdatedEntity(EntityManager em) {
        DonationRequest donationRequest = new DonationRequest()
            .cause(UPDATED_CAUSE)
            .paymentInfo(UPDATED_PAYMENT_INFO)
            .info(UPDATED_INFO)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .contact(UPDATED_CONTACT)
            .amountRaised(UPDATED_AMOUNT_RAISED);
        return donationRequest;
    }

    @BeforeEach
    public void initTest() {
        donationRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createDonationRequest() throws Exception {
        int databaseSizeBeforeCreate = donationRequestRepository.findAll().size();
        // Create the DonationRequest
        DonationRequestDTO donationRequestDTO = donationRequestMapper.toDto(donationRequest);
        restDonationRequestMockMvc.perform(post("/api/donation-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donationRequestDTO)))
            .andExpect(status().isCreated());

        // Validate the DonationRequest in the database
        List<DonationRequest> donationRequestList = donationRequestRepository.findAll();
        assertThat(donationRequestList).hasSize(databaseSizeBeforeCreate + 1);
        DonationRequest testDonationRequest = donationRequestList.get(donationRequestList.size() - 1);
        assertThat(testDonationRequest.getCause()).isEqualTo(DEFAULT_CAUSE);
        assertThat(testDonationRequest.getPaymentInfo()).isEqualTo(DEFAULT_PAYMENT_INFO);
        assertThat(testDonationRequest.getInfo()).isEqualTo(DEFAULT_INFO);
        assertThat(testDonationRequest.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testDonationRequest.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testDonationRequest.getAmountRaised()).isEqualTo(DEFAULT_AMOUNT_RAISED);
    }

    @Test
    @Transactional
    public void createDonationRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = donationRequestRepository.findAll().size();

        // Create the DonationRequest with an existing ID
        donationRequest.setId(1L);
        DonationRequestDTO donationRequestDTO = donationRequestMapper.toDto(donationRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonationRequestMockMvc.perform(post("/api/donation-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donationRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DonationRequest in the database
        List<DonationRequest> donationRequestList = donationRequestRepository.findAll();
        assertThat(donationRequestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCauseIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRequestRepository.findAll().size();
        // set the field null
        donationRequest.setCause(null);

        // Create the DonationRequest, which fails.
        DonationRequestDTO donationRequestDTO = donationRequestMapper.toDto(donationRequest);


        restDonationRequestMockMvc.perform(post("/api/donation-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donationRequestDTO)))
            .andExpect(status().isBadRequest());

        List<DonationRequest> donationRequestList = donationRequestRepository.findAll();
        assertThat(donationRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRequestRepository.findAll().size();
        // set the field null
        donationRequest.setPaymentInfo(null);

        // Create the DonationRequest, which fails.
        DonationRequestDTO donationRequestDTO = donationRequestMapper.toDto(donationRequest);


        restDonationRequestMockMvc.perform(post("/api/donation-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donationRequestDTO)))
            .andExpect(status().isBadRequest());

        List<DonationRequest> donationRequestList = donationRequestRepository.findAll();
        assertThat(donationRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRequestRepository.findAll().size();
        // set the field null
        donationRequest.setInfo(null);

        // Create the DonationRequest, which fails.
        DonationRequestDTO donationRequestDTO = donationRequestMapper.toDto(donationRequest);


        restDonationRequestMockMvc.perform(post("/api/donation-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donationRequestDTO)))
            .andExpect(status().isBadRequest());

        List<DonationRequest> donationRequestList = donationRequestRepository.findAll();
        assertThat(donationRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRequestRepository.findAll().size();
        // set the field null
        donationRequest.setTotalAmount(null);

        // Create the DonationRequest, which fails.
        DonationRequestDTO donationRequestDTO = donationRequestMapper.toDto(donationRequest);


        restDonationRequestMockMvc.perform(post("/api/donation-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donationRequestDTO)))
            .andExpect(status().isBadRequest());

        List<DonationRequest> donationRequestList = donationRequestRepository.findAll();
        assertThat(donationRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRequestRepository.findAll().size();
        // set the field null
        donationRequest.setContact(null);

        // Create the DonationRequest, which fails.
        DonationRequestDTO donationRequestDTO = donationRequestMapper.toDto(donationRequest);


        restDonationRequestMockMvc.perform(post("/api/donation-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donationRequestDTO)))
            .andExpect(status().isBadRequest());

        List<DonationRequest> donationRequestList = donationRequestRepository.findAll();
        assertThat(donationRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDonationRequests() throws Exception {
        // Initialize the database
        donationRequestRepository.saveAndFlush(donationRequest);

        // Get all the donationRequestList
        restDonationRequestMockMvc.perform(get("/api/donation-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donationRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE)))
            .andExpect(jsonPath("$.[*].paymentInfo").value(hasItem(DEFAULT_PAYMENT_INFO)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].amountRaised").value(hasItem(DEFAULT_AMOUNT_RAISED.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getDonationRequest() throws Exception {
        // Initialize the database
        donationRequestRepository.saveAndFlush(donationRequest);

        // Get the donationRequest
        restDonationRequestMockMvc.perform(get("/api/donation-requests/{id}", donationRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donationRequest.getId().intValue()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE))
            .andExpect(jsonPath("$.paymentInfo").value(DEFAULT_PAYMENT_INFO))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.amountRaised").value(DEFAULT_AMOUNT_RAISED.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDonationRequest() throws Exception {
        // Get the donationRequest
        restDonationRequestMockMvc.perform(get("/api/donation-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonationRequest() throws Exception {
        // Initialize the database
        donationRequestRepository.saveAndFlush(donationRequest);

        int databaseSizeBeforeUpdate = donationRequestRepository.findAll().size();

        // Update the donationRequest
        DonationRequest updatedDonationRequest = donationRequestRepository.findById(donationRequest.getId()).get();
        // Disconnect from session so that the updates on updatedDonationRequest are not directly saved in db
        em.detach(updatedDonationRequest);
        updatedDonationRequest
            .cause(UPDATED_CAUSE)
            .paymentInfo(UPDATED_PAYMENT_INFO)
            .info(UPDATED_INFO)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .contact(UPDATED_CONTACT)
            .amountRaised(UPDATED_AMOUNT_RAISED);
        DonationRequestDTO donationRequestDTO = donationRequestMapper.toDto(updatedDonationRequest);

        restDonationRequestMockMvc.perform(put("/api/donation-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donationRequestDTO)))
            .andExpect(status().isOk());

        // Validate the DonationRequest in the database
        List<DonationRequest> donationRequestList = donationRequestRepository.findAll();
        assertThat(donationRequestList).hasSize(databaseSizeBeforeUpdate);
        DonationRequest testDonationRequest = donationRequestList.get(donationRequestList.size() - 1);
        assertThat(testDonationRequest.getCause()).isEqualTo(UPDATED_CAUSE);
        assertThat(testDonationRequest.getPaymentInfo()).isEqualTo(UPDATED_PAYMENT_INFO);
        assertThat(testDonationRequest.getInfo()).isEqualTo(UPDATED_INFO);
        assertThat(testDonationRequest.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testDonationRequest.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testDonationRequest.getAmountRaised()).isEqualTo(UPDATED_AMOUNT_RAISED);
    }

    @Test
    @Transactional
    public void updateNonExistingDonationRequest() throws Exception {
        int databaseSizeBeforeUpdate = donationRequestRepository.findAll().size();

        // Create the DonationRequest
        DonationRequestDTO donationRequestDTO = donationRequestMapper.toDto(donationRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationRequestMockMvc.perform(put("/api/donation-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donationRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DonationRequest in the database
        List<DonationRequest> donationRequestList = donationRequestRepository.findAll();
        assertThat(donationRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDonationRequest() throws Exception {
        // Initialize the database
        donationRequestRepository.saveAndFlush(donationRequest);

        int databaseSizeBeforeDelete = donationRequestRepository.findAll().size();

        // Delete the donationRequest
        restDonationRequestMockMvc.perform(delete("/api/donation-requests/{id}", donationRequest.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DonationRequest> donationRequestList = donationRequestRepository.findAll();
        assertThat(donationRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
