package com.awabcodes.smartcommunity.service.mapper;


import com.awabcodes.smartcommunity.domain.*;
import com.awabcodes.smartcommunity.service.dto.NeedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Need} and its DTO {@link NeedDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NeedMapper extends EntityMapper<NeedDTO, Need> {


    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "removeOrders", ignore = true)
    Need toEntity(NeedDTO needDTO);

    default Need fromId(Long id) {
        if (id == null) {
            return null;
        }
        Need need = new Need();
        need.setId(id);
        return need;
    }
}
