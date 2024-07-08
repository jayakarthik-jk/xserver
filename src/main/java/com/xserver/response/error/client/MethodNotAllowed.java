package com.xserver.response.error.client;

import com.xserver.response.error.XServerException;

public class MethodNotAllowed extends XServerException {
  public MethodNotAllowed(String message) {
    super(message);
    status(405);
  }

  public MethodNotAllowed() {
    this("Method not allowed");
  }

}