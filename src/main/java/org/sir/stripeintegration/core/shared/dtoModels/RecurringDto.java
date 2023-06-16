package org.sir.stripeintegration.core.shared.dtoModels;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecurringDto {
    @SerializedName("interval")
    String interval;

    @SerializedName("interval_count")
    Long intervalCount;

    @SerializedName("usage_type")
    String usageType;
}
