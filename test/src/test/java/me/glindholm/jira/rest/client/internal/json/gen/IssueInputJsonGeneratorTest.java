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

package me.glindholm.jira.rest.client.internal.json.gen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import m2.glindholm.jira.rest.client.test.matchers.JSONObjectMatcher;
import me.glindholm.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import me.glindholm.jira.rest.client.api.domain.input.FieldInput;
import me.glindholm.jira.rest.client.api.domain.input.IssueInput;
import me.glindholm.jira.rest.client.api.domain.input.PropertyInput;
import me.glindholm.jira.rest.client.internal.json.ResourceUtil;
import me.glindholm.jira.rest.client.internal.json.gen.IssueInputJsonGenerator;

import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @since v1.0
 */
public class IssueInputJsonGeneratorTest {

    @Test
    public void testGenerate() throws Exception {
        final IssueInputJsonGenerator generator = new IssueInputJsonGenerator();
        final IssueInput issueInput = IssueInput.createWithFields(
                new FieldInput("string", "String value"),
                new FieldInput("integer", 1),
                new FieldInput("long", 1L),
                new FieldInput("complex", new ComplexIssueInputFieldValue(ImmutableMap.<String, Object>of(
                        "string", "string",
                        "integer", 1,
                        "long", 1L,
                        "complex", ComplexIssueInputFieldValue.with("test", "id")
                )))
        );

        issueInput.getProperties().add(new PropertyInput("testKey", "{\"testValue\" : \"foo\"}"));

        final JSONObject expected = ResourceUtil.getJsonObjectFromResource("/json/issueInput/valid.json");
        final JSONObject actual = generator.generate(issueInput);
        Assert.assertThat(expected, JSONObjectMatcher.isEqual(actual));
    }

    @Test
    public void testGenerateWithEmptyInput() throws Exception {
        final IssueInputJsonGenerator generator = new IssueInputJsonGenerator();
        final IssueInput issueInput = new IssueInput(Maps.<String, FieldInput>newHashMap(), new ArrayList<PropertyInput>());

        final JSONObject expected = ResourceUtil.getJsonObjectFromResource("/json/issueInput/empty.json");
        final JSONObject actual = generator.generate(issueInput);
        Assert.assertThat(expected, JSONObjectMatcher.isEqual(actual));
    }

    @Test
    public void testGenerateWithNullInput() throws Exception {
        final IssueInputJsonGenerator generator = new IssueInputJsonGenerator();
        final IssueInput issueInput = null;

        final JSONObject expected = ResourceUtil.getJsonObjectFromResource("/json/issueInput/empty.json");
        final JSONObject actual = generator.generate(issueInput);
        Assert.assertThat(expected, JSONObjectMatcher.isEqual(actual));
    }
}
