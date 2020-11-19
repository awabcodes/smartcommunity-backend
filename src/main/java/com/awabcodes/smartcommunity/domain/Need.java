package com.awabcodes.smartcommunity.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Need.
 */
@Entity
@Table(name = "need")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Need implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "info", nullable = false)
    private String info;

    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    @NotNull
    @Column(name = "contact", nullable = false)
    private String contact;

    
    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "quantity")
    private String quantity;

    @OneToMany(mappedBy = "need")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<NeedOrder> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Need name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public Need info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean isAvailable() {
        return available;
    }

    public Need available(Boolean available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getContact() {
        return contact;
    }

    public Need contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public byte[] getImage() {
        return image;
    }

    public Need image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Need imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getQuantity() {
        return quantity;
    }

    public Need quantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Set<NeedOrder> getOrders() {
        return orders;
    }

    public Need orders(Set<NeedOrder> needOrders) {
        this.orders = needOrders;
        return this;
    }

    public Need addOrders(NeedOrder needOrder) {
        this.orders.add(needOrder);
        needOrder.setNeed(this);
        return this;
    }

    public Need removeOrders(NeedOrder needOrder) {
        this.orders.remove(needOrder);
        needOrder.setNeed(null);
        return this;
    }

    public void setOrders(Set<NeedOrder> needOrders) {
        this.orders = needOrders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Need)) {
            return false;
        }
        return id != null && id.equals(((Need) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Need{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", info='" + getInfo() + "'" +
            ", available='" + isAvailable() + "'" +
            ", contact='" + getContact() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }
}
