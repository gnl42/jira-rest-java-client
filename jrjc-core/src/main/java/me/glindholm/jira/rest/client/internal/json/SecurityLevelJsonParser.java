/*
 * Copyright (C) 2013 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
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

import me.glindholm.jira.rest.client.api.domain.SecurityLevel;

/**
 * Parses SecurityLevel
 *
 * @since v2.0
 */
public class SecurityLevelJsonParser implements JsonObjectParser<SecurityLevel> {

    @Override
    public SecurityLevel parse(final JSONObject json) throws JSONException {
        final URI self = JsonParseUtil.getSelfUri(json);
        final long id = json.getLong("id");
        final String description = json.optString("description", "");
        final String name = json.getString("name");
        return new SecurityLevel(self, id, name, description);
    }
}
