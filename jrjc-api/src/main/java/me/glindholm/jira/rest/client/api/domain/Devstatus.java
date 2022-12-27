package me.glindholm.jira.rest.client.api.domain;

import java.util.List;
import java.util.Map;

public class Devstatus {

    private final List<DevstatusError> errors;

    private final List<DevstatusConfigError> configErrors;

    private final Map<String, DevstatusSummary<? extends DevstatusOverall>> summary;

    public Devstatus(final List<DevstatusError> errors, final List<DevstatusConfigError> configErrors,
            final Map<String, DevstatusSummary<? extends DevstatusOverall>> summary) {
        this.errors = errors;
        this.configErrors = configErrors;
        this.summary = summary;
    }

    public List<DevstatusError> getErrors() {
        return errors;
    }

    public List<DevstatusConfigError> getConfigErrors() {
        return configErrors;
    }

    public Map<String, DevstatusSummary<? extends DevstatusOverall>> getSummary() {
        return summary;
    }

}
