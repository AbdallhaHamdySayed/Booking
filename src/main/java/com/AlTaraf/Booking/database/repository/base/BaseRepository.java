package com.AlTaraf.Booking.database.repository.base;

import com.AlTaraf.Booking.database.entity.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<ID extends Serializable, entity extends BaseEntity<ID>>
        extends JpaRepository<entity, ID> {
}
