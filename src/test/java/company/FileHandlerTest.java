package company;

import com.company.responders.FileHandler;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

import static junit.framework.Assert.assertEquals;

public class FileHandlerTest {
    FileHandler file = new FileHandler("/Users/takayuki/Coding/java/cob_spec/public");
    Hashtable<String, Object> req = new Hashtable<String, Object>();
    Hashtable<String, Object> resp = new Hashtable<String, Object>();

    @Test public void testWrongMethod() throws IOException {
        req.put("Request-URI", "/file1");
        req.put("HTTP-Version", "HTTP/1.0");
        req.put("Method", "PUT");
        resp = file.respond(req);
        Hashtable<String, String> header = (Hashtable<String, String>) resp.get("message-header");
        assertEquals("HTTP/1.0 405 Method Not Allowed", resp.get("status-line"));
        assertEquals("GET", header.get("Allow"));
    }

    @Test public void getOKForGetRequest() throws IOException {
        req.put("Request-URI", "/file1");
        req.put("HTTP-Version", "HTTP/1.0");
        req.put("Method", "GET");
        resp = file.respond(req);
        assertEquals("HTTP/1.0 200 OK", resp.get("status-line"));
    }

    @Test public void testPartialContent() throws IOException {
        req.put("Request-URI", "/partial_content.txt");
        req.put("HTTP-Version", "HTTP/1.0");
        req.put("Method", "GET");
        req.put("Range", "bytes=0-10");
        resp = file.respond(req);
        File newFile = new File(file.getRootDir() + "/partial_content.txt");
        assertEquals("HTTP/1.0 206 Partial Content", resp.get("status-line"));
        assertEquals(getFile(newFile).getClass(), resp.get("message-body").getClass());
        assertEquals(true, Arrays.equals(getFile(newFile), (byte[]) resp.get("message-body")));
    }

    @Test public void testFourOhFour() throws IOException {
        req.put("Request-URI", "/nowAvailable.txt");
        req.put("HTTP-Version", "HTTP/1.0");
        req.put("Method", "GET");
        req.put("Range", "bytes=0-4");
        resp = file.respond(req);
        assertEquals("HTTP/1.0 404 Not Found", resp.get("status-line"));
    }

    @Test public void testPartialContentFieldRange() throws IOException {
        req.put("Request-URI", "/partial_content.txt");
        req.put("HTTP-Version", "HTTP/1.0");
        req.put("Method", "GET");
        req.put("Range", "bytes=0-4");
        resp = file.respond(req);
        Hashtable<String, String> header = (Hashtable<String, String>) resp.get("message-header");
        assertEquals("bytes 0-4", header.get("Content-Range"));
        assertEquals("4", header.get("Content-Length"));
    }

    @Test public void testJPEG() throws IOException {
        req.put("Request-URI", "/image.jpeg");
        req.put("HTTP-Version", "HTTP/1.0");
        req.put("Method", "GET");
        resp = file.respond(req);
        File newFile = new File(file.getRootDir() + "/image.jpeg");
        assertEquals(true, Arrays.equals(getFile(newFile), (byte[]) resp.get("message-body")));
    }

    @Test public void testPNG() throws IOException {
        req.put("Request-URI", "/image.png");
        req.put("HTTP-Version", "HTTP/1.0");
        req.put("Method", "GET");
        resp = file.respond(req);
        File newFile = new File(file.getRootDir() + "/image.png");
        assertEquals(true, Arrays.equals(getFile(newFile), (byte[]) resp.get("message-body")));
    }

    @Test public void testGIF() throws IOException {
        req.put("Request-URI", "/image.gif");
        req.put("HTTP-Version", "HTTP/1.0");
        req.put("Method", "GET");
        resp = file.respond(req);
        File newFile = new File(file.getRootDir() + "/image.gif");
        assertEquals(true, Arrays.equals(getFile(newFile), (byte[]) resp.get("message-body")));
    }

    @Test public void findImageType() {
        assertEquals("image/jpeg", file.findImageType("image.jpeg"));
    }

    @Test public void findPng() {
        assertEquals("image/png", file.findImageType("image.png"));
    }

    @Test public void findGif() {
        assertEquals("image/gif", file.findImageType("image.gif"));
    }

    @Test public void addImageTypes() {
        file.addFileType("jpg", "image/jpg");
        assertEquals("image/jpg", file.findImageType("image.jpg"));
    }

    public byte[] getFile(File file) throws IOException {
        byte[] newBody = new byte[(int) file.length()];
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        in.read(newBody);
        in.close();
        return newBody;
    }
}
