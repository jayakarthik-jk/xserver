package com.xserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.xserver.request.Method;
import com.xserver.request.Request;
import com.xserver.response.error.XServerException;
import com.xserver.response.error.client.BadRequest;

public class RequestParser {
  InputStream stream;

  private RequestParser(InputStream stream) {
    this.stream = stream;
  }

  public static RequestParser getParser(Socket socket) throws IOException {
    var stream = socket.getInputStream();
    return new RequestParser(stream);
  }

  public Request parse() throws IOException, XServerException {
    var reader = new BufferedReader(new InputStreamReader(stream));
    var line = reader.readLine();
    if (line == null) {
      throw new BadRequest();
    }
    var line1Splitted = line.split(" ");
    if (line1Splitted.length < 3) {
      throw new BadRequest();
    }
    var method = Method.from(line1Splitted[0]);
    if (method.isEmpty()) {
      throw new BadRequest();
    }
    var path = line1Splitted[1];
    var protocol = line1Splitted[2];
    var pathSplitted = path.split("\\?");
    var pathWithoutQueryParams = pathSplitted[0];
    Map<String, String> queryParams;

    if (pathSplitted.length > 2) {
      queryParams = parseQueryParams(pathSplitted[1]);
    } else {
      queryParams = new HashMap<>();
    }

    var endPoints = parseEndPoints(pathWithoutQueryParams);
    var headers = parseHeaders(reader);

    return new Request(method.get(), path, headers, queryParams, endPoints, stream, protocol);
  }

  private static Map<String, String> parseQueryParams(String params) {
    HashMap<String, String> queryParams = new HashMap<>();
    var splittedParams = params.split("&");
    for (String param : splittedParams) {
      var paramSplitted = param.split("=", 2);
      if (paramSplitted.length == 1) {
        queryParams.put(paramSplitted[0], "");
      } else {
        queryParams.put(paramSplitted[0], paramSplitted[1]);
      }
    }
    return queryParams;
  }

  private static Queue<String> parseEndPoints(String path) {
    Queue<String> endPoints = new LinkedList<>();
    String[] splitted;
    splitted = path.split("/");
    for (int i = 1; i < splitted.length; i++) {
      String endPoint = splitted[i];
      endPoints.add(endPoint);
    }
    return endPoints;
  }

  private static Map<String, String> parseHeaders(BufferedReader reader) throws IOException {
    Map<String, String> headers = new HashMap<>();

    while (reader.ready()) {
      var line = reader.readLine();
      var pair = line.split(": ", 2);
      if (pair.length < 2) {
        break;
      }
      headers.put(pair[0], pair[1]);
    }

    return headers;
  }

}