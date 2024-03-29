/*
 * Copyright (C) 2011 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.glindholm.jira.rest.client.internal.json;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import me.glindholm.jira.rest.client.api.domain.BasicProject;

public class BasicProjectsJsonParser implements JsonArrayParser<List<BasicProject>> {

    private final BasicProjectJsonParser basicProjectJsonParser = new BasicProjectJsonParser();

    @Override
    public List<BasicProject> parse(JSONArray json) throws JSONException {
        ArrayList<BasicProject> res = new ArrayList<>(json.length());
        for (int i = 0; i < json.length(); i++) {
            res.add(basicProjectJsonParser.parse(json.getJSONObject(i)));

        }
        return res;
    }
}
