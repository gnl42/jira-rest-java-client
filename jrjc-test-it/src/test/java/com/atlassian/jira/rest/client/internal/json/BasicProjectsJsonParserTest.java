/*
 * Copyright (C) 2011 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.atlassian.jira.rest.client.internal.json;

import com.atlassian.jira.rest.client.TestUtil;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

public class BasicProjectsJsonParserTest {

    private static final BasicProject TST_PROJECT_WITHOUT_ID = new BasicProject(TestUtil
            .toUri("http://localhost:8090/jira/rest/api/latest/project/TST"), "TST", null, "Test Project");

    private static final BasicProject TST_PROJECT = new BasicProject(TestUtil
            .toUri("http://localhost:8090/jira/rest/api/latest/project/TST"), "TST", 10000L, "Test Project");

    @Test
    public void testParseWithoutProjectId() throws Exception {
        BasicProjectsJsonParser parser = new BasicProjectsJsonParser();

        final List<BasicProject> project = parser.parse(ResourceUtil.getJsonArrayFromResource("/json/project/projects-without-id.json"));
        Assert.assertEquals(3, Lists.size(project));
        Assert.assertEquals(TST_PROJECT_WITHOUT_ID, Lists.get(project, 0));
    }

    @Test
    public void testParse() throws Exception {
        BasicProjectsJsonParser parser = new BasicProjectsJsonParser();

        final List<BasicProject> project = parser.parse(ResourceUtil.getJsonArrayFromResource("/json/project/projects.json"));
        Assert.assertEquals(3, Lists.size(project));
        Assert.assertEquals(TST_PROJECT, Lists.get(project, 0));
    }

}
