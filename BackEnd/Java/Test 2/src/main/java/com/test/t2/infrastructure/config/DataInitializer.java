package com.test.t2.infrastructure.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.test.t2.domain.model.entities.Fund;
import com.test.t2.domain.model.entities.User;
import com.test.t2.domain.model.enums.PreferredNotificationType;
import com.test.t2.infrastructure.adapter.out.FundRepository;
import com.test.t2.infrastructure.adapter.out.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Configuration
@Data
@AllArgsConstructor
public class DataInitializer {

    private final FundRepository fundRepository;
    private final UserRepository userRepository;

    @Autowired
    private Environment env;
    
    @Bean
    public CommandLineRunner initializeCategories() {
        return args -> {
            loadDefaultUsers();
            loadDefaultFunds();
        };
    }

    private void loadDefaultUsers() {
        String defaultUserPreferredNotification = env.getProperty("DEFAULT_USER_PREFERRED_NOTIFICATION");
        String defaultUserEmail = env.getProperty("DEFAULT_USER_EMAIL");
        String defaultUserPhoneNumber = env.getProperty("DEFAULT_USER_PHONENUMBER");
        String defaultUserId = env.getProperty("DEFAULT_USER_ID");

        //Lista de Usuarios predeterminados
        List<User> defaultUsers = Arrays.asList(
            new User(
                defaultUserId, 
                "Andres Martinez", 
                500000, 
                PreferredNotificationType.valueOf(defaultUserPreferredNotification), 
                defaultUserEmail, 
                defaultUserPhoneNumber
            )
        );

        // Crea los Usuarios si no existen
        for (User user : defaultUsers) {
            if (!userRepository.existsByName(user.getName())) {
                userRepository.save(user);
            }
        }
    }

    private void loadDefaultFunds() {
        String defaultFundId = env.getProperty("DEFAULT_FUND_ID");

        // Lista de Fondos predeterminados
        List<Fund> defaultFunds = Arrays.asList(
            new Fund(null, "FPV_RECAUDADORA", 75000, "FPV"),
            new Fund(defaultFundId, "FPV_EMPRESA", 125000, "FPV"),
            new Fund(null, "DEUDA_PRIVADA", 50000, "FIC"),
            new Fund(null, "FONDO_ACCIONES", 250000, "FIC"),
            new Fund(null, "FPV_DINAMICA", 100000, "FPV")
        );

        // Crea los Fondos si no existen
        for (Fund fund : defaultFunds) {
            if (!fundRepository.existsByName(fund.getName())) {
                fundRepository.save(fund);
            }
        }
    }
}
