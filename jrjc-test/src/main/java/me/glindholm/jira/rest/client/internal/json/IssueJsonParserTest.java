/*
 * Copyright (C) 2010-2014 Atlassian
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

import static me.glindholm.jira.rest.client.TestUtil.toOffsetDateTime;
import static me.glindholm.jira.rest.client.TestUtil.toOffsetDateTimeFromIsoDate;
import static me.glindholm.jira.rest.client.TestUtil.toUri;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Ignore;
import org.junit.Test;

import me.glindholm.jira.rest.client.api.domain.Attachment;
import me.glindholm.jira.rest.client.api.domain.BasicPriority;
import me.glindholm.jira.rest.client.api.domain.BasicProject;
import me.glindholm.jira.rest.client.api.domain.BasicUser;
import me.glindholm.jira.rest.client.api.domain.ChangelogGroup;
import me.glindholm.jira.rest.client.api.domain.ChangelogItem;
import me.glindholm.jira.rest.client.api.domain.Comment;
import me.glindholm.jira.rest.client.api.domain.FieldType;
import me.glindholm.jira.rest.client.api.domain.Issue;
import me.glindholm.jira.rest.client.api.domain.IssueField;
import me.glindholm.jira.rest.client.api.domain.IssueLink;
import me.glindholm.jira.rest.client.api.domain.IssueLinkType;
import me.glindholm.jira.rest.client.api.domain.IssueType;
import me.glindholm.jira.rest.client.api.domain.OperationGroup;
import me.glindholm.jira.rest.client.api.domain.OperationHeader;
import me.glindholm.jira.rest.client.api.domain.OperationLink;
import me.glindholm.jira.rest.client.api.domain.Operations;
import me.glindholm.jira.rest.client.api.domain.Subtask;
import me.glindholm.jira.rest.client.api.domain.TimeTracking;
import me.glindholm.jira.rest.client.api.domain.Visibility;
import me.glindholm.jira.rest.client.api.domain.Worklog;

// Ignore "May produce NPE" warnings, as we know what we are doing in tests
@SuppressWarnings("ConstantConditions")
public class IssueJsonParserTest {
    @Test
    @Ignore("Not finding watchers")
    public void testParseIssue() throws Exception {
        final Issue issue = parseIssue("/json/issue/valid-all-expanded.json");

        assertEquals("Testing attachem2", issue.getSummary());
        assertEquals("TST-2", issue.getKey());
        assertEquals("my description", issue.getDescription());
        assertEquals(Long.valueOf(10010), issue.getId());

        final BasicProject expectedProject = new BasicProject(toUri("http://localhost:8090/jira/rest/api/2/project/TST"), "TST", 10000L, "Test Project");
        assertEquals(expectedProject, issue.getProject());

        assertEquals("Major", issue.getPriority().getName());
        assertNull(issue.getResolution());
        assertEquals(toOffsetDateTime("2010-07-26T13:29:18.262+0200"), issue.getCreationDate());
        assertEquals(toOffsetDateTime("2012-12-07T14:52:52.570+0100"), issue.getUpdateDate());
        assertEquals(null, issue.getDueDate());

        final IssueType expectedIssueType = new IssueType(toUri("http://localhost:8090/jira/rest/api/2/issuetype/1"), 1L, "Bug", false,
                "A problem which impairs or prevents the functions of the product.", toUri("http://localhost:8090/jira/images/icons/bug.gif"));
        assertEquals(expectedIssueType, issue.getIssueType());

        assertEquals(TestConstants.USER_ADMIN, issue.getReporter());
        assertEquals(TestConstants.USER1, issue.getAssignee());

        // issue links
        assertThat(issue.getIssueLinks(),
                containsInAnyOrder(
                        new IssueLink("TST-1", toUri("http://localhost:8090/jira/rest/api/2/issue/10000"),
                                new IssueLinkType("Duplicate", "duplicates", IssueLinkType.Direction.OUTBOUND)),
                        new IssueLink("TST-1", toUri("http://localhost:8090/jira/rest/api/2/issue/10000"),
                                new IssueLinkType("Duplicate", "is duplicated by", IssueLinkType.Direction.INBOUND))));

        // watchers
//        final BasicWatchers watchers = issue.getWatchers();
//        assertNotNull(watchers);
//        assertFalse(watchers.isWatching());
//        assertEquals(toUri("http://localhost:8090/jira/rest/api/2/issue/TST-2/watchers"), watchers.getSelf());
//        assertEquals(1, watchers.getNumWatchers());

        // time tracking
        assertEquals(new TimeTracking(0, 0, 145), issue.getTimeTracking());

        // attachments
        final List<Attachment> attachments = issue.getAttachments();
        assertEquals(7, attachments.size());
        final Attachment attachment = attachments.stream().filter(file -> file.getFilename().equals("avatar1.png")).findAny().orElse(null);
        assertEquals(TestConstants.USER_ADMIN_BASIC, attachment.getAuthor());
        assertEquals(359345, attachment.getSize());
        assertEquals(toUri("http://localhost:8090/jira/secure/thumbnail/10070/_thumb_10070.png"), attachment.getThumbnailUri());
        assertEquals(toUri("http://localhost:8090/jira/secure/attachment/10070/avatar1.png"), attachment.getContentUri());
        final List<String> attachmentsNames = attachments.stream().map(file -> file.getFilename()).collect(Collectors.toList());
        assertThat(attachmentsNames, containsInAnyOrder("10000_thumb_snipe.jpg", "Admal pompa ciepła.pdf", "apache-tomcat-5.5.30.zip", "avatar1.png",
                "jira_logo.gif", "snipe.png", "transparent-png.png"));

        // worklogs
        final List<Worklog> worklogs = issue.getWorklogs();
        assertEquals(5, worklogs.size());
        final Worklog expectedWorklog1 = new Worklog(toUri("http://localhost:8090/jira/rest/api/2/issue/10010/worklog/10011"),
                toUri("http://localhost:8090/jira/rest/api/latest/issue/10010"), TestConstants.USER1_BASIC, TestConstants.USER1_BASIC, "another piece of work",
                toOffsetDateTime("2010-08-17T16:38:00.013+02:00"), toOffsetDateTime("2010-08-17T16:38:24.948+02:00"),
                toOffsetDateTime("2010-08-17T16:37:00.000+02:00"), 15, Visibility.role("Developers"));
        final Worklog worklog1 = worklogs.get(1);
        final Worklog a = worklogs.get(1);
        assertEquals(expectedWorklog1, worklog1);

        final Worklog worklog2 = worklogs.get(2);
        assertEquals(Visibility.group("jira-users"), worklog2.getVisibility());

        final Worklog worklog3 = worklogs.get(3);
        assertEquals(StringUtils.EMPTY, worklog3.getComment());

        // comments
        assertEquals(4, issue.getComments().size());
        final Comment comment = issue.getComments().iterator().next();
        assertEquals(Visibility.Type.ROLE, comment.getVisibility().getType());
        assertEquals(TestConstants.USER_ADMIN_BASIC, comment.getAuthor());
        assertEquals(TestConstants.USER_ADMIN_BASIC, comment.getUpdateAuthor());

        // components
        final List<String> componentsNames = issue.getComponents().stream().map(name -> name.getName()).collect(Collectors.toList());
        assertThat(componentsNames, containsInAnyOrder("Component A", "Component B"));
    }

    @Test
    public void testParseIssueWithCustomFieldsValues() throws Exception {
        final Issue issue = parseIssue("/json/issue/valid-all-expanded.json");

        // test float value: number,
        // com.atlassian.jira.plugin.system.customfieldtypes:float
        assertEquals(1.457, issue.getField("customfield_10000").getValue());

        // TODO: add assertions for more custom field types after fixing JRJC-122
    }

    private Issue parseIssue(final String resourcePath) throws JSONException, URISyntaxException {
        final JSONObject issueJson = ResourceUtil.getJsonObjectFromResource(resourcePath);
        final IssueJsonParser parser = new IssueJsonParser();
        return parser.parse(issueJson);
    }

    @Test
    public void testParseIssueWithResolution() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-all-expanded-with-resolution.json");
        assertEquals("Incomplete", issue.getResolution().getName());

    }

//    @Test
//    public void testParseIssueWhenWatchersAndVotersAreSwitchedOff() throws JSONException, URISyntaxException {
//        final Issue issue = parseIssue("/json/issue/valid-no-votes-no-watchers.json");
//        assertNull(issue.getWatchers());
//        assertNull(issue.getVotes());
//    }

    @Test
    public void testParseUnassignedIssue() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-unassigned-no-time-tracking.json");
        assertNull(issue.getAssignee());
    }

    @Test
    public void testParseNoTimeTrackingInfo() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-unassigned-no-time-tracking.json");
        assertNull(issue.getTimeTracking());
    }

    @Test
    public void testParseIssueWithAnonymousComment() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-anonymous-comment.json");
        assertEquals(1, issue.getComments().size());
        final Comment comment = issue.getComments().iterator().next();
        assertEquals("Comment from anonymous user", comment.getBody());
        assertNull(comment.getAuthor());

    }

    @Test
    public void testParseIssueWithVisibility() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-visibility.json");
        assertEquals(Visibility.role("Administrators"), issue.getComments().iterator().next().getVisibility());
        assertEquals(Visibility.role("Developers"), issue.getWorklogs().get(1).getVisibility());
        assertEquals(Visibility.group("jira-users"), issue.getWorklogs().get(2).getVisibility());
    }

    // TODO: temporary disabled as we want to run integration tests. Fix JRJC-122
    // and re-enable this test
    // @Test
    // public void testParseIssueWithUserPickerCustomFieldFilledOut() throws
    // JSONException, URISyntaxException {
    // final Issue issue =
    // parseIssue("/json/issue/valid-user-picker-custom-field-filled-out.json");
    // final IssueField extraUserField = issue.getFieldByName("Extra User");
    // assertNotNull(extraUserField);
    // assertEquals(BasicUser.class, extraUserField.getValue().getClass());
    // assertEquals(TestConstants.USER1, extraUserField.getValue());
    // }

    @Test
    public void testParseIssueWithUserPickerCustomFieldEmpty() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-user-picker-custom-field-empty.json");
        final IssueField extraUserIssueField = issue.getFieldByName("Extra User");
        assertNotNull(extraUserIssueField);
        assertNull(extraUserIssueField.getValue());
    }

    @Test
    @Ignore("Can't find watchers")
    public void testParseIssueJira5x0Representation() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-5.0.json");
        assertEquals(3, issue.getComments().size());
        final BasicPriority priority = issue.getPriority();
        assertNotNull(priority);
        assertEquals("Major", priority.getName());
        assertEquals("my description", issue.getDescription());
        assertEquals("TST", issue.getProject().getKey());
        assertEquals(Long.valueOf(10000), issue.getId());
        assertNotNull(issue.getDueDate());
        assertEquals(toOffsetDateTimeFromIsoDate("2010-07-05"), issue.getDueDate());
        assertEquals(4, issue.getAttachments().size());
        assertEquals(1, issue.getIssueLinks().size());
        assertEquals(1.457, issue.getField("customfield_10000").getValue());
        assertThat(issue.getComponents().stream().map(entity -> entity.getId()).collect(Collectors.toList()), containsInAnyOrder("Component A", "Component B"));
        assertEquals(2, issue.getWorklogs().size());
//        assertEquals(1, issue.getWatchers().getNumWatchers());
//        assertFalse(issue.getWatchers().isWatching());
        assertEquals(new TimeTracking(2700, 2220, 180), issue.getTimeTracking());

        assertEquals(Visibility.role("Developers"), issue.getWorklogs().iterator().next().getVisibility());
        assertEquals(Visibility.group("jira-users"), issue.getWorklogs().get(1).getVisibility());

    }

    @Test
    @Ignore("Unable to find watchers")
    public void testParseIssueJira50Representation() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-5.0-1.json");
        assertEquals(Long.valueOf(10001), issue.getId());
        assertEquals(0, issue.getComments().size());
        final BasicPriority priority = issue.getPriority();
        assertNull(priority);
        assertEquals(
                "Pivotal Tracker provides time tracking information on the project level.\n"
                        + "JIRA stores time tracking information on issue level, so this issue has been created to store imported time tracking information.",
                issue.getDescription());
        assertEquals("TIMETRACKING", issue.getProject().getKey());
        assertNull(issue.getDueDate());
        assertEquals(0, issue.getAttachments().size());
        assertNull(issue.getIssueLinks());
        assertNull(issue.getField("customfield_10000").getValue());
        assertEquals(issue.getComponents(), Collections.emptyList());
        assertEquals(2, issue.getWorklogs().size());
//        assertNotNull(issue.getWatchers());
//        assertEquals(0, issue.getWatchers().getNumWatchers());
//        assertFalse(issue.getWatchers().isWatching());
        assertEquals(new TimeTracking(null, null, 840), issue.getTimeTracking());

        assertNull(issue.getWorklogs().iterator().next().getVisibility());
        assertNull(issue.getWorklogs().get(1).getVisibility());
    }

    @Test
    public void testParseIssueWithProjectNamePresentInRepresentation() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/issue-with-project-name-present.json");
        assertEquals("My Test Project", issue.getProject().getName());
    }

    @Test
    public void testParseIssueJiraRepresentationJrjc49() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/jrjc49.json");
        final List<Worklog> worklogs = issue.getWorklogs();
        assertEquals(1, worklogs.size());
        final Worklog worklog = worklogs.get(0);
        assertEquals("Worklog comment should be returned as empty string, when JIRA doesn't include it in reply", StringUtils.EMPTY, worklog.getComment());
        assertEquals(180, worklog.getMinutesSpent());
        assertEquals("deleteduser", worklog.getAuthor().getName());
    }

    @Test
    public void testParseIssueJira5x0RepresentationNullCustomField() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-5.0-null-custom-field.json");
        assertEquals(null, issue.getField("customfield_10000").getValue());
        assertNull(issue.getIssueLinks());
    }

    @Test
    public void issueWithSubtasks() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/subtasks-5.json");
        final List<Subtask> subtasks = issue.getSubtasks();
        assertEquals(1, subtasks.size());
        final Subtask subtask = subtasks.iterator().next();
        assertNotNull(subtask);
        assertEquals("SAM-2", subtask.getIssueKey());
        assertEquals("Open", subtask.getStatus().getName());
        assertEquals("Subtask", subtask.getIssueType().getName());
    }

    @Test
    public void issueWithChangelog() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-5.0-with-changelog.json");
        assertEquals("HST-1", issue.getKey());

        final List<ChangelogGroup> changelog = issue.getChangelog();
        assertNotNull(changelog);

        assertEquals(4, changelog.size());
        final Iterator<ChangelogGroup> iterator = changelog.iterator();

        final BasicUser user1 = new BasicUser(toUri("http://localhost:2990/jira/rest/api/2/user?username=user1"), "user1", "User One");
        final BasicUser user2 = new BasicUser(toUri("http://localhost:2990/jira/rest/api/2/user?username=user2"), "user2", "User Two");

        verifyChangelog(iterator.next(), "2012-04-12T14:28:28.255+0200", user1,
                List.of(new ChangelogItem(FieldType.JIRA, "duedate", null, null, "2012-04-12", "2012-04-12 00:00:00.0"),
                        new ChangelogItem(FieldType.CUSTOM, "Radio Field", null, null, "10000", "One")));

        verifyChangelog(iterator.next(), "2012-04-12T14:28:44.079+0200", user1,
                List.of(new ChangelogItem(FieldType.JIRA, "assignee", "user1", "User One", "user2", "User Two")));

        verifyChangelog(iterator.next(), "2012-04-12T14:30:09.690+0200", user2,
                List.of(new ChangelogItem(FieldType.JIRA, "summary", null, "Simple history test", null, "Simple history test - modified"),
                        new ChangelogItem(FieldType.JIRA, "issuetype", "1", "Bug", "2", "New Feature"),
                        new ChangelogItem(FieldType.JIRA, "priority", "3", "Major", "4", "Minor"),
                        new ChangelogItem(FieldType.JIRA, "description", null, "Initial Description", null, "Modified Description"),
                        new ChangelogItem(FieldType.CUSTOM, "Date Field", "2012-04-11T14:26+0200", "11/Apr/12 2:26 PM", "2012-04-12T14:26+0200",
                                "12/Apr/12 2:26 PM"),
                        new ChangelogItem(FieldType.JIRA, "duedate", "2012-04-12", "2012-04-12 00:00:00.0", "2012-04-13", "2012-04-13 00:00:00.0"),
                        new ChangelogItem(FieldType.CUSTOM, "Radio Field", "10000", "One", "10001", "Two"),
                        new ChangelogItem(FieldType.CUSTOM, "Text Field", null, "Initial text field value", null, "Modified text field value")));

        verifyChangelog(iterator.next(), "2012-04-12T14:28:44.079+0200", null,
                List.of(new ChangelogItem(FieldType.JIRA, "assignee", "user1", "User One", "user2", "User Two")));
    }

    private static void verifyChangelog(final ChangelogGroup changelogGroup, final String createdDate, final BasicUser author,
            final List<ChangelogItem> expectedItems) {
        assertEquals(toOffsetDateTime(createdDate), changelogGroup.getCreated());
        assertEquals(author, changelogGroup.getAuthor());
        assertEquals(expectedItems, changelogGroup.getItems());
    }

    @Test
    public void testParseIssueWithLabelsForJira5x0() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-5.0-with-labels.json");
        assertThat(issue.getLabels(), containsInAnyOrder("a", "bcds"));
    }

    @Test
    public void testParseIssueWithLabels() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-5.0-with-labels.json");
        assertThat(issue.getLabels(), containsInAnyOrder("a", "bcds"));
    }

    @Test
    public void testParseIssueWithoutLabelsForJira5x0() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-5.0-without-labels.json");
        assertThat(issue.getLabels(), IsEmptyCollection.<String>empty());
    }

    @Test
    public void testParseIssueWithOperations() throws JSONException, URISyntaxException {
        final Issue issue = parseIssue("/json/issue/valid-5.0-with-operations.json");
        assertThat(issue.getOperations(),
                is(new Operations(List.of(new OperationGroup("opsbar-transitions",
                        List.of(new OperationLink("action_id_4", "issueaction-workflow-transition", "Start Progress", "Start work on the issue",
                                "/secure/WorkflowUIDispatcher.jspa?id=93813&action=4&atl_token=", 10, null)),
                        List.of(new OperationGroup(null, Collections.emptyList(), Collections.emptyList(),
                                new OperationHeader("opsbar-transitions_more", "Workflow", null, null), null)),
                        null, 20)))));
    }

}
