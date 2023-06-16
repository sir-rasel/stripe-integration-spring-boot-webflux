package org.sir.stripeintegration.core.application.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductCommonDto {
    @NotNull
    @NotEmpty
    @NotBlank
    public String name;

    public String description;
    public boolean active = true;
    public boolean shippable = true;
    public List<String> images = new ArrayList<>();
}
