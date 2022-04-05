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
import org.junit.Assert;
import org.junit.Test;

import me.glindholm.jira.rest.client.api.StatusCategory;
import me.glindholm.jira.rest.client.api.domain.Status;
import me.glindholm.jira.rest.client.internal.json.StatusJsonParser;

import static com.atlassian.jira.rest.client.TestUtil.toUri;

public class StatusJsonParserTest {

    @Test
    public void testParseNoStatusCategory() throws JSONException {
        final StatusJsonParser parser = new StatusJsonParser();
        final Status status = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/status/valid.json"));
        Assert.assertEquals(new Status(toUri("http://localhost:8090/jira/rest/api/latest/status/1"),
                1L, "Open", "The issue is open and ready for the assignee to start work on it.",
                toUri("http://localhost:8090/jira/images/icons/status_open.gif"), null), status);
    }

    @Test
    public void testParseStatusCategory() throws JSONException {
        final StatusJsonParser parser = new StatusJsonParser();
        final StatusCategory statusCategory = new StatusCategory(
                toUri("https://localhost:8080/rest/api/latest/statuscategory/2"),"New",2l,"new","blue-gray");

        final Status status = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/status/validStatusCategory.json"));
        Assert.assertEquals(new Status(toUri("http://localhost:8090/jira/rest/api/latest/status/1"),
                1L, "Open", "The issue is open and ready for the assignee to start work on it.",
                toUri("http://localhost:8090/jira/images/icons/status_open.gif"), null), status);

        Assert.assertEquals(status.getStatusCategory(), statusCategory);
    }
}
