package com.test.t1.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "company")
public class Company {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_company")
    private Integer id;

    @Column(name = "codigo_company", nullable = false, length = 20)
    private String code;

    @Column(name = "name_company", nullable = false, length = 50)
    private String name;

    @Column(name = "description_company", length = 200)
    private String description;

}
 