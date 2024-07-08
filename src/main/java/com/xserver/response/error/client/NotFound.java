package com.xserver.response.error.client;

import com.xserver.response.error.XServerException;

public class NotFound extends XServerException {
  public NotFound(String message) {
    super(message);
    status(404);
  }

  public NotFound() {
    this("Not found");
  }
}
