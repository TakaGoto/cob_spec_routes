package com.company.responders;

import com.server.Handlers.Responder;
import com.server.HtmlGenerator;
import com.server.Responses.ResponseStatusLine;

import java.nio.charset.Charset;
import java.util.Hashtable;

public class ParameterDecode implements Responder {
    private String body;
    private Hashtable<String, Object> resp = new Hashtable<String, Object>();

    public ParameterDecode() {
        generateBody();
    }

    public Hashtable<String, Object> respond(Hashtable<String, Object> req) {
        Hashtable<String, String> messageHeader = new Hashtable<String, String>();

        getParams((Hashtable<String, String>) req.get("Parameters"));

        resp.put("status-line", ResponseStatusLine.get("200", req.get("HTTP-Version")));
        resp.put("message-body", body.getBytes(Charset.forName("utf-8")));
        messageHeader.put("Content-Length", String.valueOf(body.length()));
        messageHeader.put("Content-Type", "text/html");
        messageHeader.put("Connection", "close");
        resp.put("message-header", messageHeader);
        return resp;
    }

    private void generateBody() {
        body = HtmlGenerator.getDefaultHTML();
    }

    private void echoBody(String body) {
        this.body = HtmlGenerator.echoBody(body);
    }

    private void getParams(Hashtable body) {
        String newBody = " ";
        for(Object key: body.keySet()) {
            newBody = newBody.concat(key + " = " + body.get(key) + "</br>");
        }
        echoBody(newBody);
    }
}
