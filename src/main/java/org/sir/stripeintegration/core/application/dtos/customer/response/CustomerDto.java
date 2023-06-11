package org.sir.stripeintegration.core.application.dtos.customer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    public String id;
    public String email;
    public String name;
    public String phone;
}
