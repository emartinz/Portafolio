package com.test.t1.domain.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class CompanyDTO {
    private String codigo_company;
    private String name_company;
    private List<VersionDTO> versions;

    public CompanyDTO(String codigoCompany, String nameCompany, List<VersionDTO> versions) {
        this.codigo_company = codigoCompany; 
        this.name_company = nameCompany;
        this.versions = versions;
    }
}

