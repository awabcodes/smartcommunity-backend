package com.awabcodes.smartcommunity.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.awabcodes.smartcommunity.domain.Donation} entity.
 */
public class DonationDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double amount;

    private String receiptNumber;


    private Long userId;

    private String userLogin;

    private Long requestId;

    private String requestCause;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long donationRequestId) {
        this.requestId = donationRequestId;
    }

    public String getRequestCause() {
        return requestCause;
    }

    public void setRequestCause(String donationRequestCause) {
        this.requestCause = donationRequestCause;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationDTO)) {
            return false;
        }

        return id != null && id.equals(((DonationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", receiptNumber='" + getReceiptNumber() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", requestId=" + getRequestId() +
            ", requestCause='" + getRequestCause() + "'" +
            "}";
    }
}
