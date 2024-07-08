package com.xserver.response.error.client;

import com.xserver.response.error.XServerException;

public class UnAuthorized extends XServerException {
  public UnAuthorized(String message) {
    super(message);
    status(401);
  }

  public UnAuthorized() {
    this("Unauthorized");
  }
}
