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

import java.net.URISyntaxException;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import me.glindholm.jira.rest.client.api.domain.Transition;

public class TransitionJsonParser {
    private final TransitionFieldJsonParser transitionFieldJsonParser = new TransitionFieldJsonParser();

    public Transition parse(JSONObject json, int id) throws JSONException, URISyntaxException {
        final String name = json.getString("name");
        final List<Transition.Field> fields = JsonParseUtil.parseJsonArray(json.getJSONArray("fields"),
                transitionFieldJsonParser);
        return new Transition(name, id, fields);
    }

    public static class TransitionFieldJsonParser implements JsonObjectParser<Transition.Field> {

        @Override
        public Transition.Field parse(JSONObject json) throws JSONException {
            final String name = json.getString("id");
            final boolean isRequired = json.getBoolean("required");
            final String type = json.getString("type");
            return new Transition.Field(name, isRequired, type);
        }
    }
}
