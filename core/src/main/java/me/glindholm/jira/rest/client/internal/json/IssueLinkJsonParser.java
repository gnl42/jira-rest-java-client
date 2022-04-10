/*
 * Copyright (C) 2010 Atlassian
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

import java.net.URI;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import me.glindholm.jira.rest.client.api.domain.IssueLink;
import me.glindholm.jira.rest.client.api.domain.IssueLinkType;

public class IssueLinkJsonParser implements JsonObjectParser<IssueLink> {
    private final IssueLinkTypeJsonParser issueLinkTypeJsonParser = new IssueLinkTypeJsonParser();

    @Override
    public IssueLink parse(JSONObject json) throws JSONException {
        final String key = json.getString("issueKey");
        final long targetIssueId = json.getLong("id");
        final URI targetIssueUri = JsonParseUtil.parseURI(json.getString("issue"));
        final IssueLinkType issueLinkType = issueLinkTypeJsonParser.parse(json.getJSONObject("type"));
        return new IssueLink(key, targetIssueId, targetIssueUri, issueLinkType);
    }
}
