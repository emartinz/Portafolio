package com.test.t1.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.t1.adapter.out.repository.CompanyRepository;
import com.test.t1.adapter.out.repository.VersionCompanyRepository;
import com.test.t1.domain.model.dto.CompanyDTO;
import com.test.t1.domain.model.dto.VersionDTO;
import com.test.t1.domain.model.entity.Application;
import com.test.t1.domain.model.entity.Company;
import com.test.t1.domain.model.entity.Version;
import com.test.t1.domain.model.entity.VersionCompany;

@Service
public class CompanyService{
    @Autowired
    private CompanyRepository repository;

    @Autowired
    private VersionCompanyRepository versionCompanyRepository;

    public List<Company> getAll() {
        return repository.findAll();
    }

    public Optional<Company> getById(Long id){
        return repository.findById(id);
    }

    public Company add (Company object) {
        return repository.save(object);
    }

    public Company update(Long id, Company updatedObject) {
        Company currentObject = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró una compañia con id: " + id));
        
            currentObject.setCode(updatedObject.getCode());
            currentObject.setName(updatedObject.getName());
            currentObject.setDescription(updatedObject.getDescription());
            
            return repository.save(currentObject);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<CompanyDTO> getCompanyDetailsByCodigo(String codigoCompany) {
        Optional<Company> companyOpt = repository.findByCode(codigoCompany);

        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();

            List<VersionCompany> versionCompanies = versionCompanyRepository.findByCompanyId(company.getId());

            List<VersionDTO> versions = versionCompanies.stream().map(vc -> {
                Version version = vc.getVersion();
                Application app = version.getApplication();
                return new VersionDTO(app.getCode(), app.getName(), version.getVersion(), version.getDescription());
            }).collect(Collectors.toList());

            return Optional.of(new CompanyDTO(company.getCode(), company.getName(), versions));
        }

        return Optional.empty();
    }
}
