package com.xserver.examples;

import com.xserver.core.Router;
import com.xserver.request.Request;
import com.xserver.response.Response;
import com.xserver.response.error.XServerException;

public class V1Router extends Router {
  V1Router() {
    register("notes", NotesHandler.class);
  }

  @Override
  public Response get(Request request) throws XServerException {
    return Response.ok().text("V1");
  }
}
