/*
 * Copyright (C) 2010-2012 Atlassian
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

package com.atlassian.jira.rest.client.internal.async;

import com.atlassian.jira.rest.client.api.domain.util.ErrorCollection;
import com.atlassian.jira.rest.client.internal.json.ResourceUtil;
import com.google.common.collect.Iterators;
import org.codehaus.jettison.json.JSONException;
import org.hamcrest.collection.IsListContainingInAnyOrder;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AbstractAsynchronousRestClientTest {

    private static final int BAD_REQUEST = 400;

    @Test
    public void testExtractErrors() throws JSONException {
        final String str = ResourceUtil.getStringFromResource("/json/error/valid.json");
        final List<ErrorList> errors = AbstractAsynchronousRestClient.extractErrors(BAD_REQUEST, str);
        final ErrorCollection errorList = Iterators.getOnlyElement(errors.iterator());
        Assert.assertThat(errorList.getErrors().values(), IsListContainingInAnyOrder.containsInAnyOrder("abcfsd"));
    }

    @Test
    public void testExtractErrors2() throws JSONException {
        final String str = ResourceUtil.getStringFromResource("/json/error/valid2.json");
        final List<ErrorList> errors = AbstractAsynchronousRestClient.extractErrors(BAD_REQUEST, str);
        final ErrorCollection errorList = Iterators.getOnlyElement(errors.iterator());
        Assert.assertThat(errorList.getErrorMessages(), IsListContainingInAnyOrder.containsInAnyOrder("a", "b", "xxx"));
    }

    @Test
    public void testExtractErrors3() throws JSONException {
        final String str = ResourceUtil.getStringFromResource("/json/error/valid3.json");
        final List<ErrorList> errors = AbstractAsynchronousRestClient.extractErrors(BAD_REQUEST, str);
        final ErrorCollection errorList = Iterators.getOnlyElement(errors.iterator());
        Assert.assertThat(errorList.getErrors().values(), IsListContainingInAnyOrder.containsInAnyOrder("aa", "bb"));
    }

    @Test
    public void testExtractErrors4() throws JSONException {
        final String str = ResourceUtil.getStringFromResource("/json/error/valid4.json");
        final List<ErrorList> errors = AbstractAsynchronousRestClient.extractErrors(BAD_REQUEST, str);
        final ErrorCollection errorList = Iterators.getOnlyElement(errors.iterator());

        Assert.assertThat(errorList.getErrorMessages(), IsListContainingInAnyOrder.containsInAnyOrder("a", "b"));
        Assert.assertEquals(errorList.getErrors().get("a"), "y");
        Assert.assertEquals(errorList.getErrors().get("c"), "z");
    }

}
