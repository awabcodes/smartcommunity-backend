package com.awabcodes.smartcommunity.service.mapper;


import com.awabcodes.smartcommunity.domain.*;
import com.awabcodes.smartcommunity.service.dto.NeedOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NeedOrder} and its DTO {@link NeedOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, NeedMapper.class})
public interface NeedOrderMapper extends EntityMapper<NeedOrderDTO, NeedOrder> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "need.id", target = "needId")
    @Mapping(source = "need.name", target = "needName")
    NeedOrderDTO toDto(NeedOrder needOrder);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "needId", target = "need")
    NeedOrder toEntity(NeedOrderDTO needOrderDTO);

    default NeedOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        NeedOrder needOrder = new NeedOrder();
        needOrder.setId(id);
        return needOrder;
    }
}
