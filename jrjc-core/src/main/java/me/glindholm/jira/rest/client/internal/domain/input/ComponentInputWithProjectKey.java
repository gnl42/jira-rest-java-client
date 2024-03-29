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

package me.glindholm.jira.rest.client.internal.domain.input;

import org.eclipse.jdt.annotation.Nullable;

import me.glindholm.jira.rest.client.api.domain.AssigneeType;
import me.glindholm.jira.rest.client.api.domain.input.ComponentInput;

/*
 * JIRA REST API awfully treats components as top level resources and require users to additionally provide project key;
 * JRJC tries to hide this ugliness but at least requiring to specify project key separately
 */
public class ComponentInputWithProjectKey extends ComponentInput {
    private final String projectKey;

    public ComponentInputWithProjectKey(@Nullable final String projectKey, final String name, final String description, final String leadUsername,
            final AssigneeType assigneeType) {
        super(name, description, leadUsername, assigneeType);
        this.projectKey = projectKey;
    }

    public ComponentInputWithProjectKey(@Nullable final String projectKey, final ComponentInput componentInput) {
        this(projectKey, componentInput.getName(), componentInput.getDescription(), componentInput.getLeadUsername(), componentInput.getAssigneeType());
    }

    public String getProjectKey() {
        return projectKey;
    }
}
