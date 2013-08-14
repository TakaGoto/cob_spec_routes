package com.company.responders;

import com.server.Handlers.Responder;
import com.server.HtmlGenerator;
import com.server.Responses.ResponseStatusLine;

import java.nio.charset.Charset;
import java.util.Hashtable;

public class BasicAuth implements Responder {
    private Hashtable resp;
    private String body = "";
    private Hashtable messageHeader = new Hashtable();

    public Hashtable<String, Object> respond(Hashtable<String, Object> req) {
        resp = new Hashtable<String, Object>();
        if(!req.containsKey("Authorization")) {
            addLogToBody(req);
            resp.put("status-line", ResponseStatusLine.get("401", req.get("HTTP-Version")));
        } else {
            resp.put("status-line", ResponseStatusLine.get("200", req.get("HTTP-Version")));
        }

        resp.put("message-header", messageHeader);
        resp.put("message-body", HtmlGenerator.echoBody(body).getBytes(Charset.forName("utf-8")));
        return resp;
    }

    private void addLogToBody(Hashtable req) {
        if(!req.get("Request-URI").equals("/logs")) {
            String log = req.get("Method") + " " + req.get("Request-URI") + " " + req.get("HTTP-Version") + "</br>";
            body = body + log;
        } else {
            body = "Authentication required";
        }
    }
}
