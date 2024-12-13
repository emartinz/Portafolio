package com.test.t1;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.test.t1.adapter.in.controller.CompanyController;
import com.test.t1.application.service.CompanyService;
import com.test.t1.domain.model.dto.CompanyDTO;
import com.test.t1.domain.model.dto.VersionDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.Arrays;

class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testGetCompanyDetailsByCodigo_Found() {
        // Arrange
        VersionDTO mockVersionDTO = new VersionDTO("APP001", "Test App", "1.0", "Initial Version");
        CompanyDTO mockCompanyDTO = new CompanyDTO("COMP001", "Test Company", Arrays.asList(mockVersionDTO));

        when(companyService.getCompanyDetailsByCodigo("COMP001")).thenReturn(Optional.of(mockCompanyDTO));

        // Act
        ResponseEntity<CompanyDTO> response = companyController.getCompanyDetailsByCodigo("COMP001");

        // Assert
        assertNotNull(response, "ResponseEntity should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP status should be OK");

        CompanyDTO responseBody = response.getBody();
        assertNotNull(responseBody, "Response body should not be null");
        assertEquals("COMP001", responseBody.getCodigo_company());
        assertEquals("Test Company", responseBody.getName_company());
        assertEquals("APP001", responseBody.getVersions().get(0).getApp_code());
    }

    @Test
    void testGetCompanyDetailsByCodigo_NotFound() {
        // Arrange
        when(companyService.getCompanyDetailsByCodigo("COMP001")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<CompanyDTO> response = companyController.getCompanyDetailsByCodigo("COMP001");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
