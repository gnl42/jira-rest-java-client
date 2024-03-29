/*
 * Copyright (C) 2010 Atlassian
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

package samples;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jettison.json.JSONException;

import me.glindholm.jira.rest.client.api.JiraRestClient;
import me.glindholm.jira.rest.client.api.domain.BasicIssue;
import me.glindholm.jira.rest.client.api.domain.BasicProject;
import me.glindholm.jira.rest.client.api.domain.Comment;
import me.glindholm.jira.rest.client.api.domain.Issue;
import me.glindholm.jira.rest.client.api.domain.SearchResult;
import me.glindholm.jira.rest.client.api.domain.Transition;
import me.glindholm.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import me.glindholm.jira.rest.client.api.domain.input.FieldInput;
import me.glindholm.jira.rest.client.api.domain.input.TransitionInput;
import me.glindholm.jira.rest.client.internal.ServerVersionConstants;
import me.glindholm.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

/**
 * A sample code how to use JRJC library
 *
 * @since v0.1
 */
public class Example1 {

    private static URI jiraServerUri = URI.create("http://localhost:2990/jira");
    private static boolean quiet = false;

    public static void main(final String[] args) throws URISyntaxException, JSONException, IOException {
        parseArgs(args);

        final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, "admin", "admin");
        try (restClient) {
            final int buildNumber = restClient.getMetadataClient().getServerInfo().claim().getBuildNumber();

            // first let's get and print all visible projects (only jira4.3+)
            if (buildNumber >= ServerVersionConstants.BN_JIRA_4_3) {
                final List<BasicProject> allProjects = restClient.getProjectClient().getAllProjects().claim();
                for (final BasicProject project : allProjects) {
                    println(project);
                }
            }

            // let's now print all issues matching a JQL string (here: all assigned issues)
            if (buildNumber >= ServerVersionConstants.BN_JIRA_4_3) {
                final SearchResult searchResult = restClient.getSearchClient().searchJql("assignee is not EMPTY").claim();
                for (final BasicIssue issue : searchResult.getIssues()) {
                    println(issue.getKey());
                }
            }

            final Issue issue = restClient.getIssueClient().getIssue("TST-7").claim();

            println(issue);

            // now let's vote for it
            restClient.getIssueClient().vote(issue.getVotesUri()).claim();

//            // now let's watch it
//            final BasicWatchers watchers = issue.getWatchers();
//            if (watchers != null) {
//                restClient.getIssueClient().watch(watchers.getSelf()).claim();
//            }

            // now let's start progress on this issue
            final List<Transition> transitions = restClient.getIssueClient().getTransitions(issue.getTransitionsUri()).claim();
            final Transition startProgressTransition = getTransitionByName(transitions, "Start Progress");
            restClient.getIssueClient().transition(issue.getTransitionsUri(), new TransitionInput(startProgressTransition.getId())).claim();

            // and now let's resolve it as Incomplete
            final Transition resolveIssueTransition = getTransitionByName(transitions, "Resolve Issue");
            final List<FieldInput> fieldInputs;

            // Starting from JIRA 5, fields are handled in different way -
            if (buildNumber > ServerVersionConstants.BN_JIRA_5) {
                fieldInputs = Arrays.asList(new FieldInput("resolution", ComplexIssueInputFieldValue.with("name", "Incomplete")));
            } else {
                fieldInputs = Arrays.asList(new FieldInput("resolution", "Incomplete"));
            }
            final TransitionInput transitionInput = new TransitionInput(resolveIssueTransition.getId(), fieldInputs, Comment.valueOf("My comment"));
            restClient.getIssueClient().transition(issue.getTransitionsUri(), transitionInput).claim();
        }
    }

    private static void println(final Object o) {
        if (!quiet) {
            System.out.println(o);
        }
    }

    private static void parseArgs(final String[] argsArray) throws URISyntaxException {
        final List<String> args = Arrays.asList(argsArray);
        if (args.contains("-q")) {
            quiet = true;
            args.remove(args.indexOf("-q"));
        }

        if (!args.isEmpty()) {
            jiraServerUri = new URI(args.get(0));
        }
    }

    private static Transition getTransitionByName(final List<Transition> transitions, final String transitionName) {
        for (final Transition transition : transitions) {
            if (transition.getName().equals(transitionName)) {
                return transition;
            }
        }
        return null;
    }

}
