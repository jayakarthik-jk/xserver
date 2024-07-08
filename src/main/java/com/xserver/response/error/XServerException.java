package com.xserver.response.error;

import com.xserver.response.Response;

public abstract class XServerException extends Response {
  protected XServerException() {
    super();
  }

  protected XServerException(String error) {
    super(error);
  }
}