package org.sir.stripeintegration.core.shared.dtoModels;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingDetailsDto {
    @SerializedName("address")
    public AddressDto address;
    @SerializedName("email")
    public String email;
    @SerializedName("name")
    public String name;
    @SerializedName("phone")
    public String phone;
}
