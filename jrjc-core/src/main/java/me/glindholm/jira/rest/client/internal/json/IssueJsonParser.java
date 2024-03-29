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

import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.AFFECTS_VERSIONS_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.ASSIGNEE_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.ATTACHMENT_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.COMMENT_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.COMPONENTS_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.CREATED_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.DESCRIPTION_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.DUE_DATE_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.FIX_VERSIONS_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.ISSUE_TYPE_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.LABELS_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.LINKS_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.PRIORITY_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.PROJECT_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.REPORTER_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.RESOLUTION_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.STATUS_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.SUBTASKS_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.SUMMARY_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.TIMETRACKING_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.UPDATED_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.VOTES_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.WATCHED_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.WORKLOGS_FIELD;
import static me.glindholm.jira.rest.client.api.domain.IssueFieldId.WORKLOG_FIELD;
import static me.glindholm.jira.rest.client.internal.json.JsonParseUtil.getStringKeys;
import static me.glindholm.jira.rest.client.internal.json.JsonParseUtil.parseOptionalJsonObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hc.core5.net.URIBuilder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jdt.annotation.Nullable;

import me.glindholm.jira.rest.client.api.IssueRestClient;
import me.glindholm.jira.rest.client.api.domain.Attachment;
import me.glindholm.jira.rest.client.api.domain.BasicComponent;
import me.glindholm.jira.rest.client.api.domain.BasicIssue;
import me.glindholm.jira.rest.client.api.domain.BasicPriority;
import me.glindholm.jira.rest.client.api.domain.BasicProject;
import me.glindholm.jira.rest.client.api.domain.BasicVotes;
import me.glindholm.jira.rest.client.api.domain.BasicWatchers;
import me.glindholm.jira.rest.client.api.domain.ChangelogGroup;
import me.glindholm.jira.rest.client.api.domain.CimFieldInfo;
import me.glindholm.jira.rest.client.api.domain.Comment;
import me.glindholm.jira.rest.client.api.domain.Issue;
import me.glindholm.jira.rest.client.api.domain.IssueField;
import me.glindholm.jira.rest.client.api.domain.IssueFieldId;
import me.glindholm.jira.rest.client.api.domain.IssueLink;
import me.glindholm.jira.rest.client.api.domain.IssueType;
import me.glindholm.jira.rest.client.api.domain.Operations;
import me.glindholm.jira.rest.client.api.domain.Resolution;
import me.glindholm.jira.rest.client.api.domain.Status;
import me.glindholm.jira.rest.client.api.domain.Subtask;
import me.glindholm.jira.rest.client.api.domain.TimeTracking;
import me.glindholm.jira.rest.client.api.domain.User;
import me.glindholm.jira.rest.client.api.domain.Version;
import me.glindholm.jira.rest.client.api.domain.Worklog;

public class IssueJsonParser implements JsonObjectParser<Issue> {

    private static Set<String> SPECIAL_FIELDS = new HashSet<>(IssueFieldId.ids());

    public static final String SCHEMA_SECTION = "schema";
    public static final String NAMES_SECTION = "names";

    private final BasicIssueJsonParser basicIssueJsonParser = new BasicIssueJsonParser();
    private final IssueLinkJsonParserV5 issueLinkJsonParserV5 = new IssueLinkJsonParserV5();
    private final BasicVotesJsonParser votesJsonParser = new BasicVotesJsonParser();
    private final StatusJsonParser statusJsonParser = new StatusJsonParser();
    private final JsonObjectParser<BasicWatchers> watchersJsonParser = WatchersJsonParserBuilder.createBasicWatchersParser();
    private final VersionJsonParser versionJsonParser = new VersionJsonParser();
    private final BasicComponentJsonParser basicComponentJsonParser = new BasicComponentJsonParser();
    private final AttachmentJsonParser attachmentJsonParser = new AttachmentJsonParser();
    private final CommentJsonParser commentJsonParser = new CommentJsonParser();
    private final IssueTypeJsonParser issueTypeJsonParser = new IssueTypeJsonParser();
    private final BasicProjectJsonParser projectJsonParser = new BasicProjectJsonParser();
    private final BasicPriorityJsonParser priorityJsonParser = new BasicPriorityJsonParser();
    private final ResolutionJsonParser resolutionJsonParser = new ResolutionJsonParser();
    private final UserJsonParser userJsonParser = new UserJsonParser();
    private final SubtaskJsonParser subtaskJsonParser = new SubtaskJsonParser();
    private final ChangelogJsonParser changelogJsonParser = new ChangelogJsonParser();
    private final OperationsJsonParser operationsJsonParser = new OperationsJsonParser();
    private final JsonWeakParserForString jsonWeakParserForString = new JsonWeakParserForString();
    private final CimFieldsInfoMapJsonParser metaParserForString = new CimFieldsInfoMapJsonParser();

