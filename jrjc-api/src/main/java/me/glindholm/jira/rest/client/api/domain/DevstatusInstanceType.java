package me.glindholm.jira.rest.client.api.domain;

public class DevstatusInstanceType {
    private final Long count;
    private final String name;

    public DevstatusInstanceType(final Long count, final String name) {
        this.count = count;
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public String getName() {
        return name;
    }
}
