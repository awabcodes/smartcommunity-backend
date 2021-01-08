package com.awabcodes.smartcommunity.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.awabcodes.smartcommunity.domain.Poll} entity.
 */
public class PollDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String question;

    @NotNull
    private Boolean active;

    @NotNull
    private String createdBy;

    @NotNull
    private Instant creationDate;

    private Set<UserDTO> users = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PollDTO)) {
            return false;
        }

        return id != null && id.equals(((PollDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PollDTO{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", active='" + isActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", users='" + getUsers() + "'" +
            "}";
    }
}
