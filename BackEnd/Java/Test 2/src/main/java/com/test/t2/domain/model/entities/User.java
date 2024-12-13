package com.test.t2.domain.model.entities;

import org.springframework.data.annotation.Id;

import com.test.t2.domain.model.enums.PreferredNotificationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    private double balance;
    private PreferredNotificationType preferredNotification;
    private String email;
    private String phoneNumber;
}