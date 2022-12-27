package me.glindholm.jira.rest.client.api.domain;

import java.time.OffsetDateTime;

public class DevstatusRepostoryOverall extends DevstatusOverall {

    public DevstatusRepostoryOverall(final Long count, final OffsetDateTime lastUpdated) {
        super(count, lastUpdated);
    }
}
