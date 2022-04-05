/*
 * Copyright (C) 2012 Atlassian
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

import com.google.common.collect.ImmutableMap;

import m2.glindholm.jira.rest.client.TestUtil;
import me.glindholm.jira.rest.client.api.domain.BasicComponent;
import me.glindholm.jira.rest.client.api.domain.BasicUser;
import me.glindholm.jira.rest.client.api.domain.User;
import me.glindholm.jira.rest.client.api.domain.Version;

import static m2.glindholm.jira.rest.client.TestUtil.toUri;

import java.net.URI;

/**
 * Constants used in various unit tests.
 * All constants including full URIs are usually useless in integration tests though, as during integration
 * tests we may be testing against a JIRA running on a different port and with a different web context
 *
 * @since v0.1
 */
public class TestConstants {
    public static final String USER1_USERNAME = "wseliga";

    public static final String USER1_PASSWORD = "wseliga";

    public static final String ADMIN_USERNAME = "admin";

    public static final String ADMIN_PASSWORD = "admin";

    // USER1_BASIC_DEPRECATED is deprecated - all tests that use this constant should be reviewed (JIRA now returns more info than we test)
    public static final BasicUser USER1_BASIC_DEPRECATED = new BasicUser(toUri("http://localhost:8090/jira/rest/api/latest/user?username=wseliga"), USER1_USERNAME, "Wojciech Seliga");
    public static final BasicUser USER1_BASIC = new BasicUser(toUri("http://localhost:8090/jira/rest/api/2/user?username=wseliga"), USER1_USERNAME, "Wojciech Seliga");
    public static final User USER1 = new User(toUri("http://localhost:8090/jira/rest/api/2/user?username=wseliga"), USER1_USERNAME,
            "Wojciech Seliga", "wojciech.seliga@spartez.com", null, ImmutableMap.of(
            "16x16", URI.create("http://localhost:8090/jira/secure/useravatar?size=small&avatarId=10082"),
            "48x48", URI.create("http://localhost:8090/jira/secure/useravatar?avatarId=10082")), null);

    // USER_ADMIN_DEPRECATED this is deprecated - all tests that use this constant should be reviewed (JIRA now returns more info than we test)
    public static final BasicUser USER_ADMIN_BASIC_DEPRECATED = new BasicUser(toUri("http://localhost:8090/jira/rest/api/latest/user?username=admin"), ADMIN_USERNAME, "Administrator");
    public static final BasicUser USER_ADMIN_BASIC = new BasicUser(toUri("http://localhost:8090/jira/rest/api/2/user?username=admin"), ADMIN_USERNAME, "Administrator");
    public static final BasicUser USER_ADMIN_BASIC_LATEST = new BasicUser(toUri("http://localhost:8090/jira/rest/api/latest/user?username=admin"), ADMIN_USERNAME, "Administrator");
    public static final User USER_ADMIN = new User(toUri("http://localhost:8090/jira/rest/api/2/user?username=admin"), ADMIN_USERNAME, "Administrator",
            "wojciech.seliga@spartez.com", null, ImmutableMap.of(
            "16x16", URI.create("http://localhost:8090/jira/secure/useravatar?size=small&ownerId=admin&avatarId=10054"),
            "48x48", URI.create("http://localhost:8090/jira/secure/useravatar?ownerId=admin&avatarId=10054")), null);

    public static final String USER2_USERNAME = "user";

    public static final String USER2_PASSWORD = "user";

    @SuppressWarnings("UnusedDeclaration")
    public static final BasicUser USER2 = new BasicUser(toUri("http://localhost:8090/jira/rest/api/latest/user?username=user"), USER2_USERNAME, "My Test User");

    public static final Version VERSION_1 = new Version(toUri("http://localhost:8090/jira/rest/api/latest/version/10001"),
            10001L, "1", "initial version", false, false, null);

    public static final Version VERSION_1_1 = new Version(toUri("http://localhost:8090/jira/rest/api/latest/version/10000"),
            10000L, "1.1", "Some version", true, false, TestUtil.toDateTime("2010-08-25T00:00:00.000+0200"));

    public static final BasicComponent BCOMPONENT_A = new BasicComponent(toUri("http://localhost:8090/jira/rest/api/latest/component/10000"),
            10000L, "Component A", "this is some description of component A");

    public static final BasicComponent BCOMPONENT_B = new BasicComponent(toUri("http://localhost:8090/jira/rest/api/latest/component/10001"),
            10001L, "Component B", "another description");

    public static final String DEFAULT_JIRA_DUMP_FILE = "jira-dump.xml";
    public static final String DATA_FOR_UNIT_TESTS_FILE = "data-for-unit-tests.xml";
    public static final String EXPORT_FOR_HISTORY_TESTS_FILE = "export-for-history-tests.xml";
    public static final String JIRA_DUMP_CREATING_ISSUE_TESTS_FILE = "jira-dump-creating-issue-tests.xml";
    public static final String JIRA_DUMP_UNASSIGNED_FILE = "jira-dump-unassigned.xml";
    public static final String JIRA_DUMP_WITH_COMMENT_AND_WORKLOG_FROM_REMOVED_USER_FILE = "jira-dump-with-comment-and-worklog-from-removed-user.xml";
    public static final String JIRA_DUMP_WITH_FILTERS_FILE = "jira-dump-with-filters.xml";
}
