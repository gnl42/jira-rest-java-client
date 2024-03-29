package me.glindholm.jira.rest.client.internal.json;

import me.glindholm.jira.rest.client.api.domain.BasicUser;
import org.junit.Assert;
import org.junit.Test;

import static me.glindholm.jira.rest.client.TestUtil.toUri;
import static me.glindholm.jira.rest.client.internal.json.ResourceUtil.getJsonObjectFromResource;

public class BasicUserJsonParserTest {

    private final BasicUserJsonParser parser = new BasicUserJsonParser();

    @Test
    public void testParseWhenAnonymousUser() throws Exception {
        final BasicUser user = parser.parse(getJsonObjectFromResource("/json/user/valid-basic-anonymous.json"));

        Assert.assertNull(user);
    }

    @Test
    public void testParseWhenDeletedUserBugJRA30263() throws Exception {
        final BasicUser user = parser.parse(getJsonObjectFromResource("/json/user/valid-basic-deleted-JRA-30263.json"));

        Assert.assertEquals("mark", user.getName());
        Assert.assertTrue(user.isSelfUriIncomplete());
    }


    @Test
    public void testParseWhenValid() throws Exception {
        final BasicUser user = parser.parse(getJsonObjectFromResource("/json/user/valid.json"));

        Assert.assertNotNull(user);
        Assert.assertEquals("admin", user.getName());
        Assert.assertEquals("Administrator", user.getDisplayName());
        Assert.assertNull(user.getAccountId());
        Assert.assertEquals(toUri("http://localhost:8090/jira/rest/api/latest/user?username=admin"), user.getSelf());
        Assert.assertFalse(user.isSelfUriIncomplete());
    }

    @Test
    public void testParseWhenValidWithAccountId() throws Exception {
        final BasicUser user = parser.parse(getJsonObjectFromResource("/json/user/valid-with-accountId.json"));

        Assert.assertNotNull(user);
        Assert.assertEquals("admin", user.getName());
        Assert.assertEquals("Administrator", user.getDisplayName());
        Assert.assertEquals("uuid-accountId-admin", user.getAccountId());
        Assert.assertEquals(toUri("http://localhost:8090/jira/rest/api/latest/user?username=admin"), user.getSelf());
        Assert.assertFalse(user.isSelfUriIncomplete());
    }

    @Test
    public void testParseWhenValidWithAccountIdWithoutName() throws Exception {
        final BasicUser user = parser.parse(getJsonObjectFromResource("/json/user/valid-with-accountId-without-name.json"));

        Assert.assertNotNull(user);
        Assert.assertNull(user.getName());
        Assert.assertEquals("Administrator", user.getDisplayName());
        Assert.assertEquals("uuid-accountId-admin", user.getAccountId());
        Assert.assertEquals(toUri("http://localhost:8090/jira/rest/api/latest/user?username=admin"), user.getSelf());
        Assert.assertFalse(user.isSelfUriIncomplete());
    }
}
