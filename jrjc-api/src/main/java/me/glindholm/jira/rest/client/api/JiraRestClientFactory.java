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

package me.glindholm.jira.rest.client.api;

import java.net.URI;
import java.net.URISyntaxException;

import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.httpclient.api.factory.HttpClientOptions;

/**
 * Factory for producing JIRA REST com.atlassian.jira.client with selected authentication handler
 *
 * @since v0.1
 */
public interface JiraRestClientFactory {

    /**
     * Creates an instance of JiraRestClient with default HttpClient settings.
     *
     * @param serverUri             - URI of JIRA instance.
     * @param authenticationHandler - requests authenticator.
     * @throws URISyntaxException
     */
    JiraRestClient create(final URI serverUri, final AuthenticationHandler authenticationHandler) throws URISyntaxException;

    /**
     * Creates an instance of JiraRestClient with default HttpClient settings. HttpClient will conduct a
     * basic authentication for given credentials.
     *
     * @param serverUri - URI or JIRA instance.
     * @param username  - username of the user used to log in to JIRA.
     * @param password  - password of the user used to log in to JIRA.
     */
    JiraRestClient createWithBasicHttpAuthentication(final URI serverUri, final String username, final String password) throws URISyntaxException;

    /**
     * Creates an instance of JiraRestClient with default HttpClient settings. HttpClient will call the provided
     * authentication handler prior to making requests.
     *
     * @param serverUri             - URI or JIRA instance.
     * @param authenticationHandler - Authentication handler.
     */
    JiraRestClient createWithAuthenticationHandler(final URI serverUri, final AuthenticationHandler authenticationHandler) throws URISyntaxException;

    /**
     * Creates an instance of JiraRestClient with given Atlassian HttpClient.
     * Please note, that this me.glindholm.jira.rest.client.api has to be fully configured to do the request authentication.
     *
     * @param serverUri  - URI of JIRA instance.
     * @param httpClient - instance of Atlassian HttpClient.
     */
    JiraRestClient create(final URI serverUri, final HttpClient httpClient) throws URISyntaxException;

    /**
     * Creates an instance of JiraRestClient with Bearer HttpClient settings.
     * HttpClient will conduct a Bearer authentication for given token.
     *
     * @param serverUri
     * @param password
     * @return
     * @throws URISyntaxException
     */
    JiraRestClient createWithBearerHttpAuthentication(URI serverUri, String token) throws URISyntaxException;

    JiraRestClient createWithBearerHttpAuthentication(URI uri, String token, HttpClientOptions httpOptions) throws URISyntaxException;

    JiraRestClient createWithBasicHttpAuthentication(URI uri, String username, String password, HttpClientOptions httpOptions) throws URISyntaxException;

    JiraRestClient create(URI uri, AuthenticationHandler authenticationHandler, HttpClientOptions httpOptions) throws URISyntaxException;
}
