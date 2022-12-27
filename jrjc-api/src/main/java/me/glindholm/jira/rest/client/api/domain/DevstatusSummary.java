package me.glindholm.jira.rest.client.api.domain;

import java.util.Map;

public class DevstatusSummary<T> {
    private final T overall;
    private final Map<String, DevstatusInstanceType> byInstanceType;

    public DevstatusSummary(final T overall, final Map<String, DevstatusInstanceType> byInstanceType) {
        this.overall = overall;
        this.byInstanceType = byInstanceType;
    }

    public T getOverall() {
        return overall;
    }

    public Map<String, DevstatusInstanceType> getByInstanceType() {
        return byInstanceType;
    }
}
