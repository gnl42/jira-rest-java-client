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
import java.time.OffsetDateTime;

import me.glindholm.jira.rest.client.api.domain.Attachment;
import me.glindholm.jira.rest.client.api.domain.BasicUser;

public class AttachmentJsonParser implements JsonObjectParser<Attachment> {

    private static final String THUMBNAIL = "thumbnail";

    @Override
    public Attachment parse(JSONObject json) throws JSONException {
        final long id = json.getLong("id");
        final URI selfUri = JsonParseUtil.getSelfUri(json);
        final String filename = json.getString("filename");
        final BasicUser author = JsonParseUtil.parseBasicUser(json.optJSONObject("author"));
        final OffsetDateTime creationDate = JsonParseUtil.parseDateTime(json.getString("created"));
        final int size = json.getInt("size");
        final String mimeType = json.getString("mimeType");
        final URI contentURI = JsonParseUtil.parseURI(json.getString("content"));
        final URI thumbnailURI = JsonParseUtil.parseOptionalURI(json, THUMBNAIL);
        return new Attachment(id, selfUri, filename, author, creationDate, size, mimeType, contentURI, thumbnailURI);
    }
}
