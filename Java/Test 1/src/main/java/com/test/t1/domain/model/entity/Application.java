package com.test.t1.domain.model.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "application") 
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Integer id;

    @Column(name = "app_code", nullable = false, length = 20)
    private String code;

    @Column(name = "app_name", nullable = false, length = 50)
    private String name;

    @Column(name = "app_description", length = 200)
    private String description;

    // Mapeo bidireccional con Version
    @OneToMany(mappedBy = "application")
    private Set<Version> versions;
    
}
