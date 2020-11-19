package com.awabcodes.smartcommunity.service.mapper;


import com.awabcodes.smartcommunity.domain.*;
import com.awabcodes.smartcommunity.service.dto.PollChoiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PollChoice} and its DTO {@link PollChoiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {PollMapper.class})
public interface PollChoiceMapper extends EntityMapper<PollChoiceDTO, PollChoice> {

    @Mapping(source = "poll.id", target = "pollId")
    @Mapping(source = "poll.question", target = "pollQuestion")
    PollChoiceDTO toDto(PollChoice pollChoice);

    @Mapping(target = "votes", ignore = true)
    @Mapping(target = "removeVotes", ignore = true)
    @Mapping(source = "pollId", target = "poll")
    PollChoice toEntity(PollChoiceDTO pollChoiceDTO);

    default PollChoice fromId(Long id) {
        if (id == null) {
            return null;
        }
        PollChoice pollChoice = new PollChoice();
        pollChoice.setId(id);
        return pollChoice;
    }
}
