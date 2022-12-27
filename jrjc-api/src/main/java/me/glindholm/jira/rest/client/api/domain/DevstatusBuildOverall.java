package me.glindholm.jira.rest.client.api.domain;

import java.time.OffsetDateTime;

public class DevstatusBuildOverall extends DevstatusOverall {
    private final Long failedBuildCount;
    private final Long successfulBuildCount;
    private final Long unknownBuildCount;

    public DevstatusBuildOverall(final Long count, final OffsetDateTime lastUpdated, final Long failedBuildCount, final Long successfulBuildCount,
            final Long unknownBuildCount) {
        super(count, lastUpdated);
        this.failedBuildCount = failedBuildCount;
        this.successfulBuildCount = successfulBuildCount;
        this.unknownBuildCount = unknownBuildCount;
    }

    public Long getFailedBuildCount() {
        return failedBuildCount;
    }

    public Long getSuccessfulBuildCount() {
        return successfulBuildCount;
    }

    public Long getUnknownBuildCount() {
        return unknownBuildCount;
    }

}
