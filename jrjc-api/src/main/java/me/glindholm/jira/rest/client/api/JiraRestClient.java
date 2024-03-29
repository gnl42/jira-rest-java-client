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

import java.io.Closeable;
import java.io.IOException;

/**
 * Main access point to REST me.glindholm.jira.rest.client.api.
 * As there are many types resources exposed by JIRA REST API, various resources are grouped into clusters
 * and then handled by different specialized *RestClient classes.
 *
 * @since v0.1
 */
public interface JiraRestClient extends Closeable {
    /**
     * @return me.glindholm.jira.rest.client.api for performing operations on selected issue
     */
    IssueRestClient getIssueClient();

    /**
     * @return the me.glindholm.jira.rest.client.api handling session information
     */
    SessionRestClient getSessionClient();

    /**
     * @return the me.glindholm.jira.rest.client.api handling full user information
     */
    UserRestClient getUserClient();

    /**
     * @return the me.glindholm.jira.rest.client.api handling group information
     */
    GroupRestClient getGroupClient();

    /**
     * @return the me.glindholm.jira.rest.client.api handling project metadata
     */
    ProjectRestClient getProjectClient();

    /**
     * @return the me.glindholm.jira.rest.client.api handling components
     */
    ComponentRestClient getComponentClient();

    /**
     * @return the me.glindholm.jira.rest.client.api handling basic meta-data (data dictionaries defined in JIRA - like resolutions, statuses,
     * priorities)
     */
    MetadataRestClient getMetadataClient();

    /**
     * @return the me.glindholm.jira.rest.client.api handling search (e.g. JQL)
     */
    SearchRestClient getSearchClient();

    /**
     * @return the me.glindholm.jira.rest.client.api handling project versions.
     */
    VersionRestClient getVersionRestClient();

    /**
     * @return the me.glindholm.jira.rest.client.api for project roles.
     */
    ProjectRolesRestClient getProjectRolesRestClient();

    /**
     * @return the me.glindholm.jira.rest.client.api for auditing records
     */
    AuditRestClient getAuditRestClient();

    /**
     * @return the me.glindholm.jira.rest.client.api for my permissions.
     */
    MyPermissionsRestClient getMyPermissionsRestClient();

    /**
     * Destroys this instance of JIRA Rest Client.
     *
     * @throws IOException if there is a problem closing this client.
     */
    void close() throws IOException;
}
