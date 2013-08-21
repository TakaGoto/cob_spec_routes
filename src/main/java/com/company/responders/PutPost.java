package com.company.responders;

import com.server.Handlers.Responder;
import com.server.HtmlGenerator;
import com.server.Responses.ResponseStatusLine;

import java.nio.charset.Charset;
import java.util.Hashtable;

public class PutPost implements Responder {
    private String body;
    private Hashtable<String, Object> resp = new Hashtable<String, Object>();

    public PutPost() {
        generateBody();
    }

    public Hashtable<String, Object> respond(Hashtable<String, Object> req) {
        Hashtable<String, String> messageHeader = new Hashtable<String, String>();

        if(req.containsValue("POST") || req.containsValue("PUT")) {
            if(req.containsKey("Body")) {
                echoBody((Hashtable<String, String>) req.get("Body"));
            }
        }

        resp.put("status-line", ResponseStatusLine.get("200", req.get("HTTP-Version")));
        resp.put("message-body", this.body.getBytes(Charset.forName("utf-8")));
        messageHeader.put("Content-Length", String.valueOf(this.body.length()));
        messageHeader.put("Content-Type", "text/html");
        messageHeader.put("Connection", "close");
        resp.put("message-header", messageHeader);
        return resp;
    }

    private void generateBody() {
        body = HtmlGenerator.getDefaultHTML();
    }

    private void echoBody(Hashtable body) {
        String newBody = "";
        for(Object key: body.keySet()) {
            if(!key.equals("length")) {
                newBody = newBody.concat(key + " = " + body.get(key) + "\r\n");
            }
        }
        newBody = newBody.replaceAll("\\+", " ");
        this.body = newBody;
    }
}