    private static final String FIELDS = "fields";
    private static final String VALUE_ATTR = "value";

    private final JSONObject providedNames;
    private final JSONObject providedSchema;

    public IssueJsonParser() {
        providedNames = null;
        providedSchema = null;
    }

    public IssueJsonParser(final JSONObject providedNames, final JSONObject providedSchema) {
        this.providedNames = providedNames;
        this.providedSchema = providedSchema;
    }

    static List<String> parseExpandos(final JSONObject json) throws JSONException {
        final String expando = json.getString("expand");
        return List.of(expando.split(","));
        // return Splitter.on(',').split(expando);
    }

    private <T> List<T> parseArray(final JSONObject jsonObject, final JsonWeakParser<T> jsonParser, final String arrayAttribute)
            throws JSONException, URISyntaxException {
        // String type = jsonObject.getString("type");
        // final String name = jsonObject.getString("name");
        final JSONArray valueObject = jsonObject.optJSONArray(arrayAttribute);
        if (valueObject == null) {
            return new ArrayList<>();
        }
        final List<T> res = new ArrayList<>(valueObject.length());
        for (int i = 0; i < valueObject.length(); i++) {
            res.add(jsonParser.parse(valueObject.get(i)));
        }
        return res;
    }

    private <T> List<T> parseOptionalArrayNotNullable(final JSONObject json, final JsonWeakParser<T> jsonParser, final String... path)
            throws JSONException, URISyntaxException {
        final List<T> res = parseOptionalArray(json, jsonParser, path);
        return res == null ? Collections.<T>emptyList() : res;
    }

