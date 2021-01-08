package com.awabcodes.smartcommunity.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Donation.
 */
@Entity
@Table(name = "donation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Donation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "receipt_number")
    private String receiptNumber;

    @Column(name = "collected")
    private Boolean collected;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "donations", allowSetters = true)
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "donations", allowSetters = true)
    private DonationRequest request;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public Donation amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public Donation receiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
        return this;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Boolean isCollected() {
        return collected;
    }

    public Donation collected(Boolean collected) {
        this.collected = collected;
        return this;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public User getUser() {
        return user;
    }

    public Donation user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DonationRequest getRequest() {
        return request;
    }

    public Donation request(DonationRequest donationRequest) {
        this.request = donationRequest;
        return this;
    }

    public void setRequest(DonationRequest donationRequest) {
        this.request = donationRequest;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Donation)) {
            return false;
        }
        return id != null && id.equals(((Donation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Donation{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", receiptNumber='" + getReceiptNumber() + "'" +
            ", collected='" + isCollected() + "'" +
            "}";
    }
}
