package org.sir.stripeintegration.core.shared.constant;

import lombok.Getter;

@Getter
public enum Interval {
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year");

    private final String intervalValue;

    Interval(String intervalValue) {
        this.intervalValue = intervalValue;
    }
}
