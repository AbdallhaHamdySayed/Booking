package com.AlTaraf.Booking.database.entity;

import com.AlTaraf.Booking.database.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "integrations_urls")
public class IntegrationsUrls extends BaseEntity<Integer> {

    @Column(name = "url")
    private String url;
}
