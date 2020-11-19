package com.awabcodes.smartcommunity.service.mapper;


import com.awabcodes.smartcommunity.domain.*;
import com.awabcodes.smartcommunity.service.dto.DonationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Donation} and its DTO {@link DonationDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, DonationRequestMapper.class})
public interface DonationMapper extends EntityMapper<DonationDTO, Donation> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "request.id", target = "requestId")
    @Mapping(source = "request.cause", target = "requestCause")
    DonationDTO toDto(Donation donation);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "requestId", target = "request")
    Donation toEntity(DonationDTO donationDTO);

    default Donation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Donation donation = new Donation();
        donation.setId(id);
        return donation;
    }
}
