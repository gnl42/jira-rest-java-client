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

package me.glindholm.jira.rest.client.internal.json;

import me.glindholm.jira.rest.client.api.domain.TimeTracking;
import org.junit.Assert;
import org.junit.Test;

public class TimeTrackingJsonParserTest {
    @Test
    public void testParse() throws Exception {
        final TimeTrackingJsonParser parser = new TimeTrackingJsonParser();
        final TimeTracking timeTracking = parser.parse(ResourceUtil.getJsonObjectFromResource("/json/timeTracking/valid.json")
                .getJSONObject("value"));
        Assert.assertEquals(new TimeTracking(1500, 70, 190), timeTracking);
    }

    @Test
    public void testParseNoEstimation() throws Exception {
        final TimeTrackingJsonParser parser = new TimeTrackingJsonParser();
        final TimeTracking timeTracking = parser.parse(ResourceUtil
                .getJsonObjectFromResource("/json/timeTracking/valid-no-estimation.json").getJSONObject("value"));
        Assert.assertEquals(new TimeTracking(null, 170, 9), timeTracking);
    }

    @Test
    public void testParseJustLoggedTime() throws Exception {
        final TimeTrackingJsonParser parser = new TimeTrackingJsonParser();
        final TimeTracking timeTracking = parser.parse(ResourceUtil
                .getJsonObjectFromResource("/json/timeTracking/valid-just-timespent.json").getJSONObject("value"));
        Assert.assertEquals(new TimeTracking(null, null, 840), timeTracking);
    }

}
