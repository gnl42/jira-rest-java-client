package me.glindholm.jira.rest.client.api.domain;

import java.time.OffsetDateTime;

public class DevstatusOverall {
    private final Long count;

    private final OffsetDateTime lastUpdated;

    public DevstatusOverall(final Long count, final OffsetDateTime lastUpdated) {
        this.count = count;
        this.lastUpdated = lastUpdated;
    }

    public Long getCount() {
        return count;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }
}
