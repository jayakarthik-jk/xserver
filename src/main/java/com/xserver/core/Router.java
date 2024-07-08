package com.xserver.core;

import java.util.HashMap;
import java.util.Map;

import com.xserver.request.Request;
import com.xserver.response.Response;
import com.xserver.response.error.XServerException;
import com.xserver.response.error.client.NotFound;
import com.xserver.util.DependencyRegistry;

public abstract class Router implements Handler {
  private final Map<String, Class<? extends Handler>> subRouters = new HashMap<>();

  public Router register(String endPoint, Class<? extends Handler> router) {
    subRouters.put(endPoint, router);
    return this;
  }

  @Override
  public Response dispatch(Request request) throws XServerException {
    if (request.getEndPoints().isEmpty()) {
      return Handler.super.dispatch(request);
    }

    var endPoint = request.getEndPoints().poll();
    // TODO: instead of contains key. we need to use regex
    if (!subRouters.containsKey(endPoint)) {
      throw new NotFound();
    }

    var subRouter = subRouters.get(endPoint);
    return DependencyRegistry.createInstance(subRouter).dispatch(request);
  }
}
