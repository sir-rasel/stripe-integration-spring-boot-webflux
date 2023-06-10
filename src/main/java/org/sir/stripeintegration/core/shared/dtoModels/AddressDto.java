package org.sir.stripeintegration.core.shared.dtoModels;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @SerializedName("city")
    public String city;
    @SerializedName("country")
    public String country;
    @SerializedName("line1")
    public String line1;
    @SerializedName("line2")
    public String line2;
    @SerializedName("postal_code")
    public String postalCode;
    @SerializedName("state")
    public String state;
}
