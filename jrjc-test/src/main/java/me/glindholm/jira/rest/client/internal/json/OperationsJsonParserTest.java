/*
 * Copyright (C) 2014 Atlassian
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

package me.glindholm.jira.rest.client.internal.json;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import me.glindholm.jira.rest.client.api.domain.OperationGroup;
import me.glindholm.jira.rest.client.api.domain.OperationHeader;
import me.glindholm.jira.rest.client.api.domain.OperationLink;
import me.glindholm.jira.rest.client.api.domain.Operations;

public class OperationsJsonParserTest {
    @Test
    public void testParse() throws Exception {
        OperationsJsonParser parser = new OperationsJsonParser();
        Operations actual = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/operations/valid.json"));
        assertThat(actual, is(new Operations(List.of(new OperationGroup(
                "opsbar-transitions",
                List.of(new OperationLink("action_id_4", "issueaction-workflow-transition",
                        "Start Progress", "Start work on the issue", "/secure/WorkflowUIDispatcher.jspa?id=93813&action=4&atl_token=",
                        10, null)),
                List.of(new OperationGroup(
                        null,
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new OperationHeader("opsbar-transitions_more", "Workflow", null, null),
                        null)),
                null,
                20
                )))));
    }
}
