package tests;

import com.company.responders.BasicAuth;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import static junit.framework.Assert.assertEquals;

public class BasicAuthTest {
    String authentication;
    String encoded;
    BasicAuth auth = new BasicAuth();
    Hashtable<String, Object> req = new Hashtable<String, Object>();
    Hashtable<String, Object> resp = new Hashtable<String, Object>();

    @Before public void init() {
        authentication = "admin:hunter2";
        encoded = Base64.encode(authentication.getBytes());
    }

    @Test public void returnsFourOhOne() throws IOException {
        resp = auth.respond(createRequest("/logs", "GET", "HTTP/1.0"));
        String body = new String((byte[]) resp.get("message-body"), "UTF-8");
        assertEquals("HTTP/1.0 401 Unauthorized", resp.get("status-line"));
        Assert.assertTrue(body.contains("Authentication required"));
    }

    @Test public void returnsTwoHundredWithRightAuth() {
        req = createRequest("/logs", "GET", "HTTP/1.0");
        req.put("Authorization", encoded);
        resp = auth.respond(req);
        assertEquals("HTTP/1.0 200 OK", resp.get("status-line"));
    }

    @Test public void logsMultiplesRequests() throws UnsupportedEncodingException {
        Hashtable<String, Object> finalReq;
        auth.respond(createRequest("/log", "GET", "HTTP/1.0"));
        auth.respond(createRequest("/requests", "HEAD", "HTTP/1.0"));
        finalReq = createRequest("/logs", "GET", "HTTP/1.0");
        finalReq.put("Authorization", encoded);
        resp = auth.respond(finalReq);
        String body = new String((byte[]) resp.get("message-body"), "UTF-8");
        Assert.assertTrue(body.contains("GET /log HTTP/1.0"));
        Assert.assertTrue(body.contains("HEAD /requests HTTP/1.0"));
    }

    private Hashtable<String, Object> createRequest(String URI, String m, String http) {
        Hashtable<String, Object> req = new Hashtable<String, Object>();
        req.put("Request-URI", URI);
        req.put("Method", m);
        req.put("HTTP-Version", http);
        return req;
    }
}
