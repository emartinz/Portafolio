package com.test.t1.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Data
@Table(name = "version")
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "version_id")
    private Integer id;

    @Column(name = "version", nullable = false, length = 20)
    private String version;

    @Column(name = "version_description", length = 200)
    private String description;

    // Mapeo con Application
    @ManyToOne
    @JoinColumn(name = "app_id", nullable = false)
    private Application application;
}
