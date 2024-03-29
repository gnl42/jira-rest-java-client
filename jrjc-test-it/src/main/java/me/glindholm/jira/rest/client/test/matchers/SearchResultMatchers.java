/*
 * Copyright (C) 2012 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.glindholm.jira.rest.client.test.matchers;

import java.util.List;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import com.google.common.collect.Lists;

import me.glindholm.jira.rest.client.api.domain.Issue;
import me.glindholm.jira.rest.client.api.domain.SearchResult;

public class SearchResultMatchers {
    public static Matcher<? super SearchResult> withStartIndex(final int startIndex) {
        return new FeatureMatcher<>(Matchers.is(startIndex),
                "search result with start index that", "startIndex") {

            @Override
            protected Integer featureValueOf(SearchResult searchResult) {
                return searchResult.getStartIndex();
            }
        };
    }

    public static Matcher<? super SearchResult> withMaxResults(final int maxResults) {
        return new FeatureMatcher<>(Matchers.is(maxResults),
                "search result with max results that", "maxResults") {

            @Override
            protected Integer featureValueOf(SearchResult searchResult) {
                return searchResult.getMaxResults();
            }
        };
    }

    public static Matcher<? super SearchResult> withTotal(final int total) {
        return new FeatureMatcher<>(Matchers.is(total),
                "search result with total that", "total") {

            @Override
            protected Integer featureValueOf(SearchResult searchResult) {
                return searchResult.getTotal();
            }
        };
    }

    public static Matcher<? super SearchResult> withIssueCount(final int issueCount) {
        return new FeatureMatcher<>(Matchers.is(issueCount),
                "search result with issue count that", "issue count") {

            @Override
            protected Integer featureValueOf(SearchResult searchResult) {
                final List<Issue> issues = searchResult.getIssues();
                return issues == null ? 0 : Lists.size(issues);
            }
        };
    }

    public static Matcher<? super SearchResult> searchResultWithParamsAndIssueCount(final int startIndex, final int maxResults,
            final int total, final int issueCount) {
        return Matchers.allOf(withStartIndex(startIndex), withMaxResults(maxResults), withTotal(total),
                withIssueCount(issueCount));
    }
}
