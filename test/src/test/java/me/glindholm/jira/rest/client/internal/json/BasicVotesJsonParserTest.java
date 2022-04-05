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

import me.glindholm.jira.rest.client.api.domain.BasicVotes;
import me.glindholm.jira.rest.client.internal.json.BasicVotesJsonParser;

import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import m2.glindholm.jira.rest.client.TestUtil;

/**
 * @since v0.1
 */
public class BasicVotesJsonParserTest {
    @Test
    public void testParse() throws JSONException {
        final BasicVotesJsonParser parser = new BasicVotesJsonParser();
        final BasicVotes basicVotes = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/votes/valid.json"));
        Assert.assertEquals(1, basicVotes.getVotes());
        Assert.assertTrue(basicVotes.hasVoted());
        Assert.assertEquals(TestUtil.toUri("http://localhost:8090/jira/rest/api/latest/issue/TST-1/votes"), basicVotes.getSelf());
    }
}
