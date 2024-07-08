package com.xserver.examples;

import com.xserver.core.Handler;
import com.xserver.request.Request;
import com.xserver.response.Response;
import com.xserver.response.error.XServerException;

public class HomeHandler implements Handler {
  @Override
  public Response get(Request request) throws XServerException {
    return Response.ok().text("Home");
  }
}
