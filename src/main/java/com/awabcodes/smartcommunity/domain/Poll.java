package com.awabcodes.smartcommunity.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Poll.
 */
@Entity
@Table(name = "poll")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Poll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "question", nullable = false)
    private String question;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @OneToMany(mappedBy = "poll")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PollChoice> choices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public Poll question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean isActive() {
        return active;
    }

    public Poll active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Poll createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Poll creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Set<PollChoice> getChoices() {
        return choices;
    }

    public Poll choices(Set<PollChoice> pollChoices) {
        this.choices = pollChoices;
        return this;
    }

    public Poll addChoices(PollChoice pollChoice) {
        this.choices.add(pollChoice);
        pollChoice.setPoll(this);
        return this;
    }

    public Poll removeChoices(PollChoice pollChoice) {
        this.choices.remove(pollChoice);
        pollChoice.setPoll(null);
        return this;
    }

    public void setChoices(Set<PollChoice> pollChoices) {
        this.choices = pollChoices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Poll)) {
            return false;
        }
        return id != null && id.equals(((Poll) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Poll{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", active='" + isActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
