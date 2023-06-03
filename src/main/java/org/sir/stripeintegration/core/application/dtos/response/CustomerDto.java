package org.sir.stripeintegration.core.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    public UUID id;
    public String customerId;
    public String email;
    public String name;
    public String phone;

    public CustomerDto(String customerId, String email, String name, String phone) {
        this.customerId = customerId;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
