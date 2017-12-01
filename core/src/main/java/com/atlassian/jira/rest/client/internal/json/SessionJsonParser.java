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

package com.atlassian.jira.rest.client.internal.json;

import com.atlassian.jira.rest.client.api.domain.LoginInfo;
import com.atlassian.jira.rest.client.api.domain.Session;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.net.URI;

public class SessionJsonParser implements JsonObjectParser<Session> {
    private final LoginInfoJsonParser loginInfoJsonParser = new LoginInfoJsonParser();

    @Override
    public Session parse(JSONObject json) throws JSONException {
        final URI userUri = JsonParseUtil.getSelfUri(json);
        final String username = json.getString("name");
        final LoginInfo loginInfo = loginInfoJsonParser.parse(json.getJSONObject("loginInfo"));
        return new Session(userUri, username, loginInfo);
    }
}
