package me.glindholm.jira.rest.client.api.domain;

import java.time.OffsetDateTime;

public class DevstatusReviewOverall extends DevstatusOverall {
    private final Long stateCount;

    private final OffsetDateTime dueDate;

    private final Boolean overdue;

    private final Boolean completed;

    public DevstatusReviewOverall(final Long count, final OffsetDateTime lastUpdated, final Long stateCount, final OffsetDateTime dueDate,
            final Boolean overdue, final Boolean completed) {
        super(count, lastUpdated);
        this.stateCount = stateCount;
        this.dueDate = dueDate;
        this.overdue = overdue;
        this.completed = completed;
    }

    public Long getStateCount() {
        return stateCount;
    }

    public OffsetDateTime getDueDate() {
        return dueDate;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
