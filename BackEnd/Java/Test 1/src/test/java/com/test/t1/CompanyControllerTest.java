package com.test.t1;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.test.t1.adapter.in.controller.CompanyController;
import com.test.t1.application.service.CompanyService;
import com.test.t1.domain.model.dto.CompanyDTO;
import com.test.t1.domain.model.dto.VersionDTO;

import java.util.Optional;
import java.util.Arrays;

@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Test
    public void testGetCompanyDetailsByCodigo_Found() throws Exception {
        // Arrange
        VersionDTO mockVersionDTO = new VersionDTO("APP001", "Test App", "1.0", "Initial Version");
        CompanyDTO mockCompanyDTO = new CompanyDTO("COMP001", "Test Company", Arrays.asList(mockVersionDTO));

        when(companyService.getCompanyDetailsByCodigo("COMP001")).thenReturn(Optional.of(mockCompanyDTO));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/company/getByCode/COMP001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo_company").value("COMP001"))
                .andExpect(jsonPath("$.name_company").value("Test Company"))
                .andExpect(jsonPath("$.versions[0].app_code").value("APP001"));
    }

    @Test
    public void testGetCompanyDetailsByCodigo_NotFound() throws Exception {
        // Arrange
        when(companyService.getCompanyDetailsByCodigo("COMP001")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/company/getByCode/COMP010"))
                .andExpect(status().isNotFound());
    }
}
