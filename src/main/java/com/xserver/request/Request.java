package com.xserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class Request {
  private Method method;
  private String path;
  private Map<String, String> headers;
  private Map<String, String> queryParams;
  private InputStream body;
  private String protocol;

  private Queue<String> endPoints;

  public Request(Method method, String path, Map<String, String> headers, Map<String, String> queryParams,
      Queue<String> endPoints, InputStream body, String protocol) {
    this.method = method;
    this.path = path;
    this.headers = headers;
    this.queryParams = queryParams;
    this.endPoints = endPoints;
    this.body = body;
    this.protocol = protocol;
  }

  public String getProtocol() {
    return protocol;
  }

  public Method getMethod() {
    return method;
  }

  public String getPath() {
    return path;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public Map<String, String> getQueryParams() {
    return queryParams;
  }

  public Queue<String> getEndPoints() {
    return endPoints;
  }

  public String text() {
    var result = new BufferedReader(new InputStreamReader(body))
        .lines().parallel().collect(Collectors.joining("\n"));
    close();
    return result;
  }

  private void close() {
    try {
      body.close();
    } catch (IOException e) {
      System.err.println("Unable to close the request stream");
    }
  }

  @Override
  public String toString() {
    return "Request [method=" + method + ", path=" + path + ", headers=" + headers + ", queryParams=" + queryParams
        + ", body=" + body + ", endPoints=" + endPoints + "]";
  }
}