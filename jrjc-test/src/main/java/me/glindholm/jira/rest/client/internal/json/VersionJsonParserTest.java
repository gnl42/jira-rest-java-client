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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import me.glindholm.jira.rest.client.api.domain.Version;
import me.glindholm.jira.rest.client.test.matchers.DateTimeMatcher;

public class VersionJsonParserTest {

    @Test
    public void testParse() throws JSONException, URISyntaxException {
        VersionJsonParser parser = new VersionJsonParser();
        final Version version = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/version/valid.json"));

        assertEquals(new URI("http://localhost:8090/jira/rest/api/latest/version/10000"), version.getSelf());
        assertEquals(Long.valueOf(10000), version.getId());
        assertEquals("1.1", version.getName());
        assertEquals("Some version", version.getDescription());
        Assert.assertFalse(version.isReleased());
        Assert.assertTrue(version.isArchived());
        assertThat(version.getReleaseDate(), DateTimeMatcher.isEqual(
                OffsetDateTime.of(2010, 8, 25, 0, 0, 0, 0, ZoneOffset.ofHours(2))));
    }

    @Test
    public void testParseNoReleaseDate() throws JSONException, URISyntaxException {
        VersionJsonParser parser = new VersionJsonParser();
        final Version version = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/version/valid2-no-releaseDate.json"));

        assertEquals(new URI("http://localhost:8090/jira/rest/api/latest/version/10000"), version.getSelf());
        assertEquals(Long.valueOf(10000), version.getId());
        assertEquals("1.1abc", version.getName());
        assertNull(version.getDescription());
        Assert.assertTrue(version.isReleased());
        Assert.assertFalse(version.isArchived());
        assertNull(version.getReleaseDate());
    }

    @Test
    public void testParseNoId() throws JSONException, URISyntaxException {
        VersionJsonParser parser = new VersionJsonParser();
        final Version version = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/version/valid-no-id.json"));

        assertEquals(new URI("http://localhost:8090/jira/rest/api/latest/version/10000"), version.getSelf());
        assertNull(version.getId());
        assertEquals("1.1", version.getName());
        assertEquals("Some version", version.getDescription());
        Assert.assertFalse(version.isReleased());
        Assert.assertTrue(version.isArchived());
        assertThat(version.getReleaseDate(), DateTimeMatcher.isEqual(
                OffsetDateTime.of(2010, 8, 25, 0, 0, 0, 0, ZoneOffset.ofHours(2))));
    }

    @Test
    public void testParseNoDescription() throws JSONException, URISyntaxException {
        VersionJsonParser parser = new VersionJsonParser();
        final Version version = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/version/valid-no-description.json"));

        assertEquals(new URI("http://localhost:8090/jira/rest/api/latest/version/10000"), version.getSelf());
        assertEquals(Long.valueOf(10000), version.getId());
        assertEquals("1.1", version.getName());
        assertNull(version.getDescription());
        Assert.assertFalse(version.isReleased());
        Assert.assertTrue(version.isArchived());
        assertThat(version.getReleaseDate(), DateTimeMatcher.isEqual(
                OffsetDateTime.of(2010, 8, 25, 0, 0, 0, 0, ZoneOffset.ofHours(2))));
    }

}
