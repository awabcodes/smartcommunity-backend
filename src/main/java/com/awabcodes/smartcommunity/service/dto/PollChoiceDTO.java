package com.awabcodes.smartcommunity.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.awabcodes.smartcommunity.domain.PollChoice} entity.
 */
public class PollChoiceDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String choice;


    private Long pollId;

    private String pollQuestion;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public String getPollQuestion() {
        return pollQuestion;
    }

    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PollChoiceDTO)) {
            return false;
        }

        return id != null && id.equals(((PollChoiceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PollChoiceDTO{" +
            "id=" + getId() +
            ", choice='" + getChoice() + "'" +
            ", pollId=" + getPollId() +
            ", pollQuestion='" + getPollQuestion() + "'" +
            "}";
    }
}
