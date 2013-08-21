package company;

import com.company.responders.PutPost;
import org.junit.Test;

import java.io.IOException;
import java.util.Hashtable;

import static junit.framework.Assert.assertEquals;

public class PutPostTest {
    PutPost putPost;
    String htmlBody = "data = cosby\r\n";
    Hashtable<String, Object> req = new Hashtable<String, Object>();
    Hashtable<String, Object> resp = new Hashtable<String, Object>();

    @Test public void testGetPutGetPost() throws IOException {
        Hashtable<String, String> body = new Hashtable<String, String>();
        body.put("data", "cosby");
        body.put("length", "5");
        putPost = new PutPost();
        req.put("Request-URI", "/form");
        req.put("Method", "POST");
        req.put("Body", body);
        resp = putPost.respond(req);
        String newBody = new String((byte[]) resp.get("message-body"), "UTF-8");
        assertEquals(htmlBody, newBody);
    }
}
