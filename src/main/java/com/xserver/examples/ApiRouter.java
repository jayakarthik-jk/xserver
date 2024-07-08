package com.xserver.examples;

import com.xserver.core.Router;
import com.xserver.request.Request;
import com.xserver.response.Response;
import com.xserver.response.error.XServerException;

public class ApiRouter extends Router {
  public ApiRouter() {
    register("v1", V1Router.class);
  }

  @Override
  public Response get(Request request) throws XServerException {
    return Response.ok().text("API");
  }

}
