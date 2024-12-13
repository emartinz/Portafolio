package com.test.t1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.test.t1.adapter.out.repository.CompanyRepository;
import com.test.t1.adapter.out.repository.VersionCompanyRepository;
import com.test.t1.application.service.CompanyService;
import com.test.t1.domain.model.dto.CompanyDTO;
import com.test.t1.domain.model.entity.Application;
import com.test.t1.domain.model.entity.Company;
import com.test.t1.domain.model.entity.Version;
import com.test.t1.domain.model.entity.VersionCompany;

import java.util.Optional;
import java.util.Arrays;

public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private VersionCompanyRepository versionCompanyRepository;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCompanyDetailsByCodigo_Found() {
        // Arrange
        Company mockCompany = new Company();
        mockCompany.setId(1);
        mockCompany.setCode("COMP001");
        mockCompany.setName("Test Company"); 

        when(companyRepository.findByCode("COMP001")).thenReturn(Optional.of(mockCompany));

        Version mockVersion = new Version();
        mockVersion.setVersion("1.0");
        mockVersion.setDescription("Initial Version");

        Application mockApplication = new Application();
        mockApplication.setCode("APP001");
        mockApplication.setName("Test App");
        mockVersion.setApplication(mockApplication);

        VersionCompany mockVersionCompany = new VersionCompany();
        mockVersionCompany.setVersion(mockVersion);
        
        when(versionCompanyRepository.findByCompanyId(1)).thenReturn(Arrays.asList(mockVersionCompany));

        // Act
        Optional<CompanyDTO> result = companyService.getCompanyDetailsByCodigo("COMP001");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("COMP001", result.get().getCodigo_company());
        assertEquals(1, result.get().getVersions().size());
        assertEquals("APP001", result.get().getVersions().get(0).getApp_code());
    }

    @Test
    public void testGetCompanyDetailsByCodigo_NotFound() {
        // Arrange
        when(companyRepository.findByCode("COMP001")).thenReturn(Optional.empty());

        // Act
        Optional<CompanyDTO> result = companyService.getCompanyDetailsByCodigo("COMP001");

        // Assert
        assertFalse(result.isPresent());
    }
}
