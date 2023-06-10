package org.sir.stripeintegration.core.shared.dtoModels;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    @SerializedName("brand")
    public String brand;
    @SerializedName("country")
    public String country;
    @SerializedName("exp_month")
    public Long expMonth;
    @SerializedName("exp_year")
    public Long expYear;
    @SerializedName("last4")
    public String last4;
}
