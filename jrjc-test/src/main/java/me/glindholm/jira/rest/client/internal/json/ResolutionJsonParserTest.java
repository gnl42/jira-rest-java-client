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

import me.glindholm.jira.rest.client.api.domain.Resolution;
import org.junit.Assert;
import org.junit.Test;

import static me.glindholm.jira.rest.client.TestUtil.toUri;

public class ResolutionJsonParserTest {
    @Test
    public void testParse() throws Exception {
        final ResolutionJsonParser parser = new ResolutionJsonParser();
        final Resolution basicresolution = parser.parse(ResourceUtil
                .getJsonObjectFromResource("/json/resolution/valid.json"));
        Assert.assertEquals(new Resolution(
                toUri("http://localhost:8090/jira/rest/api/latest/resolution/4"), 4L, "Incomplete",
                "The problem is not completely described."), basicresolution);
    }
}
