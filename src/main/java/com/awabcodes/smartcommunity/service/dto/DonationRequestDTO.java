package com.awabcodes.smartcommunity.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.awabcodes.smartcommunity.domain.DonationRequest} entity.
 */
public class DonationRequestDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String cause;

    @NotNull
    private String paymentInfo;

    @NotNull
    private String info;

    @NotNull
    private Double totalAmount;

    @NotNull
    private String contact;

    private Double amountRaised;

    private Boolean active;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getAmountRaised() {
        return amountRaised;
    }

    public void setAmountRaised(Double amountRaised) {
        this.amountRaised = amountRaised;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationRequestDTO)) {
            return false;
        }

        return id != null && id.equals(((DonationRequestDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationRequestDTO{" +
            "id=" + getId() +
            ", cause='" + getCause() + "'" +
            ", paymentInfo='" + getPaymentInfo() + "'" +
            ", info='" + getInfo() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", contact='" + getContact() + "'" +
            ", amountRaised=" + getAmountRaised() +
            ", active='" + isActive() + "'" +
            "}";
    }
}
