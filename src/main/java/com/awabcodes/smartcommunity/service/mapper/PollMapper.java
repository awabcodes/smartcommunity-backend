package com.awabcodes.smartcommunity.service.mapper;


import com.awabcodes.smartcommunity.domain.*;
import com.awabcodes.smartcommunity.service.dto.PollDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Poll} and its DTO {@link PollDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PollMapper extends EntityMapper<PollDTO, Poll> {


    @Mapping(target = "choices", ignore = true)
    @Mapping(target = "removeChoices", ignore = true)
    Poll toEntity(PollDTO pollDTO);

    default Poll fromId(Long id) {
        if (id == null) {
            return null;
        }
        Poll poll = new Poll();
        poll.setId(id);
        return poll;
    }
}
