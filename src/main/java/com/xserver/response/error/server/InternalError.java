package com.xserver.response.error.server;

import com.xserver.response.error.XServerException;

public class InternalError extends XServerException {

  public InternalError(String error) {
    super(error);
    status(500);
  }

  public InternalError() {
    this("Internal Error");
  }

}
