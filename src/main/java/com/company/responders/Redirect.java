package com.company.responders;

import com.server.Handlers.Responder;
import com.server.HtmlGenerator;
import com.server.Responses.ResponseStatusLine;

import java.nio.charset.Charset;
import java.util.Hashtable;

public class Redirect implements Responder {
    private String body;
    private Hashtable<String, Object> resp = new Hashtable<String, Object>();

    public Redirect() {
        generateBody();
    }

    public Hashtable<String, Object> respond(Hashtable<String, Object> req) {
        Hashtable<String, String> messageHeader = new Hashtable<String, String>();
        resp.put("status-line", ResponseStatusLine.get("301", req.get("HTTP-Version")));
        messageHeader.put("Location", "http://localhost:5000/");
        messageHeader.put("Content-Type", "text/html");
        messageHeader.put("Connection", "close");
        messageHeader.put("Content-Length", String.valueOf(body.length()));
        resp.put("message-header", messageHeader);
        resp.put("message-body", body.getBytes(Charset.forName("utf-8")));
        return resp;
    }

    private void generateBody() {
        body = HtmlGenerator.getDefaultHTML();
    }
}
