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

package me.glindholm.jira.rest.client.internal.json;

import java.net.URISyntaxException;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import me.glindholm.jira.rest.client.api.domain.CimProject;

/**
 * JSON parser for REST action issue/createmeta result
 *
 * @since v1.0
 */
public class CreateIssueMetadataJsonParser implements JsonObjectParser<List<CimProject>> {

    private final GenericJsonArrayParser<CimProject> projectsParser = new GenericJsonArrayParser<>(new CimProjectJsonParser());

    @Override
    public List<CimProject> parse(final JSONObject json) throws JSONException, URISyntaxException {
        return projectsParser.parse(json.getJSONArray("projects"));
    }
}