    @Nullable
    private <T> List<T> parseOptionalArray(final JSONObject json, final JsonWeakParser<T> jsonParser, final String... path)
            throws JSONException, URISyntaxException {
        final JSONArray jsonArray = JsonParseUtil.getNestedOptionalArray(json, path);
        if (jsonArray == null) {
            return null;
        }
        final List<T> res = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            res.add(jsonParser.parse(jsonArray.get(i)));
        }
        return res;
    }

    private String getFieldStringValue(final JSONObject json, final String attributeName) throws JSONException {
        final JSONObject fieldsJson = json.getJSONObject(FIELDS);

        final Object summaryObject = fieldsJson.get(attributeName);
        if (summaryObject instanceof JSONObject) { // pre JIRA 5.0 way
            return ((JSONObject) summaryObject).getString(VALUE_ATTR);
        }
        if (summaryObject instanceof String) { // JIRA 5.0 way
            return (String) summaryObject;
        }
        throw new JSONException("Cannot parse [" + attributeName + "] from available fields");
    }

    private JSONObject getFieldUnisex(final JSONObject json, final String attributeName) throws JSONException {
        final JSONObject fieldsJson = json.getJSONObject(FIELDS);
        final JSONObject fieldJson = fieldsJson.getJSONObject(attributeName);
        if (fieldJson.has(VALUE_ATTR)) {
            return fieldJson.getJSONObject(VALUE_ATTR); // pre 5.0 way
        } else {
            return fieldJson; // JIRA 5.0 way
        }
    }

    @Nullable
    private String getOptionalFieldStringUnisex(final JSONObject json, final String attributeName) throws JSONException {
        final JSONObject fieldsJson = json.getJSONObject(FIELDS);
        return JsonParseUtil.getOptionalString(fieldsJson, attributeName);
    }

    private String getFieldStringUnisex(final JSONObject json, final String attributeName) throws JSONException {
        final JSONObject fieldsJson = json.getJSONObject(FIELDS);
        final Object fieldJson = fieldsJson.get(attributeName);
        if (fieldJson instanceof JSONObject) {
            return ((JSONObject) fieldJson).getString(VALUE_ATTR); // pre 5.0 way
        }
        return fieldJson.toString(); // JIRA 5.0 way
    }

    @Override
    public Issue parse(final JSONObject issueJson) throws JSONException, URISyntaxException {
        final BasicIssue basicIssue = basicIssueJsonParser.parse(issueJson);
        final List<String> expandos = parseExpandos(issueJson);
        final JSONObject jsonFields = issueJson.optJSONObject(FIELDS);
        final List<Comment> comments;
        if (jsonFields != null) {
            final JSONObject commentsJson = jsonFields.optJSONObject(COMMENT_FIELD.id);
            comments = commentsJson == null ? Collections.emptyList()
                    : parseArray(commentsJson, new JsonWeakParserForJsonObject<>(commentJsonParser), "comments");
        } else {
            comments = Collections.emptyList();
        }

        final String summary = getFieldStringValue(issueJson, SUMMARY_FIELD.id);
        final String description = getOptionalFieldStringUnisex(issueJson, DESCRIPTION_FIELD.id);

        final List<Attachment> attachments = parseOptionalArray(issueJson, new JsonWeakParserForJsonObject<>(attachmentJsonParser), FIELDS,
                ATTACHMENT_FIELD.id);
        final List<IssueField> fields = parseFields(issueJson);

        final IssueType issueType = issueTypeJsonParser.parse(getFieldUnisex(issueJson, ISSUE_TYPE_FIELD.id));
        final OffsetDateTime creationDate = JsonParseUtil.parseOffsetDateTime(getFieldStringUnisex(issueJson, CREATED_FIELD.id));
        final OffsetDateTime updateDate = JsonParseUtil.parseOffsetDateTime(getFieldStringUnisex(issueJson, UPDATED_FIELD.id));

        final String dueDateString = getOptionalFieldStringUnisex(issueJson, DUE_DATE_FIELD.id);
        final OffsetDateTime dueDate = dueDateString == null ? null : JsonParseUtil.parseOffsetDateTimeOrDate(dueDateString);

        final BasicPriority priority = getOptionalNestedField(issueJson, PRIORITY_FIELD.id, priorityJsonParser);
        final Resolution resolution = getOptionalNestedField(issueJson, RESOLUTION_FIELD.id, resolutionJsonParser);
        final User assignee = getOptionalNestedField(issueJson, ASSIGNEE_FIELD.id, userJsonParser);
        final User reporter = getOptionalNestedField(issueJson, REPORTER_FIELD.id, userJsonParser);

        final BasicProject project = projectJsonParser.parse(getFieldUnisex(issueJson, PROJECT_FIELD.id));
        final List<IssueLink> issueLinks;
        issueLinks = parseOptionalArray(issueJson, new JsonWeakParserForJsonObject<>(issueLinkJsonParserV5), FIELDS, LINKS_FIELD.id);

        final List<Subtask> subtasks = parseOptionalArray(issueJson, new JsonWeakParserForJsonObject<>(subtaskJsonParser), FIELDS, SUBTASKS_FIELD.id);

        final BasicVotes votes = getOptionalNestedField(issueJson, VOTES_FIELD.id, votesJsonParser);
        final Status status = statusJsonParser.parse(getFieldUnisex(issueJson, STATUS_FIELD.id));

        final List<Version> fixVersions = parseOptionalArray(issueJson, new JsonWeakParserForJsonObject<>(versionJsonParser), FIELDS, FIX_VERSIONS_FIELD.id);
        final List<Version> affectedVersions = parseOptionalArray(issueJson, new JsonWeakParserForJsonObject<>(versionJsonParser), FIELDS,
                AFFECTS_VERSIONS_FIELD.id);
        final List<BasicComponent> components = parseOptionalArray(issueJson, new JsonWeakParserForJsonObject<>(basicComponentJsonParser), FIELDS,
                COMPONENTS_FIELD.id);

        final List<Worklog> worklogs;
        final URI selfUri = basicIssue.getSelf();

        final String transitionsUriString;
        if (issueJson.has(IssueFieldId.TRANSITIONS_FIELD.id)) {
            final Object transitionsObj = issueJson.get(IssueFieldId.TRANSITIONS_FIELD.id);
            transitionsUriString = transitionsObj instanceof String ? (String) transitionsObj : null;
        } else {
            transitionsUriString = getOptionalFieldStringUnisex(issueJson, IssueFieldId.TRANSITIONS_FIELD.id);
        }
        final URI transitionsUri = parseTransisionsUri(transitionsUriString, selfUri);

        if (JsonParseUtil.getNestedOptionalObject(issueJson, FIELDS, WORKLOG_FIELD.id) != null) {
            worklogs = parseOptionalArray(issueJson, new JsonWeakParserForJsonObject<>(new WorklogJsonParserV5(selfUri)), FIELDS, WORKLOG_FIELD.id,
                    WORKLOGS_FIELD.id);
        } else {
            worklogs = Collections.emptyList();
        }

        final BasicWatchers watched = getOptionalNestedField(issueJson, WATCHED_FIELD.id, watchersJsonParser);

        final TimeTracking timeTracking = getOptionalNestedField(issueJson, TIMETRACKING_FIELD.id, new TimeTrackingJsonParserV5());

        final Set<String> labels = new HashSet<>(parseOptionalArrayNotNullable(issueJson, jsonWeakParserForString, FIELDS, LABELS_FIELD.id));

        final List<ChangelogGroup> changelog = parseOptionalArray(issueJson, new JsonWeakParserForJsonObject<>(changelogJsonParser), "changelog", "histories");
        final Operations operations = parseOptionalJsonObject(issueJson, "operations", operationsJsonParser);

        final BasicIssue parent = getOptionalNestedField(issueJson, "parent", basicIssueJsonParser);

        final JSONObject editmeta = issueJson.optJSONObject(IssueRestClient.Expandos.EDITMETA.getValue());
        Map<String, CimFieldInfo> metadata = null;
        if (editmeta != null) {
            final JSONObject metaFields = editmeta.getJSONObject("fields");
            metadata = metaParserForString.parse(metaFields);
        }
        return new Issue(summary, selfUri, basicIssue.getKey(), basicIssue.getId(), project, issueType, status, description, priority, resolution, attachments,
                reporter, assignee, creationDate, updateDate, dueDate, affectedVersions, fixVersions, components, timeTracking, fields, comments,
                transitionsUri, issueLinks, votes, worklogs, watched, expandos, subtasks, changelog, operations, labels, parent, metadata);
    }

    private URI parseTransisionsUri(final String transitionsUriString, final URI selfUri) throws URISyntaxException {
        return transitionsUriString != null ? JsonParseUtil.parseURI(transitionsUriString)
                : new URIBuilder(selfUri).appendPath("transitions").addParameter("expand", "transitions.fields").build();
    }

    @Nullable
    private <T> T getOptionalNestedField(final JSONObject s, final String fieldId, final JsonObjectParser<T> jsonParser)
            throws JSONException, URISyntaxException {
        final JSONObject fieldJson = JsonParseUtil.getNestedOptionalObject(s, FIELDS, fieldId);
        // for fields like assignee (when unassigned) value attribute may be missing
        // completely
        if (fieldJson != null) {
            return jsonParser.parse(fieldJson);
        }
        return null;
    }

    private List<IssueField> parseFields(final JSONObject issueJson) throws JSONException {
        final JSONObject names = providedNames != null ? providedNames : issueJson.optJSONObject(NAMES_SECTION);
        final Map<String, String> namesMap = parseNames(names);
        final JSONObject schema = providedSchema != null ? providedSchema : issueJson.optJSONObject(SCHEMA_SECTION);
        final Map<String, String> typesMap = parseSchema(schema);

        final JSONObject json = issueJson.getJSONObject(FIELDS);
        final List<IssueField> fields = new ArrayList<>(json.length());
        @SuppressWarnings("unchecked")
        final Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            final String key = iterator.next();
            try {
                // if (SPECIAL_FIELDS.contains(key)) {
                // continue;
                // }
                // TODO: JRJC-122
                // we should use fieldParser here (some new version as the old one probably
                // won't work)
                // enable IssueJsonParserTest#testParseIssueWithUserPickerCustomFieldFilledOut
                // after fixing this
                final Object value = json.opt(key);
                fields.add(new IssueField(key, namesMap.get(key), typesMap.get("key"),
                        value == JSONObject.NULL || value == JSONObject.EXPLICIT_NULL ? null : value));
            } catch (final Exception e) {
                throw new JSONException("Error while parsing [" + key + "] field: " + e.getMessage()) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Throwable getCause() {
                        return e;
                    }
                };
            }
        }
        return fields;
    }

    private Map<String, String> parseSchema(final JSONObject json) throws JSONException {
        final HashMap<String, String> res = new HashMap<>();
        final Iterator<String> it = JsonParseUtil.getStringKeys(json);
        while (it.hasNext()) {
            final String fieldId = it.next();
            final JSONObject fieldDefinition = json.getJSONObject(fieldId);
            res.put(fieldId, fieldDefinition.getString("type"));

        }
        return res;
    }

    private Map<String, String> parseNames(final JSONObject json) throws JSONException {
        final HashMap<String, String> res = new HashMap<>();
        final Iterator<String> iterator = getStringKeys(json);
        while (iterator.hasNext()) {
            final String key = iterator.next();
            res.put(key, json.getString(key));
        }
        return res;
    }

}