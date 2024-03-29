/*
 * Copyright (C) 2014 Atlassian
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import me.glindholm.jira.rest.client.api.domain.Permission;
import me.glindholm.jira.rest.client.api.domain.Permissions;

public class PermissionsJsonParser implements JsonObjectParser<Permissions> {
    private final PermissionJsonParser permissionJsonParser = new PermissionJsonParser();

    @Override
    public Permissions parse(final JSONObject json) throws JSONException {
        final JSONObject permissionsObject = json.getJSONObject("permissions");

        final List<Permission> permissions = new ArrayList<>();
        final Iterator it = permissionsObject.keys();
        while (it.hasNext()) {
            final String key = it.next().toString();
            final JSONObject permissionObject = permissionsObject.getJSONObject(key);
            final Permission permission = permissionJsonParser.parse(permissionObject);
            permissions.add(permission);
        }
        return new Permissions(permissions);
    }
}
