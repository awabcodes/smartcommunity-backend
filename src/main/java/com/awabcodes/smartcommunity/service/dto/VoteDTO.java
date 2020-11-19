package com.awabcodes.smartcommunity.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.awabcodes.smartcommunity.domain.Vote} entity.
 */
public class VoteDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant creationDate;


    private Long userId;

    private String userLogin;

    private Long choiceId;

    private String choiceChoice;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
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

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long pollChoiceId) {
        this.choiceId = pollChoiceId;
    }

    public String getChoiceChoice() {
        return choiceChoice;
    }

    public void setChoiceChoice(String pollChoiceChoice) {
        this.choiceChoice = pollChoiceChoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoteDTO)) {
            return false;
        }

        return id != null && id.equals(((VoteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoteDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", choiceId=" + getChoiceId() +
            ", choiceChoice='" + getChoiceChoice() + "'" +
            "}";
    }
}
