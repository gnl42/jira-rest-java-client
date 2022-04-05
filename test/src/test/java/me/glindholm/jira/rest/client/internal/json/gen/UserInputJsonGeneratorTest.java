/*
 * Copyright (C) 2018 Atlassian
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

package me.glindholm.jira.rest.client.internal.json.gen;

import com.atlassian.jira.rest.client.test.matchers.JSONObjectMatcher;

import me.glindholm.jira.rest.client.api.domain.input.UserInput;
import me.glindholm.jira.rest.client.internal.json.ResourceUtil;
import me.glindholm.jira.rest.client.internal.json.gen.UserInputJsonGenerator;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertThat;

/**
 * @since v5.1.0
 */
public class UserInputJsonGeneratorTest {

    private final UserInputJsonGenerator generator = new UserInputJsonGenerator();

    @Test
    public void testGenerate() throws JSONException {
        final UserInput userInput = new UserInput(
                "admin",
                "admin",
                null,
                "admin@atlassian.com",
                "Administrator",
                null,
                Collections.singleton("jira-core")
        );

        final JSONObject actual = generator.generate(userInput);
        final JSONObject expected = ResourceUtil.getJsonObjectFromResource("/json/userInput/valid.json");
        assertThat(actual, JSONObjectMatcher.isEqual(expected));
    }
}
