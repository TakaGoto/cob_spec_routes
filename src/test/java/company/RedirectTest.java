package company;

import com.company.responders.Redirect;
import org.junit.Test;

import java.util.Hashtable;

import static junit.framework.Assert.assertEquals;

public class RedirectTest {

    @Test public void testRedirect() {
        Redirect redirect = new Redirect();
        Hashtable<String, Object> req = new Hashtable<String, Object>();
        req.put("Host", "http://localhost:5000/");
        req.put("HTTP-Version", "HTTP/1.0");
        Hashtable<String, Object> response = redirect.respond(req);
        Hashtable<String, String> header = (Hashtable<String, String>) response.get("message-header");
        assertEquals("http://localhost:5000/", header.get("Location"));
        assertEquals("HTTP/1.0 301 Moved Permanently", response.get("status-line"));
    }
}
