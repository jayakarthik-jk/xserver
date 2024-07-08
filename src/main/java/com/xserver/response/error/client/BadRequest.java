package com.xserver.response.error.client;

import com.xserver.response.error.XServerException;

public class BadRequest extends XServerException {

  public BadRequest(String message) {
    super(message);
    status(400);
  }

  public BadRequest() {
    this("Bad request");
  }
}
