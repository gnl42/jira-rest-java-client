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

import me.glindholm.jira.rest.client.TestUtil;
import me.glindholm.jira.rest.client.api.domain.IssueLink;
import me.glindholm.jira.rest.client.api.domain.IssueLinkType;
import org.junit.Assert;
import org.junit.Test;

public class IssueLinkJsonParserTest {
    @Test
    public void testParseIssueLink() throws Exception {
        IssueLinkJsonParser parser = new IssueLinkJsonParser();
        final IssueLink issueLink = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/issueLink/valid.json"));
        Assert.assertEquals(new IssueLinkType("Duplicate", "duplicates", IssueLinkType.Direction.OUTBOUND), issueLink
                .getIssueLinkType());
        Assert.assertEquals("TST-2", issueLink.getTargetIssueKey());
        Assert.assertEquals(TestUtil.toUri("http://localhost:8090/jira/rest/api/latest/issue/TST-2"), issueLink
                .getTargetIssueUri());
    }
}
