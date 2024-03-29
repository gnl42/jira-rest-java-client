/*
 * Copyright (C) 2011 Atlassian
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

import java.util.Objects;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Represents time tracking information associated with given issue
 *
 * @since me.glindholm.jira.rest.client.api 0.3, server 4.4
 */
public class TimeTracking {
    @Nullable
    private final Integer originalEstimateMinutes;

    @Nullable
    private final Integer remainingEstimateMinutes;
    @Nullable
    private final Integer timeSpentMinutes;

    public TimeTracking(@Nullable Integer originalEstimateMinutes, @Nullable Integer remainingEstimateMinutes, @Nullable Integer timeSpentMinutes) {
        this.originalEstimateMinutes = originalEstimateMinutes;
        this.remainingEstimateMinutes = remainingEstimateMinutes;
        this.timeSpentMinutes = timeSpentMinutes;
    }

    /**
     * @return original estimation [in minutes] for this issue or <code>null</code> when time spent information is not available
     */
    @Nullable
    public Integer getOriginalEstimateMinutes() {
        return originalEstimateMinutes;
    }

    /**
     * @return original remaining estimated time [in minutes] for this issue or <code>null</code> when such estimation was not provided
     */
    @Nullable
    public Integer getRemainingEstimateMinutes() {
        return remainingEstimateMinutes;
    }

    /**
     * @return time spent [in minutes] on this issue or <code>null</code> when time spent information is not available to the caller
     * (in some strange circumstances)
     */
    @Nullable
    public Integer getTimeSpentMinutes() {
        return timeSpentMinutes;
    }

    @Override
    public String toString() {
        return "TimeTracking [originalEstimateMinutes=" + originalEstimateMinutes + ", remainingEstimateMinutes=" + remainingEstimateMinutes
                + ", timeSpentMinutes=" + timeSpentMinutes + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeTracking) {
            TimeTracking that = (TimeTracking) obj;
            return Objects.equals(this.originalEstimateMinutes, that.originalEstimateMinutes)
                    && Objects.equals(this.timeSpentMinutes, that.timeSpentMinutes)
                    && Objects.equals(this.remainingEstimateMinutes, that.remainingEstimateMinutes);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalEstimateMinutes, remainingEstimateMinutes, timeSpentMinutes);
    }

}
