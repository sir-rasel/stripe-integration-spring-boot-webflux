package org.sir.stripeintegration.core.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    public String id;
    public String email;
    public String name;
    public String phone;
}
