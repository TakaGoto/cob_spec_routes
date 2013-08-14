package com.company;

import com.company.responders.*;
import com.server.Handlers.FileHandler;
import com.server.Server;

public class Main {
    private static String rootDir = "/Users/takayuki/Coding/java/cob_spec/public\n";

    public static void main(String[] args) {
        Server server = new Server(5000, rootDir);
        BasicAuth basicAuth = new BasicAuth();
        server.mount("/", new Root());
        server.mount("/form", new PutPost());
        server.mount("/parameters", new ParameterDecode());
        server.mount("/redirect", new Redirect());
        server.mount("/file1", new FileHandler(rootDir));
        server.mount("/logs", basicAuth);
        server.mount("/these", basicAuth);
        server.mount("/requests", basicAuth);
        server.listen();
    }
}
