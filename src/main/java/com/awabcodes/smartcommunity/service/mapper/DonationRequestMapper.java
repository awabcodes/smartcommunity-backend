package com.awabcodes.smartcommunity.service.mapper;


import com.awabcodes.smartcommunity.domain.*;
import com.awabcodes.smartcommunity.service.dto.DonationRequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DonationRequest} and its DTO {@link DonationRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DonationRequestMapper extends EntityMapper<DonationRequestDTO, DonationRequest> {


    @Mapping(target = "donations", ignore = true)
    @Mapping(target = "removeDonations", ignore = true)
    DonationRequest toEntity(DonationRequestDTO donationRequestDTO);

    default DonationRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        DonationRequest donationRequest = new DonationRequest();
        donationRequest.setId(id);
        return donationRequest;
    }
}
