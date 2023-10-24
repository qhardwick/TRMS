package com.infy.constants;

import org.springframework.data.cassandra.core.mapping.Table;

@Table
public enum Event {
    UNIVERSITY_COURSE(0.8),
    SEMINAR(0.6),
    CERT_PREP(0.75),
    CERTIFICATION(1.0),
    TECH_TRAINING(0.9),
    OTHER(0.2);

    private final double rate;

    Event(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}
