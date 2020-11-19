package com.awabcodes.smartcommunity.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.awabcodes.smartcommunity.domain.Need} entity.
 */
public class NeedDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String info;

    @NotNull
    private Boolean available;

    @NotNull
    private String contact;

    
    @Lob
    private byte[] image;

    private String imageContentType;
    private String quantity;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NeedDTO)) {
            return false;
        }

        return id != null && id.equals(((NeedDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NeedDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", info='" + getInfo() + "'" +
            ", available='" + isAvailable() + "'" +
            ", contact='" + getContact() + "'" +
            ", image='" + getImage() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }
}
