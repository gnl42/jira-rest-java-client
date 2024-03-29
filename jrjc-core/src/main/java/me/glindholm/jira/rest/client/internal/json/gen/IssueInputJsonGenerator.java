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

package me.glindholm.jira.rest.client.internal.json.gen;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import me.glindholm.jira.rest.client.api.domain.input.FieldInput;
import me.glindholm.jira.rest.client.api.domain.input.IssueInput;
import me.glindholm.jira.rest.client.api.domain.input.PropertyInput;

/**
 * Json Generator for IssueInput
 *
 * @since 1.0
 */
public class IssueInputJsonGenerator implements JsonGenerator<IssueInput> {

    private final ComplexIssueInputFieldValueJsonGenerator complexIssueInputFieldValueJsonGenerator = new ComplexIssueInputFieldValueJsonGenerator();

    @Override
    public JSONObject generate(final IssueInput issue) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        final JSONObject fields = new JSONObject();

        if (issue != null && issue.getFields() != null) {
            for (final FieldInput field : issue.getFields().values()) {
                if (field.getValue() != null) {
                    fields.put(field.getId(), complexIssueInputFieldValueJsonGenerator.generateFieldValueForJson(field
                            .getValue()));
                }
            }
        }

        jsonObject.put("fields", fields);

        // Add entity properties
        final JSONArray entityProperties = new JSONArray();
        if (issue != null && issue.getProperties() != null) {
            for (final PropertyInput p : issue.getProperties()) {
                final JSONObject property = new JSONObject();
                property.put("key", p.getKey());
                property.put("value", new JSONObject(p.getValue()));
                entityProperties.put(property);
            }
        }
        jsonObject.put("properties", entityProperties);

        return jsonObject;
    }

}
