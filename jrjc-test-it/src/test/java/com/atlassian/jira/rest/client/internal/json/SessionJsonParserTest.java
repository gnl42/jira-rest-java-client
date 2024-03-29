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

import com.atlassian.jira.rest.client.TestUtil;
import com.atlassian.jira.rest.client.api.domain.LoginInfo;
import com.atlassian.jira.rest.client.api.domain.Session;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionJsonParserTest {
    @Test
    public void testParse() throws Exception {
        SessionJsonParser parser = new SessionJsonParser();
        final Session session = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/session/valid.json"));
        Assert.assertEquals(TestConstants.USER_ADMIN_BASIC_DEPRECATED.getSelf(), session.getUserUri());
        Assert.assertEquals("admin", session.getUsername());
        assertEquals(new LoginInfo(12, 413, TestUtil.toOffsetDateTime("2010-09-14T16:15:47.554+0200"),
                TestUtil.toOffsetDateTime("2010-09-14T16:48:33.002+0200")), session.getLoginInfo());
    }
}
