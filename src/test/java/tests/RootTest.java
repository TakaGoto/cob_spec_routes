package tests;

import com.company.responders.Root;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class RootTest {
    Root root;
    Hashtable<String, Object> req;

    @Before public void init() {
        root = new Root();
        req = new Hashtable<String, Object>();
    }

    @Test public void testRoot() {
        req.put("Request-URI", "/");
        req.put("Method", "GET");
        req.put("HTTP-Version", "HTTP/1.0");
        req.put("Host", "http://localhost:5000");
        Hashtable<String, Object> response = root.respond(req);
        assertEquals("HTTP/1.0 200 OK", response.get("status-line"));
    }

    @Test public void FourOhFourForAnyOtherMethods() {
        req.put("Request-URI", "/");
        req.put("Method", "PUT");
        req.put("HTTP-Version", "HTTP/1.0");
        req.put("Host", "http://localhost:5000");
        Hashtable<String, Object> response = root.respond(req);
        assertEquals("HTTP/1.0 404 Not Found", response.get("status-line"));
    }

    @Test public void rootBodyContainsFile1() {
        assertTrue(root.getBody().contains("file1"));
    }

    @Test public void rootBodyContainsFile2() {
        assertTrue(root.getBody().contains("file2"));
    }

    @Test public void rootBodyContainsImageGif() {
        assertTrue(root.getBody().contains("image.gif"));
    }

    @Test public void rootBodyContainsImageJpeg() {
        assertTrue(root.getBody().contains("image.jpeg"));
    }

    @Test public void rootBodyContainsImagePng() {
        assertTrue(root.getBody().contains("image.png"));
    }

    @Test public void rootBodyContainsTextFile() {
        assertTrue(root.getBody().contains("text-file.txt"));
    }

    @Test public void optionsReturnsAllow() {
        req.put("Request-URI", "/method_options");
        req.put("Method", "OPTIONS");
        req.put("HTTP-Version", "HTTP/1.0");
        req.put("Host", "http://localhost:5000");
        Hashtable<String, Object> response = root.respond(req);
        Hashtable header = (Hashtable) response.get("message-header");
        String allow = (String) header.get("Allow");
        Assert.assertTrue(allow.contains("HEAD,POST,OPTIONS,PUT,GET"));
    }
}
