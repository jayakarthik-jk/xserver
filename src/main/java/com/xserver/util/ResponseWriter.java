package com.xserver.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.xserver.request.Request;
import com.xserver.response.Response;

public class ResponseWriter {
  private Socket socket;
  private Request request;

  private ResponseWriter(Socket socket, Request request) {
    this.socket = socket;
    this.request = request;
  }

  public static ResponseWriter getWriter(Socket socket, Request request) {
    return new ResponseWriter(socket, request);
  }

  public void write(Response response) throws IOException {
    var protocol = request != null ? request.getProtocol() : "HTTP/1.1";

    try (var writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
      writer.write(String.format("%s %s %s\n", protocol, response.status(), response.getMessage()));
      for (var header : response.headers().entrySet()) {
        writer.write(String.format("%s %s\n", header.getKey(), header.getValue()));
      }
      writer.write('\n');
      writer.write(response.text());
    }

  }
}