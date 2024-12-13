package com.test.t2.domain.core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

@Service
public class SnsService {
    private static final Logger logger = LoggerFactory.getLogger(SnsService.class);
    private final SnsClient snsClient;

    // Inyecta el valor de topicArn desde el archivo de configuración o variable de entorno
    public SnsService() {
        this.snsClient = SnsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    // Método para enviar un SMS directamente a un número de teléfono
    public void sendSms(String phoneNumber, String message) {
        PublishRequest request = PublishRequest.builder()
                .message(message)
                .phoneNumber(phoneNumber)
                .build();

        try {
            PublishResponse result = snsClient.publish(request);
            logger.info("SMS sent! Message ID: " + result.messageId());
        } catch (SnsException e) {
            logger.error("Failed to send SMS: " + e.awsErrorDetails().errorMessage());
        }
    }
}
