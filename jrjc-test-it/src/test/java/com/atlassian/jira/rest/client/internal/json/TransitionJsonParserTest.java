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

package com.atlassian.jira.rest.client.internal.json;

import com.atlassian.jira.rest.client.api.domain.Transition;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

public class TransitionJsonParserTest {
    @Test
    public void testParse() throws Exception {
        final TransitionJsonParser parser = new TransitionJsonParser();

        final Transition transition = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/transition/valid.json"), 5);
        Assert.assertEquals(4, Lists.size(transition.getFields()));
        Assert.assertEquals(new Transition.Field("assignee", false, "com.opensymphony.user.User"), Lists.getLast(transition
                .getFields()));
        Assert.assertEquals(5, transition.getId());
    }
}
