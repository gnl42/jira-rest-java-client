/*
 * Copyright (C) 2018 Atlassian
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

import com.atlassian.jira.rest.client.api.domain.Group;
import com.google.common.collect.Lists;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @since v5.1.0
 */
public class GroupsJsonParserTest {

    private static final Group ADMINISTRATORS = new Group(null, "jira-administrators");
    private static final Group USERS = new Group(null, "jira-users");

    @Test
    public void testParse() throws Exception {
        final GroupsJsonParser parser = new GroupsJsonParser();
        final List<Group> groups = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/groups/valid.json"));

        assertEquals(2, Lists.size(groups));
        assertThat(groups, contains(ADMINISTRATORS, USERS));
    }
}
