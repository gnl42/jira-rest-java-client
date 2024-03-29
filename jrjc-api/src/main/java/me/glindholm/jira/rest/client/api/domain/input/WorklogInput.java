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

package me.glindholm.jira.rest.client.api.domain.input;

import java.net.URI;
import java.util.Objects;

import org.eclipse.jdt.annotation.Nullable;

import java.time.OffsetDateTime;

import me.glindholm.jira.rest.client.api.domain.BasicUser;
import me.glindholm.jira.rest.client.api.domain.Visibility;

/**
 * Represents worklog item in JIRA. Is used to create new worklog or update existing one.
 * Contains also estimate adjustment options which are used only to adjust change of remaining
 * estimate (adjustEstimate and adjustEstimateValue).
 * <p>
 * Possible values for adjustEstimate and adjustEstimateValue are:
 * <ul>
 *   <li>When adjustEstimate is set to {@link AdjustEstimate#AUTO} or {@link AdjustEstimate#LEAVE} adjustEstimateValue
 * is not used</li>
 *   <li>When adjustEstimate is set to {@link AdjustEstimate#NEW} then remaining estimate is set to adjustEstimateValue</li>
 *   <li>When adjustEstimate is set to {@link AdjustEstimate#MANUAL} then remaining estimate is reduced by adjustEstimateValue</li>
 * </ul>
 */
public class WorklogInput {
    @Nullable
    private final URI self;
    private final URI issueUri;
    @Nullable
    private final BasicUser author;
    @Nullable
    private final BasicUser updateAuthor;
    @Nullable
    private final String comment;
    private final OffsetDateTime startDate;
    private final int minutesSpent;
    @Nullable
    private final Visibility visibility;

    @Nullable
    private final String adjustEstimateValue;
    private final AdjustEstimate adjustEstimate;

    /**
     * Creates new WorklogInput with given values
     *
     * @param self                URI to this worklog, pass null if this is new worklog item.
     * @param issueUri            URI to destination issue
     * @param author              author of this worklog
     * @param updateAuthor        author of worklog actualization
     * @param comment             comment attached to worklog
     * @param startDate           date of work start
     * @param minutesSpent        time spend in minutes
     * @param visibility          visibility settings for this worklog
     * @param adjustEstimate      adjust estimate option
     * @param adjustEstimateValue value for estimate adjustment. Only used when adjustEstimate is set
     *                            to {@link AdjustEstimate#NEW} or {@link AdjustEstimate#MANUAL}
     */
    public WorklogInput(@Nullable URI self, URI issueUri, @Nullable BasicUser author, @Nullable BasicUser updateAuthor,
            @Nullable String comment, OffsetDateTime startDate, int minutesSpent, @Nullable Visibility visibility,
            AdjustEstimate adjustEstimate, @Nullable String adjustEstimateValue) {
        this.visibility = visibility;
        this.minutesSpent = minutesSpent;
        this.startDate = startDate;
        this.comment = comment;
        this.updateAuthor = updateAuthor;
        this.author = author;
        this.issueUri = issueUri;
        this.self = self;
        this.adjustEstimate = adjustEstimate;
        this.adjustEstimateValue = adjustEstimateValue;
    }

    /**
     * Creates new WorklogInput with given values. Sets adjust estimate option to default value - {@link AdjustEstimate#AUTO}.
     *
     * @param self         URI to this worklog, pass null if this is new worklog item.
     * @param issueUri     URI to destination issue
     * @param author       author of this worklog
     * @param updateAuthor author of worklog actualization
     * @param comment      comment attached to worklog
     * @param startDate    date of work start
     * @param minutesSpent time spend in minutes
     * @param visibility   visibility settings for this worklog
     */
    public WorklogInput(@Nullable URI self, URI issueUri, @Nullable BasicUser author, @Nullable BasicUser updateAuthor,
            @Nullable String comment, OffsetDateTime startDate, int minutesSpent, @Nullable Visibility visibility) {
        this(self, issueUri, author, updateAuthor, comment, startDate, minutesSpent, visibility, AdjustEstimate.AUTO, null);
    }

    public static WorklogInput create(URI issueUri, @Nullable String comment, OffsetDateTime startDate, int minutesSpent) {
        return new WorklogInputBuilder(issueUri).setComment(comment).setStartDate(startDate).setMinutesSpent(minutesSpent)
                .build();
    }

    public static WorklogInput create(URI issueUri, @Nullable String comment, OffsetDateTime startDate, int minutesSpent, @Nullable Visibility visibility) {
        return new WorklogInputBuilder(issueUri).setComment(comment).setStartDate(startDate).setMinutesSpent(minutesSpent)
                .setVisibility(visibility).build();
    }

    @Nullable
    public URI getSelf() {
        return self;
    }

    public URI getIssueUri() {
        return issueUri;
    }

    @Nullable
    public BasicUser getAuthor() {
        return author;
    }

    @Nullable
    public BasicUser getUpdateAuthor() {
        return updateAuthor;
    }

    @Nullable
    public String getComment() {
        return comment;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public int getMinutesSpent() {
        return minutesSpent;
    }

    @Nullable
    public Visibility getVisibility() {
        return visibility;
    }

    public AdjustEstimate getAdjustEstimate() {
        return adjustEstimate;
    }

    @Nullable
    public String getAdjustEstimateValue() {
        return adjustEstimateValue;
    }

    @Override
    public String toString() {
        return "WorklogInput [self=" + self + ", issueUri=" + issueUri + ", author=" + author + ", updateAuthor=" + updateAuthor + ", comment=" + comment
                + ", minutesSpent=" + minutesSpent + ", visibility=" + visibility + ", adjustEstimateValue=" + adjustEstimateValue + ", adjustEstimate="
                + adjustEstimate + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WorklogInput) {
            final WorklogInput that = (WorklogInput) obj;

            return Objects.equals(this.self, that.self)
                    && Objects.equals(this.issueUri, that.issueUri)
                    && Objects.equals(this.author, that.author)
                    && Objects.equals(this.updateAuthor, that.updateAuthor)
                    && Objects.equals(this.comment, that.comment)
                    && Objects.equals(this.startDate, that.startDate)
                    && Objects.equals(this.minutesSpent, that.minutesSpent)
                    && Objects.equals(this.visibility, that.visibility)
                    && Objects.equals(this.adjustEstimate, that.adjustEstimate)
                    && Objects.equals(this.adjustEstimateValue, that.adjustEstimateValue);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(self, issueUri, author, updateAuthor, comment, startDate, minutesSpent, visibility,
                adjustEstimate, adjustEstimateValue);
    }

    public static enum AdjustEstimate {
        /**
         * Set remaining estimate to given value.
         */
        NEW,
        /**
         * Leave estimate as is.
         */
        LEAVE,
        /**
         * Decrease estimate manually by given value.
         */
        MANUAL,
        /**
         * Automatically decrease estimate based on given time spent. This is the default value.
         */
        AUTO;

        public final String restValue;

        private AdjustEstimate() {
            restValue = this.name().toLowerCase();
        }
    }
}

