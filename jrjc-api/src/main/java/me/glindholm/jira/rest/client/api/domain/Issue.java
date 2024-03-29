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

package me.glindholm.jira.rest.client.api.domain;

import java.io.Serializable;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.Nullable;

import me.glindholm.jira.rest.client.api.ExpandableResource;
import me.glindholm.jira.rest.client.api.IssueRestClient.Expandos;
import me.glindholm.jira.rest.client.api.domain.util.UriUtil;

/**
 * Single JIRA issue
 *
 * @since v0.1
 */
public class Issue extends BasicIssue implements Serializable, ExpandableResource {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Issue [status=" + status + ", issueType=" + issueType + ", project=" + project + ", transitionsUri=" + transitionsUri + ", expandos=" + expandos
                + ", components=" + components + ", summary=" + summary + ", description=" + description + ", reporter=" + reporter + ", assignee=" + assignee
                + ", resolution=" + resolution + ", issueFields=" + issueFields + ", creationDate=" + creationDate + ", updateDate=" + updateDate + ", dueDate="
                + dueDate + ", priority=" + priority + ", votes=" + votes + ", fixVersions=" + fixVersions + ", affectedVersions=" + affectedVersions
                + ", comments=" + comments + ", issueLinks=" + issueLinks + ", attachments=" + attachments + ", worklogs=" + worklogs + ", watchers=" + watched
                + ", timeTracking=" + timeTracking + ", subtasks=" + subtasks + ", changelog=" + changelog + ", operations=" + operations + ", labels=" + labels
                + "]";
    }

    public Issue(final String summary, final URI self, final String key, final Long id, final BasicProject project, final IssueType issueType,
            final Status status, final String description, @Nullable final BasicPriority priority, @Nullable final Resolution resolution,
            final List<Attachment> attachments, @Nullable final User reporter, @Nullable final User assignee, final OffsetDateTime creationDate,
            final OffsetDateTime updateDate, final OffsetDateTime dueDate, final List<Version> affectedVersions, final List<Version> fixVersions,
            final List<BasicComponent> components, @Nullable final TimeTracking timeTracking, final List<IssueField> issueFields, final List<Comment> comments,
            @Nullable final URI transitionsUri, @Nullable final List<IssueLink> issueLinks, final BasicVotes votes, final List<Worklog> worklogs,
            final BasicWatchers watched, final List<String> expandos, @Nullable final List<Subtask> subtasks, @Nullable final List<ChangelogGroup> changelog,
            @Nullable final Operations operations, final Set<String> labels, final BasicIssue parent, final Map<String, CimFieldInfo> metadata) {
        super(self, key, id);
        this.summary = summary;
        this.project = project;
        this.status = status;
        this.description = description;
        this.resolution = resolution;
        this.expandos = expandos;
        this.comments = comments;
        this.attachments = attachments;
        this.issueFields = issueFields;
        this.issueType = issueType;
        this.reporter = reporter;
        this.assignee = assignee;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.dueDate = dueDate;
        this.transitionsUri = transitionsUri;
        this.issueLinks = issueLinks;
        this.votes = votes;
        this.worklogs = worklogs;
        this.watched = watched;
        this.fixVersions = fixVersions;
        this.affectedVersions = affectedVersions;
        this.components = components;
        this.priority = priority;
        this.timeTracking = timeTracking;
        this.subtasks = subtasks;
        this.changelog = changelog;
        this.operations = operations;
        this.labels = labels;

        this.parent = parent;

        this.metadata = metadata;
    }

    private final Status status;
    private final IssueType issueType;
    private final BasicProject project;
    private final URI transitionsUri;
    private final List<String> expandos;
    private final List<BasicComponent> components;
    private final String summary;
    @Nullable
    private final String description;
    @Nullable
    private final User reporter;
    private final User assignee;
    @Nullable
    private final Resolution resolution;
    private final List<IssueField> issueFields;
    private final OffsetDateTime creationDate;
    private final OffsetDateTime updateDate;
    private final OffsetDateTime dueDate;
    private final BasicPriority priority;
    private final BasicVotes votes;
    @Nullable
    private final List<Version> fixVersions;
    @Nullable
    private final List<Version> affectedVersions;

    private final List<Comment> comments;

    @Nullable
    private final List<IssueLink> issueLinks;

    private final List<Attachment> attachments;

    private final List<Worklog> worklogs;
    private final BasicWatchers watched;

    @Nullable
    private final TimeTracking timeTracking;
    @Nullable
    private final List<Subtask> subtasks;
    @Nullable
    private final List<ChangelogGroup> changelog;
    @Nullable
    private final Operations operations;
    private final Set<String> labels;

    @Nullable
    private final BasicIssue parent;

    @Nullable
    private final Map<String, CimFieldInfo> metadata;

    public Status getStatus() {
        return status;
    }

    /**
     * @return reporter of this issue or <code>null</code> if this issue has no
     *         reporter
     */
    @Nullable
    public User getReporter() {
        return reporter;
    }

