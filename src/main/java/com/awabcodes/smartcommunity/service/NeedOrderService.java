package com.awabcodes.smartcommunity.service;

import com.awabcodes.smartcommunity.domain.NeedOrder;
import com.awabcodes.smartcommunity.repository.NeedOrderRepository;
import com.awabcodes.smartcommunity.service.dto.NeedOrderDTO;
import com.awabcodes.smartcommunity.service.mapper.NeedOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NeedOrder}.
 */
@Service
@Transactional
public class NeedOrderService {

    private final Logger log = LoggerFactory.getLogger(NeedOrderService.class);

    private final NeedOrderRepository needOrderRepository;

    private final NeedOrderMapper needOrderMapper;

    public NeedOrderService(NeedOrderRepository needOrderRepository, NeedOrderMapper needOrderMapper) {
        this.needOrderRepository = needOrderRepository;
        this.needOrderMapper = needOrderMapper;
    }

    /**
     * Save a needOrder.
     *
     * @param needOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public NeedOrderDTO save(NeedOrderDTO needOrderDTO) {
        log.debug("Request to save NeedOrder : {}", needOrderDTO);
        NeedOrder needOrder = needOrderMapper.toEntity(needOrderDTO);
        needOrder = needOrderRepository.save(needOrder);
        return needOrderMapper.toDto(needOrder);
    }

    /**
     * Get all the needOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NeedOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NeedOrders");
        return needOrderRepository.findAll(pageable)
            .map(needOrderMapper::toDto);
    }


    /**
     * Get one needOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NeedOrderDTO> findOne(Long id) {
        log.debug("Request to get NeedOrder : {}", id);
        return needOrderRepository.findById(id)
            .map(needOrderMapper::toDto);
    }

    /**
     * Delete the needOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NeedOrder : {}", id);
        needOrderRepository.deleteById(id);
    }
}
