package me.glindholm.jira.rest.client.api.domain;

import java.time.OffsetDateTime;

public class DevstatusPullRequestOverall extends DevstatusOverall {
    private final Long stateCount;
    private final String state;
    private final DevstatusPullRequestOverallDetail details;
    private final Boolean open;

    public DevstatusPullRequestOverall(final Long count, final OffsetDateTime lastUpdated, final Long stateCount, final String state,
            final DevstatusPullRequestOverallDetail details, final Boolean open) {
        super(count, lastUpdated);
        this.stateCount = stateCount;
        this.state = state;
        this.details = details;
        this.open = open;
    }

    public Long getStateCount() {
        return stateCount;
    }

    public String getState() {
        return state;
    }

    public DevstatusPullRequestOverallDetail getDetails() {
        return details;
    }

    public Boolean getOpen() {
        return open;
    }
}
