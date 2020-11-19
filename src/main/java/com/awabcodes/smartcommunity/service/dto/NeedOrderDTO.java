package com.awabcodes.smartcommunity.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.awabcodes.smartcommunity.domain.NeedOrder} entity.
 */
public class NeedOrderDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String quantity;

    private String note;


    private Long userId;

    private String userLogin;

    private Long needId;

    private String needName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Long getNeedId() {
        return needId;
    }

    public void setNeedId(Long needId) {
        this.needId = needId;
    }

    public String getNeedName() {
        return needName;
    }

    public void setNeedName(String needName) {
        this.needName = needName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NeedOrderDTO)) {
            return false;
        }

        return id != null && id.equals(((NeedOrderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NeedOrderDTO{" +
            "id=" + getId() +
            ", quantity='" + getQuantity() + "'" +
            ", note='" + getNote() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", needId=" + getNeedId() +
            ", needName='" + getNeedName() + "'" +
            "}";
    }
}
