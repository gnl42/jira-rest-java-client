/*
 * Copyright (C) 2012 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.glindholm.jira.rest.client.api.domain.input;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import me.glindholm.jira.rest.client.api.IdentifiableEntity;
import me.glindholm.jira.rest.client.api.NamedEntity;
import me.glindholm.jira.rest.client.api.domain.BasicProject;
import me.glindholm.jira.rest.client.api.domain.CustomFieldOption;
import me.glindholm.jira.rest.client.api.domain.TimeTracking;

/**
 * Transforms most of standard fields values into form understandable by input generator.
 *
 * @since v1.0
 */
public class BaseValueTransformer implements Serializable, ValueTransformer {
    private static final long serialVersionUID = 1L;

    @Override
    public Object apply(Object rawValue) {
        if (rawValue == null) {
            return null;
        } else if (rawValue instanceof String || rawValue instanceof Number || rawValue instanceof ComplexIssueInputFieldValue) {
            return rawValue;
        } else if (rawValue instanceof BasicProject) {
            return new ComplexIssueInputFieldValue(Map.<String, Object>of("key", ((BasicProject) rawValue).getKey()));
        } else if (rawValue instanceof CustomFieldOption) {
            return transformCustomFieldOption((CustomFieldOption) rawValue);
        } else if (rawValue instanceof TimeTracking) {
            return transformTimeTracking((TimeTracking) rawValue);
        } else if (rawValue instanceof IdentifiableEntity) {
            final IdentifiableEntity identifiableEntity = (IdentifiableEntity) rawValue;
            return new ComplexIssueInputFieldValue(Map.<String, Object>of("id", identifiableEntity.getId().toString()));
        } else if (rawValue instanceof NamedEntity) {
            final NamedEntity namedEntity = (NamedEntity) rawValue;
            return new ComplexIssueInputFieldValue(Map.<String, Object>of("name", namedEntity.getName()));
        }

        return CANNOT_HANDLE;
    }

    private ComplexIssueInputFieldValue transformCustomFieldOption(CustomFieldOption cfo) {
        if (cfo.getChild() != null) {
            return new ComplexIssueInputFieldValue(Map.<String, Object>of(
                    "id", cfo.getId().toString(),
                    "value", cfo.getValue(),
                    "child", this.apply(cfo.getChild())));
        } else {
            return new ComplexIssueInputFieldValue(Map.<String, Object>of("id", cfo.getId().toString(), "value", cfo
                    .getValue()));
        }
    }

    private ComplexIssueInputFieldValue transformTimeTracking(TimeTracking timeTracking) {
        final Map<String, Object> fields = new HashMap<>();

        final Integer originalEstimateMinutes = timeTracking.getOriginalEstimateMinutes();
        if (originalEstimateMinutes != null) {
            fields.put("originalEstimate", originalEstimateMinutes + "m");
        }

        final Integer remainingEstimateMinutes = timeTracking.getRemainingEstimateMinutes();
        if (remainingEstimateMinutes != null) {
            fields.put("remainingEstimate", remainingEstimateMinutes + "m");
        }

        // Don't use time spent as JIRA says: "Setting the Time Spent directly is not supported."

        return new ComplexIssueInputFieldValue(fields);
    }

}
