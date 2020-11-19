package com.awabcodes.smartcommunity.service.mapper;


import com.awabcodes.smartcommunity.domain.*;
import com.awabcodes.smartcommunity.service.dto.FeedbackDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Feedback} and its DTO {@link FeedbackDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface FeedbackMapper extends EntityMapper<FeedbackDTO, Feedback> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    FeedbackDTO toDto(Feedback feedback);

    @Mapping(source = "userId", target = "user")
    Feedback toEntity(FeedbackDTO feedbackDTO);

    default Feedback fromId(Long id) {
        if (id == null) {
            return null;
        }
        Feedback feedback = new Feedback();
        feedback.setId(id);
        return feedback;
    }
}
