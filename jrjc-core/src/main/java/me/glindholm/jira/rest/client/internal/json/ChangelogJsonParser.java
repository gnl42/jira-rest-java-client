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
import java.time.OffsetDateTime;

import me.glindholm.jira.rest.client.api.domain.BasicUser;
import me.glindholm.jira.rest.client.api.domain.ChangelogGroup;
import me.glindholm.jira.rest.client.api.domain.ChangelogItem;

public class ChangelogJsonParser implements JsonObjectParser<ChangelogGroup> {
    private final ChangelogItemJsonParser changelogItemJsonParser = new ChangelogItemJsonParser();

    @Override
    public ChangelogGroup parse(JSONObject json) throws JSONException, URISyntaxException {
        final OffsetDateTime created = JsonParseUtil.parseOffsetDateTime(json, "created");
        final BasicUser author = json.has("author") ? JsonParseUtil.parseBasicUser(json.getJSONObject("author")) : null;
        final List<ChangelogItem> items = JsonParseUtil.parseJsonArray(json.getJSONArray("items"), changelogItemJsonParser);
        return new ChangelogGroup(author, created, items);
    }
}