    /**
     * @return assignee of this issue or <code>null</code> if this issue is
     *         unassigned.
     */
    @Nullable
    public User getAssignee() {
        return assignee;
    }

    public String getSummary() {
        return summary;
    }

    /**
     * @return priority of this issue
     */
    @Nullable
    public BasicPriority getPriority() {
        return priority;
    }

    /**
     * @return issue links for this issue (possibly nothing) or <code>null</code>
     *         when issue links are deactivated for this JIRA instance
     */
    @Nullable
    public List<IssueLink> getIssueLinks() {
        return issueLinks;
    }

    @Nullable
    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    /**
     * @return fields inaccessible by concrete getter methods (e.g. all custom
     *         issueFields)
     */
    public List<IssueField> getFields() {
        return issueFields;
    }

    /**
     * @param id identifier of the field (inaccessible by concrete getter method)
     * @return field with given id, or <code>null</code> when no field with given id
     *         exists for this issue
     */
    @Nullable
    public IssueField getField(final String id) {
        for (final IssueField issueField : issueFields) {
            if (issueField.getId().equals(id)) {
                return issueField;
            }
        }
        return null;
    }

    /**
     * This method returns the first field with specified name. Names of fields in
     * JIRA do not need to be unique. Therefore this method does not guarantee that
     * you will get what you really want. It's added just for convenience. For
     * identify fields you should use id rather than name.
     *
     * @param name name of the field.
     * @return the first field matching selected name or <code>null</code> when no
     *         field with given name exists for this issue
     */
    @Nullable
    public IssueField getFieldByName(final String name) {
        for (final IssueField issueField : issueFields) {
            if (issueField.getName().equals(name)) {
                return issueField;
            }
        }
        return null;
    }

    @Override
    public List<String> getExpandos() {
        return expandos;
    }

    /**
     * @return issue type
     */
    public IssueType getIssueType() {
        return issueType;
    }

    /**
     * @return attachments of this issue
     */
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public URI getAttachmentsUri() {
        return UriUtil.path(getSelf(), "attachments");
    }

    public URI getWorklogUri() {
        return UriUtil.path(getSelf(), "worklog");
    }

    /**
     * @return comments for this issue
     */
    public List<Comment> getComments() {
        return comments;
    }

    public URI getCommentsUri() {
        return UriUtil.path(getSelf(), "comment");
    }

    /**
     * @return project this issue belongs to
     */
    public BasicProject getProject() {
        return project;
    }

    /**
     * @return <code>null</code> when voting is disabled in JIRA
     */
    @Nullable
    public BasicVotes getVotes() {
        return votes;
    }

    public List<Worklog> getWorklogs() {
        return worklogs;
    }

    @Nullable
    public List<Version> getFixVersions() {
        return fixVersions;
    }

    @Nullable
    public URI getTransitionsUri() {
        return transitionsUri;
    }

    @Nullable
    public List<Version> getAffectedVersions() {
        return affectedVersions;
    }

    public List<BasicComponent> getComponents() {
        return components;
    }

    public Set<String> getLabels() {
        return labels;
    }

    /**
     * Returns changelog available for issues retrieved with CHANGELOG expanded.
     *
     * @return issue changelog or <code>null</code> if CHANGELOG has not been
     *         expanded or REST API on the server side does not serve this
     *         information (pre-5.0)
     * @see me.glindholm.jira.rest.client.api.IssueRestClient#getIssue(String, List)
     * @since me.glindholm.jira.rest.client.api 0.6, server 5.0
     */
    @Nullable
    public List<ChangelogGroup> getChangelog() {
        return changelog;
    }

    /**
     * Returns operations available/allowed for issues retrieved with
     * {@link Expandos#OPERATIONS} expanded.
     *
     * @return issue operations or <code>null</code> if {@link Expandos#OPERATIONS}
     *         has not been expanded or REST API on the server side does not serve
     *         this information (pre-5.0)
     * @see me.glindholm.jira.rest.client.api.IssueRestClient#getIssue(String, List)
     * @since me.glindholm.jira.rest.client.api 2.0, server 5.0
     */
    @Nullable
    public Operations getOperations() {
        return operations;
    }

    public URI getVotesUri() {
        return UriUtil.path(getSelf(), "votes");
    }

    @Nullable
    public Resolution getResolution() {
        return resolution;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public OffsetDateTime getUpdateDate() {
        return updateDate;
    }

    public OffsetDateTime getDueDate() {
        return dueDate;
    }

    @Nullable
    public TimeTracking getTimeTracking() {
        return timeTracking;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Override
    protected String getToStringHelper() {
        return toString();
    }

    public List<IssueField> getIssueFields() {
        return issueFields;
    }

    public BasicWatchers getWatched() {
        return watched;
    }

    public BasicIssue getParent() {
        return parent;
    }

    public Map<String, CimFieldInfo> getMetadata() {
        return metadata;
    }
}
