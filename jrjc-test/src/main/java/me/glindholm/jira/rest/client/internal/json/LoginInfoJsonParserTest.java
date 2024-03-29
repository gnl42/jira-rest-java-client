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
import me.glindholm.jira.rest.client.api.domain.LoginInfo;
import org.junit.Assert;
import org.junit.Test;

public class LoginInfoJsonParserTest {
    @Test
    public void testParse() throws Exception {
        final LoginInfoJsonParser parser = new LoginInfoJsonParser();
        final LoginInfo loginInfo = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/loginInfo/valid.json"));
        Assert.assertEquals(new LoginInfo(5, 379, TestUtil.toOffsetDateTime("2010-09-13T17:19:20.752+0200"),
                TestUtil.toOffsetDateTime("2010-09-13T17:19:38.220+0200")), loginInfo);
    }

    @Test
    public void testParseNoFailedLoginBefore() throws Exception {
        final LoginInfoJsonParser parser = new LoginInfoJsonParser();
        final LoginInfo loginInfo = parser.parse(ResourceUtil
                .getJsonObjectFromResource("/json/loginInfo/valid-no-login-failure.json"));
        Assert.assertEquals(new LoginInfo(0, 379, null, TestUtil.toOffsetDateTime("2010-09-13T17:19:38.220+0200")), loginInfo);
    }

    @Test
    public void testParseNoLoginBefore() throws Exception {
        final LoginInfoJsonParser parser = new LoginInfoJsonParser();
        final LoginInfo loginInfo = parser.parse(ResourceUtil
                .getJsonObjectFromResource("/json/loginInfo/valid-no-login-so-far.json"));
        Assert.assertEquals(new LoginInfo(0, 1, null, null), loginInfo);
    }

}
