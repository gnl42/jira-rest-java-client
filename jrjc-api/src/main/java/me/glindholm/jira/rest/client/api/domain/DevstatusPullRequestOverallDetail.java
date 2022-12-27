package me.glindholm.jira.rest.client.api.domain;

public class DevstatusPullRequestOverallDetail {
    private final Long openCount;
    private final Long mergedCount;
    private final Long declinedCount;

    public DevstatusPullRequestOverallDetail(final Long openCount, final Long mergedCount, final Long declinedCount) {
        super();
        this.openCount = openCount;
        this.mergedCount = mergedCount;
        this.declinedCount = declinedCount;
    }

    public Long getOpenCount() {
        return openCount;
    }

    public Long getMergedCount() {
        return mergedCount;
    }

    public Long getDeclinedCount() {
        return declinedCount;
    }

}
