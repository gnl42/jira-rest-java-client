package me.glindholm.jira.rest.client.api.domain.input;

import static me.glindholm.jira.rest.client.api.domain.input.WorklogInputBuilder.ESTIMATE_UNIT_MINUTES;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.URI;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import me.glindholm.jira.rest.client.TestUtil;
import me.glindholm.jira.rest.client.api.domain.input.WorklogInput.AdjustEstimate;

public class WorklogInputBuilderTest {

    private final static URI dummyUri = TestUtil.toUri("http://jira.atlassian.com/");

    private WorklogInputBuilder builder;

    private static String asMinutes(int value) {
        return value + ESTIMATE_UNIT_MINUTES;
    }

    @Before
    public void beforeMethod() {
        builder = new WorklogInputBuilder(dummyUri);
    }

    private void testAdjustEstimateImpl(WorklogInputBuilder worklogInputBuilder, String expectedEstimateValue,
            AdjustEstimate expectedAdjustEstimate) throws Exception {

        final WorklogInput worklogInput = worklogInputBuilder.build();

        assertThat(worklogInput.getAdjustEstimate(), Matchers.equalTo(expectedAdjustEstimate));
        assertThat(worklogInput.getAdjustEstimateValue(), Matchers.equalTo(expectedEstimateValue));
    }

    @Test
    public void testSetAdjustEstimateNewMinutes() throws Exception {
        testAdjustEstimateImpl(builder.setAdjustEstimateNew(12345), asMinutes(12345), AdjustEstimate.NEW);
    }

    @Test
    public void testSetAdjustEstimateNew() throws Exception {
        testAdjustEstimateImpl(builder.setAdjustEstimateNew("1w 2d 3h 5m"), "1w 2d 3h 5m", AdjustEstimate.NEW);
    }

    @Test
    public void testSetAdjustEstimateLeave() throws Exception {
        testAdjustEstimateImpl(builder.setAdjustEstimateLeave(), null, AdjustEstimate.LEAVE);
    }

    @Test
    public void testSetAdjustEstimateManualMinutes() throws Exception {
        testAdjustEstimateImpl(builder.setAdjustEstimateManual(54321), asMinutes(54321), AdjustEstimate.MANUAL);
    }

    @Test
    public void testSetAdjustEstimateManual() throws Exception {
        testAdjustEstimateImpl(builder.setAdjustEstimateManual("1w 2d 3h 5m"), "1w 2d 3h 5m", AdjustEstimate.MANUAL);
    }

    @Test
    public void testSetAdjustEstimateAuto() throws Exception {
        testAdjustEstimateImpl(builder.setAdjustEstimateAuto(), null, AdjustEstimate.AUTO);
    }
}
