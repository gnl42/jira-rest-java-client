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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import java.time.OffsetDateTime;

import me.glindholm.jira.rest.client.api.domain.BasicUser;
import me.glindholm.jira.rest.client.api.domain.Visibility;
import me.glindholm.jira.rest.client.api.domain.Worklog;

import java.net.URI;

public class WorklogJsonParser implements JsonObjectParser<Worklog> {

    @Override
    public Worklog parse(JSONObject json) throws JSONException {
        final URI self = JsonParseUtil.getSelfUri(json);
        final URI issueUri = JsonParseUtil.parseURI(json.getString("issue"));
        final BasicUser author = JsonParseUtil.parseBasicUser(json.optJSONObject("author"));
        final BasicUser updateAuthor = JsonParseUtil.parseBasicUser(json.optJSONObject("updateAuthor"));
        // it turns out that somehow it can be sometimes omitted in the resource representation - JRJC-49
        final String comment = JsonParseUtil.getOptionalString(json, "comment");
        final OffsetDateTime creationDate = JsonParseUtil.parseOffsetDateTime(json, "created");
        final OffsetDateTime updateDate = JsonParseUtil.parseOffsetDateTime(json, "updated");
        final OffsetDateTime startDate = JsonParseUtil.parseOffsetDateTime(json, "started");
        final int minutesSpent = json.getInt("minutesSpent");
        final Visibility visibility = new VisibilityJsonParser().parseVisibility(json);
        return new Worklog(self, issueUri, author, updateAuthor, comment, creationDate, updateDate, startDate, minutesSpent, visibility);
    }
}
