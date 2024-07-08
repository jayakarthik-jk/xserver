package com.xserver.request;

import java.util.Optional;

public enum Method {
  GET,
  POST,
  PUT,
  DELETE,
  PATCH;

  public static Optional<Method> from(String value) {
    Method result = switch (value) {
      case "GET" -> GET;
      case "POST" -> POST;
      case "PUT" -> PUT;
      case "PATCH" -> PATCH;
      case "DELETE" -> DELETE;
      default -> null;
    };
    if (result == null) {
      return Optional.empty();
    }
    return Optional.of(result);
  }
}