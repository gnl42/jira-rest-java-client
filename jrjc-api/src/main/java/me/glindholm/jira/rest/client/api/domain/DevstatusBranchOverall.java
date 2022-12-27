package me.glindholm.jira.rest.client.api.domain;

import java.time.OffsetDateTime;

public class DevstatusBranchOverall extends DevstatusOverall {

    public DevstatusBranchOverall(final Long count, final OffsetDateTime lastUpdated) {
        super(count, lastUpdated);
    }

}
