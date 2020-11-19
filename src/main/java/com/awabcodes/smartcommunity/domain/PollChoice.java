package com.awabcodes.smartcommunity.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PollChoice.
 */
@Entity
@Table(name = "poll_choice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PollChoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "choice", nullable = false)
    private String choice;

    @OneToMany(mappedBy = "choice")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Vote> votes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "choices", allowSetters = true)
    private Poll poll;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChoice() {
        return choice;
    }

    public PollChoice choice(String choice) {
        this.choice = choice;
        return this;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public PollChoice votes(Set<Vote> votes) {
        this.votes = votes;
        return this;
    }

    public PollChoice addVotes(Vote vote) {
        this.votes.add(vote);
        vote.setChoice(this);
        return this;
    }

    public PollChoice removeVotes(Vote vote) {
        this.votes.remove(vote);
        vote.setChoice(null);
        return this;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Poll getPoll() {
        return poll;
    }

    public PollChoice poll(Poll poll) {
        this.poll = poll;
        return this;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PollChoice)) {
            return false;
        }
        return id != null && id.equals(((PollChoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PollChoice{" +
            "id=" + getId() +
            ", choice='" + getChoice() + "'" +
            "}";
    }
}
