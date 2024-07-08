package com.xserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.xserver.request.Request;
import com.xserver.response.Response;
import com.xserver.response.error.XServerException;
import com.xserver.util.RequestDispatcher;
import com.xserver.util.RequestParser;
import com.xserver.util.ResponseWriter;

public class Server {
  private final Map<String, Class<? extends Handler>> handlers;

  private static final class ServerInstanceHelper {
    private static final Server instance = new Server();
  }

  private Server() {
    handlers = new HashMap<>();
  }

  public static synchronized Server getInstance() {
    return ServerInstanceHelper.instance;
  }

  public Server register(Class<? extends Handler> handler) {
    handlers.put("", handler);
    return this;
  }

  public Server register(String endPoint, Class<? extends Handler> handler) {
    handlers.put(endPoint, handler);
    return this;
  }

  public void run(int port) throws IOException {
    try (var serverSocket = new ServerSocket(port)) {
      System.out.printf("Server started listening at %d\n", port);
      while (true) {
        var socket = serverSocket.accept();
        process(socket);
      }
    }
  }

  private void process(Socket socket) throws IOException {
    Response response;
    Request request;
    try {
      request = RequestParser.getParser(socket).parse();
      response = RequestDispatcher.getDispatcher(handlers, request).dispatch();
    } catch (XServerException error) {
      response = error;
      request = null;
    }
    ResponseWriter.getWriter(socket, request).write(response);

  }

}