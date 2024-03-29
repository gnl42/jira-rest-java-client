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

package me.glindholm.jira.rest.client;

import static com.google.common.collect.Iterators.getOnlyElement;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Lists;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.hamcrest.Matchers;
import org.joda.time.OffsetDateTimeZone;
import org.joda.time.format.ISOOffsetDateTimeFormat;
import org.joda.time.format.OffsetDateTimeFormatter;

import com.atlassian.httpclient.api.Response;
import com.google.common.collect.Iterators;

import junit.framework.Assert;
import me.glindholm.jira.rest.client.api.RestClientException;
import me.glindholm.jira.rest.client.api.domain.OperationGroup;
import me.glindholm.jira.rest.client.api.domain.OperationLink;
import me.glindholm.jira.rest.client.api.domain.Transition;
import me.glindholm.jira.rest.client.api.domain.util.ErrorCollection;

public class TestUtil {
    private static OffsetDateTimeFormatter universalOffsetDateTimeParser = ISOOffsetDateTimeFormat.dateTimeParser();
    private static OffsetDateTimeFormatter formatter = ISOOffsetDateTimeFormat.dateTime();
    private static OffsetDateTimeFormatter dateFormatter = ISOOffsetDateTimeFormat.date();
    public static List<OperationGroup> EMPTY_GROUPS = Lists.emptyList();
    public static List<OperationLink> EMPTY_LINKS = Lists.emptyList();

    public static URI toUri(String str) {
        return new URIBuilder(str).build();
    }

    public static OffsetDateTime toOffsetDateTime(String isoOffsetDateTimeSt) {
        return universalOffsetDateTimeParser.parseOffsetDateTime(isoOffsetDateTimeSt);
    }

    @SuppressWarnings("unused")
    public static OffsetDateTime toOffsetDateTime(String isoOffsetDateTimeSt, OffsetDateTimeZone zone) {
        return formatter.withZone(zone).parseOffsetDateTime(isoOffsetDateTimeSt);
    }

    public static OffsetDateTime toOffsetDateTimeFromIsoDate(String isoDate) {
        return dateFormatter.parseOffsetDateTime(isoDate);
    }

    public static void assertErrorCode(int errorCode, Runnable runnable) {
        assertErrorCode(errorCode, StringUtils.EMPTY, runnable);
    }

    @SuppressWarnings("unused")
    public static <T extends Throwable> void assertThrows(Class<T> clazz, String regexp, Runnable runnable) {
        try {
            runnable.run();
            Assert.fail(clazz.getName() + " exception expected");
        } catch (Throwable e) {
            Assert.assertTrue("Expected exception of class " + clazz.getName() + " but was caught " + e.getClass().getName(),
                    clazz.isInstance(e));
            if (e.getMessage() == null && regexp != null) {
                Assert.fail("Exception with no message caught, while expected regexp [" + regexp + "]");
            }
            if (regexp != null && e.getMessage() != null) {
                Assert.assertTrue("Message [" + e.getMessage() + "] does not match regexp [" + regexp + "]", e.getMessage()
                        .matches(regexp));
            }
        }

    }

    public static void assertErrorCode(Response.Status status, String message, Runnable runnable) {
        assertErrorCode(status.getStatusCode(), message, runnable);
    }

    public static void assertExpectedErrorCollection(final List<ErrorList> errors, final Runnable runnable) {
        assertExpectedErrors(errors, runnable);
    }

    public static void assertErrorCodeWithRegexp(Response.Status status, String regexp, Runnable runnable) {
        assertErrorCodeWithRegexp(status.getStatusCode(), regexp, runnable);
    }

    public static void assertErrorCode(Response.Status status, Runnable runnable) {
        assertErrorCode(status.getStatusCode(), null, runnable);
    }

    public static void assertErrorCode(int errorCode, String message, Runnable runnable) {
        try {
            runnable.run();
            Assert.fail(RestClientException.class + " exception expected");
        } catch (me.glindholm.jira.rest.client.api.RestClientException e) {
            Assert.assertTrue(e.getStatusCode().isPresent());
            Assert.assertEquals(errorCode, e.getStatusCode().get().intValue());
            if (!StringUtils.isEmpty(message)) {
                // We expect a single error message. Either error or error message.
                Assert.assertEquals(1, e.getErrorLists().size());
                if (Iterators.getOnlyElement(e.getErrorLists().iterator()).getErrorMessages().size() > 0) {
                    Assert.assertEquals(getOnlyElement(getOnlyElement(e.getErrorLists().iterator()).getErrorMessages()
                            .iterator()), message);
                } else if (Iterators.getOnlyElement(e.getErrorLists().iterator()).getErrors().size() > 0) {
                    Assert.assertEquals(getOnlyElement(getOnlyElement(e.getErrorLists().iterator()).getErrors().values()
                            .iterator()), message);
                } else {
                    Assert.fail("Expected an error message.");
                }
            }
        }
    }

    public static void assertErrorCodeWithRegexp(int errorCode, String regExp, Runnable runnable) {
        try {
            runnable.run();
            Assert.fail(RestClientException.class + " exception expected");
        } catch (me.glindholm.jira.rest.client.api.RestClientException ex) {
            final ErrorCollection errorElement = getOnlyElement(ex.getErrorLists().iterator());
            final String errorMessage = getOnlyElement(errorElement.getErrorMessages().iterator());
            Assert.assertTrue("'" + ex.getMessage() + "' does not match regexp '" + regExp + "'", errorMessage.matches(regExp));
            Assert.assertTrue(ex.getStatusCode().isPresent());
            Assert.assertEquals(errorCode, ex.getStatusCode().get().intValue());
        }
    }


    public static String getLastPathSegment(URI uri) {
        final String path = uri.getPath();
        final int index = path.lastIndexOf('/');
        if (index == -1) {
            return path;
        }
        if (index == path.length()) {
            return "";
        }
        return path.substring(index + 1);
    }

    public static <E> void assertEqualsSymmetrical(E a, E b) {
        Assert.assertEquals(a, b);
        Assert.assertEquals(b, a);
    }

    public static <E> void assertNotEquals(E a, E b) {
        if (a == null) {
            Assert.assertFalse("[" + a + "] not equals [" + b + "]", b.equals(a));
        } else if (b == null) {
            Assert.assertFalse("[" + a + "] not equals [" + b + "]", a.equals(b));
        } else if (a.equals(b) || b.equals(a)) {
            Assert.fail("[" + a + "] not equals [" + b + "]");
        }
    }

    @Nullable
    public static Transition getTransitionByName(List<Transition> transitions, String transitionName) {
        Transition transitionFound = null;
        for (Transition transition : transitions) {
            if (transition.getName().equals(transitionName)) {
                transitionFound = transition;
                break;
            }
        }
        return transitionFound;
    }

    private static void assertExpectedErrors(final List<ErrorList> expectedErrors, final Runnable runnable) {
        try {
            runnable.run();
            Assert.fail(RestClientException.class + " exception expected");
        } catch (me.glindholm.jira.rest.client.api.RestClientException e) {
            Assert.assertEquals(e.getErrorLists(), expectedErrors);
        }
    }

    public static <K> void assertEmptyList(List<K> iterable) {
        org.hamcrest.MatcherAssert.assertThat(iterable, Matchers.<K>emptyList());
    }
}
