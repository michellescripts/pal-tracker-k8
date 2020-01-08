package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class PalTrackerHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();

        if (FailureController.simulateFailure) {
            builder.down();
        } else {
            builder.up();
        }

        return builder.build();
    }
}
