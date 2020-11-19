package com.awabcodes.smartcommunity.service.mapper;


import com.awabcodes.smartcommunity.domain.*;
import com.awabcodes.smartcommunity.service.dto.VoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vote} and its DTO {@link VoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PollChoiceMapper.class})
public interface VoteMapper extends EntityMapper<VoteDTO, Vote> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "choice.id", target = "choiceId")
    @Mapping(source = "choice.choice", target = "choiceChoice")
    VoteDTO toDto(Vote vote);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "choiceId", target = "choice")
    Vote toEntity(VoteDTO voteDTO);

    default Vote fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vote vote = new Vote();
        vote.setId(id);
        return vote;
    }
}
