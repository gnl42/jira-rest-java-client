package me.glindholm.jira.rest.client.api.domain;

import java.net.URI;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 */
public class Subtask {

    private final long id;
    private final String issueKey;
    private final URI issueUri;
    private final String summary;
    private final IssueType issueType;
    private final Status status;

    public Subtask(long id, String issueKey, URI issueUri, String summary, IssueType issueType, Status status) {
        this.id = id;
        this.issueKey = issueKey;
        this.issueUri = issueUri;
        this.summary = summary;
        this.issueType = issueType;
        this.status = status;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public URI getIssueUri() {
        return issueUri;
    }

    public String getSummary() {
        return summary;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(super.toString()).
                add("id", id).
                add("issueKey", issueKey).
                add("issueUri", issueUri).
                add("summary", summary).
                add("issueType", issueType).
                add("status", status).
                toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Subtask) {
            Subtask that = (Subtask) obj;
            return super.equals(obj) && Objects.equal(id, that.id) && Objects.equal(issueKey, that.issueKey)
                    && Objects.equal(issueUri, that.issueUri)
                    && Objects.equal(summary, that.summary)
                    && Objects.equal(issueType, that.issueType)
                    && Objects.equal(status, that.status);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, issueKey, issueUri, summary, issueType, status);
    }

}
