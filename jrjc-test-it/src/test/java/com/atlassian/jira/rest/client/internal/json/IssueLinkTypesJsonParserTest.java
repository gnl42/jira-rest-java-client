package com.atlassian.jira.rest.client.internal.json;

import com.atlassian.jira.rest.client.TestUtil;
import com.atlassian.jira.rest.client.api.domain.IssuelinksType;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

public class IssueLinkTypesJsonParserTest {
    @Test
    public void testParse() throws Exception {
        IssueLinkTypesJsonParser parser = new IssueLinkTypesJsonParser();
        final List<IssuelinksType> issueLinks = parser.parse(ResourceUtil
                .getJsonObjectFromResource("/json/issueLinks/issue-links-5.0.json"));
        Assert.assertEquals(8, Lists.size(issueLinks));
        Assert.assertEquals(new IssuelinksType(TestUtil.toUri("https://jdog.atlassian.com/rest/api/2/issueLinkType/10160"),
                "10160", "Bonfire Testing", "discovered while testing", "testing discovered"), Lists.get(issueLinks, 0));

        Assert.assertEquals(new IssuelinksType(TestUtil.toUri("https://jdog.atlassian.com/rest/api/2/issueLinkType/10020"),
                "10020", "Relates", "is related to", "relates to"), Lists.getLast(issueLinks));
    }
}
