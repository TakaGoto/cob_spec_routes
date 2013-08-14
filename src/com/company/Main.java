package com.company;

import com.company.responders.*;
import com.server.Server;

public class Main {
    private static String rootDir = "/Users/takayuki/Coding/java/cob_spec/public\n";
    private static int defaultPort = 5000;

    public static void main(String[] args) {
        parseArgs(args);
        Server server = new Server(defaultPort, rootDir);
        BasicAuth basicAuth = new BasicAuth();
        server.mount("/", new Root());
        server.mount("/form", new PutPost());
        server.mount("/parameters", new ParameterDecode());
        server.mount("/redirect", new Redirect());
        server.mount("/file1", new FileHandler(rootDir));
        server.mount("/text-file.txt", new FileHandler(rootDir));
        server.mount("/logs", basicAuth);
        server.mount("/log", basicAuth);
        server.mount("/these", basicAuth);
        server.mount("/requests", basicAuth);
        server.mount("/image.jpeg", new FileHandler(rootDir));
        server.mount("/image.gif", new FileHandler(rootDir));
        server.mount("/image.png", new FileHandler(rootDir));
        server.listen();
    }

    private static void parseArgs(String[] args) {
        try {
            defaultPort = Integer.parseInt(args[1]);
            rootDir = args[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Proper Usage: java -jar <your jar file> -p <port> -d <directory to serve>");
        }
    }
}
