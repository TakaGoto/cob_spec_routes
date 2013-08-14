package com.company.responders;

import com.server.Handlers.Responder;
import com.server.HtmlGenerator;
import com.server.Responses.ResponseStatusLine;

import java.nio.charset.Charset;
import java.util.Hashtable;

public class Root implements Responder {
    private String body;
    private Hashtable<String, Object> resp = new Hashtable<String, Object>();

    public Root() {
        generateBody();
    }

    public Hashtable<String, Object> respond(Hashtable<String, Object> req) {
        Hashtable<String, String> messageHeader = new Hashtable<String, String>();

        if(req.containsValue("GET")) {
            resp.put("status-line", ResponseStatusLine.get("200", req.get("HTTP-Version")));
        } else if(req.get("Method").equals("OPTIONS")) {
            resp.put("status-line", ResponseStatusLine.get("200", req.get("HTTP-Version")));
            messageHeader.put("Allow", "HEAD,POST,OPTIONS,PUT,GET");
        } else {
            resp.put("status-line", ResponseStatusLine.get("404", req.get("HTTP-Version")));
        }
        messageHeader.put("Content-Type", "text/html");
        messageHeader.put("Connection", "close");
        resp.put("message-header", messageHeader);
        resp.put("message-body", body.getBytes(Charset.forName("utf-8")));
        return resp;
    }

    private void generateBody() {
        body = HtmlGenerator.generateList();
    }

    public String getBody() {
        return body;
    }
}
