package com.awabcodes.smartcommunity.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import com.awabcodes.smartcommunity.domain.enumeration.AnnouncementType;

/**
 * A DTO for the {@link com.awabcodes.smartcommunity.domain.Announcement} entity.
 */
public class AnnouncementDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 20)
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Instant creationDate;

    @NotNull
    private AnnouncementType type;

    private String location;

    
    @Lob
    private byte[] image;

    private String imageContentType;
    private String contact;

    private Instant announcementDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public AnnouncementType getType() {
        return type;
    }

    public void setType(AnnouncementType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Instant getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementDate(Instant announcementDate) {
        this.announcementDate = announcementDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnouncementDTO)) {
            return false;
        }

        return id != null && id.equals(((AnnouncementDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnnouncementDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", type='" + getType() + "'" +
            ", location='" + getLocation() + "'" +
            ", image='" + getImage() + "'" +
            ", contact='" + getContact() + "'" +
            ", announcementDate='" + getAnnouncementDate() + "'" +
            "}";
    }
}
