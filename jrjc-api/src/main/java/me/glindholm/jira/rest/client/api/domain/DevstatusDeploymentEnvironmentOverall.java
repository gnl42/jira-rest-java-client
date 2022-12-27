package me.glindholm.jira.rest.client.api.domain;

import java.time.OffsetDateTime;
import java.util.List;

public class DevstatusDeploymentEnvironmentOverall extends DevstatusOverall {

    private final List<String> topEnvironments;

    private final Boolean showProjects;

    private final Long successfulCount;

    public DevstatusDeploymentEnvironmentOverall(final Long count, final OffsetDateTime lastUpdated, final List<String> topEnvironments,
            final Boolean showProjects, final Long successfulCount) {
        super(count, lastUpdated);
        this.topEnvironments = topEnvironments;
        this.showProjects = showProjects;
        this.successfulCount = successfulCount;
    }

    public List<String> getTopEnvironments() {
        return topEnvironments;
    }

    public Boolean getShowProjects() {
        return showProjects;
    }

    public Long getSuccessfulCount() {
        return successfulCount;
    }
}
