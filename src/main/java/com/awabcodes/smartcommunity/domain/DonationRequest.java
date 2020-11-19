package com.awabcodes.smartcommunity.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DonationRequest.
 */
@Entity
@Table(name = "donation_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DonationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cause", nullable = false)
    private String cause;

    @NotNull
    @Column(name = "payment_info", nullable = false)
    private String paymentInfo;

    @NotNull
    @Column(name = "info", nullable = false)
    private String info;

    @NotNull
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @NotNull
    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "amount_raised")
    private Double amountRaised;

    @OneToMany(mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Donation> donations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public DonationRequest cause(String cause) {
        this.cause = cause;
        return this;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public DonationRequest paymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
        return this;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getInfo() {
        return info;
    }

    public DonationRequest info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public DonationRequest totalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getContact() {
        return contact;
    }

    public DonationRequest contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getAmountRaised() {
        return amountRaised;
    }

    public DonationRequest amountRaised(Double amountRaised) {
        this.amountRaised = amountRaised;
        return this;
    }

    public void setAmountRaised(Double amountRaised) {
        this.amountRaised = amountRaised;
    }

    public Set<Donation> getDonations() {
        return donations;
    }

    public DonationRequest donations(Set<Donation> donations) {
        this.donations = donations;
        return this;
    }

    public DonationRequest addDonations(Donation donation) {
        this.donations.add(donation);
        donation.setRequest(this);
        return this;
    }

    public DonationRequest removeDonations(Donation donation) {
        this.donations.remove(donation);
        donation.setRequest(null);
        return this;
    }

    public void setDonations(Set<Donation> donations) {
        this.donations = donations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationRequest)) {
            return false;
        }
        return id != null && id.equals(((DonationRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationRequest{" +
            "id=" + getId() +
            ", cause='" + getCause() + "'" +
            ", paymentInfo='" + getPaymentInfo() + "'" +
            ", info='" + getInfo() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", contact='" + getContact() + "'" +
            ", amountRaised=" + getAmountRaised() +
            "}";
    }
}
