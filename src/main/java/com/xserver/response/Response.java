package com.xserver.response;

import java.util.HashMap;
import java.util.Map;

public class Response extends Exception {

  private Map<String, String> headers = new HashMap<>();
  private int status = 200;
  private String body = "";

  protected Response() {
    super();
  }

  protected Response(String message) {
    super(message);
  }

  protected Response(int status) {
    super(defaultSuccessMessage(status));
    status(status);
  }

  private static String defaultSuccessMessage(int status) {
    return switch (status) {
      case 200 -> "Success";
      case 201 -> "Created";
      case 202 -> "Accepted";
      case 203 -> "Non-Authoritative Information";
      case 204 -> "No Content";
      case 205 -> "Reset Content";
      case 206 -> "Partial Content";
      case 207 -> "Multi-Status";
      case 208 -> "Already Reported";
      case 226 -> "In Used";
      case 300 -> "Multiple Choices";
      case 301 -> "Moved Permanently";
      case 302 -> "Found";
      case 303 -> "See Other";
      case 304 -> "Not Modified";
      case 305 -> "Use Proxy";
      case 306 -> "unused";
      case 307 -> "Temporary Redirect";
      case 308 -> "Permanent Redirect";
      default -> "";
    };
  }

  // Body field
  public String text() {
    return body;
  }

  public Response text(String message) {
    this.body = message;
    return this;
  }

  // Static helper methods
  public static Response ok() {
    return new Response(200);
  }

  public static Response created() {
    return new Response(201);
  }

  public static Response accepted() {
    return new Response(202);
  }

  public static Response permanentRedirec() {
    return new Response(308);
  }

  public static Response temporaryRedirec() {
    return new Response(307);
  }

  public static Response unuse() {
    return new Response(306);
  }

  public static Response useProxy() {
    return new Response(305);
  }

  public static Response notModified() {
    return new Response(304);
  }

  public static Response seeOther() {
    return new Response(303);
  }

  public static Response found() {
    return new Response(302);
  }

  public static Response movedPermanently() {
    return new Response(301);
  }

  public static Response multipleChoices() {
    return new Response(300);
  }

  public static Response inUsed() {
    return new Response(226);
  }

  public static Response alreadyReported() {
    return new Response(208);
  }

  public static Response multiStatus() {
    return new Response(207);
  }

  public static Response partialContent() {
    return new Response(206);
  }

  public static Response resetContent() {
    return new Response(205);
  }

  public static Response noContent() {
    return new Response(204);
  }

  public static Response nonAuthoritativeInformation() {
    return new Response(203);
  }

  // Headers
  public Map<String, String> headers() {
    return headers;
  }

  public String header(String key) {
    return headers.get(key);
  }

  public Response header(String key, String value) {
    headers.put(key, value);
    return this;
  }

  // Status
  public int status() {
    return status;
  }

  public Response status(int status) {
    this.status = status;
    return this;
  }

}