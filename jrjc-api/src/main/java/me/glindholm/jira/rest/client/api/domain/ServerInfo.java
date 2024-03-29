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

package me.glindholm.jira.rest.client.api.domain;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Objects;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Basic information about JIRA server
 *
 * @since v0.1
 */
public class ServerInfo {
    private final URI baseUri;
    private final String version;
    private final int buildNumber;
    private final OffsetDateTime buildDate;
    @Nullable
    private final OffsetDateTime serverTime;
    private final String scmInfo;
    private final String serverTitle;
    private final String deploymentType;

    public ServerInfo(final URI baseUri, final String version, final int buildNumber, final OffsetDateTime buildDate, @Nullable final OffsetDateTime serverTime,
            final String scmInfo, final String serverTitle, final String deploymentType) {
        this.baseUri = baseUri;
        this.version = version;
        this.buildNumber = buildNumber;
        this.buildDate = buildDate;
        this.serverTime = serverTime;
        this.scmInfo = scmInfo;
        this.serverTitle = serverTitle;
        this.deploymentType = deploymentType;
    }

    /**
     * @return base URI of this JIRA instance
     */
    public URI getBaseUri() {
        return baseUri;
    }

    /**
     * @return version of this JIRA instance (like "4.2.1")
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return build number
     */
    public int getBuildNumber() {
        return buildNumber;
    }

    /**
     * @return date when the version of this JIRA instance has been built
     */
    public OffsetDateTime getBuildDate() {
        return buildDate;
    }

    /**
     * @return current time (when the response is generated) on the server side or
     *         <code>null</code> when the user is not authenticated.
     */
    @Nullable
    public OffsetDateTime getServerTime() {
        return serverTime;
    }

    /**
     * @return SCM information (like SVN revision) indicated from which sources this
     *         JIRA server has been built.
     */
    public String getScmInfo() {
        return scmInfo;
    }

    /**
     * @return name of this JIRA instance (as defined by JIRA admin)
     */
    public String getServerTitle() {
        return serverTitle;
    }

    public String getDeploymentType() {
        return deploymentType;
    }

    @Override
    public String toString() {
        return "ServerInfo [baseUri=" + baseUri + ", version=" + version + ", buildNumber=" + buildNumber + ", scmInfo=" + scmInfo + ", serverTitle="
                + serverTitle + "]";
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof ServerInfo) {
            final ServerInfo that = (ServerInfo) obj;
            return Objects.equals(baseUri, that.baseUri) && Objects.equals(version, that.version) && Objects.equals(buildNumber, that.buildNumber)
                    && Objects.equals(buildDate, that.buildDate) && Objects.equals(serverTime, that.serverTime) && Objects.equals(scmInfo, that.scmInfo)
                    && Objects.equals(serverTitle, that.serverTitle);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseUri, version, buildNumber, buildDate, serverTime, scmInfo, serverTitle);
    }

}
