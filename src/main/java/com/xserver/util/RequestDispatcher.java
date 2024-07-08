package com.xserver.util;

import java.util.Map;

import com.xserver.core.Handler;
import com.xserver.request.Request;
import com.xserver.response.Response;
import com.xserver.response.error.XServerException;
import com.xserver.response.error.client.NotFound;

public class RequestDispatcher {

  private Request request;
  private Map<String, Class<? extends Handler>> handlers;

  private RequestDispatcher(Map<String, Class<? extends Handler>> handlers, Request request) {
    this.handlers = handlers;
    this.request = request;
  }

  public static RequestDispatcher getDispatcher(Map<String, Class<? extends Handler>> handlers, Request request) {
    return new RequestDispatcher(handlers, request);
  }

  public Response dispatch() throws XServerException {
    var endPoint = request.getEndPoints().isEmpty() ? "" : request.getEndPoints().poll();
    // TODO: instead of contains key. we need to use regex
    var handler = handlers.get(endPoint);
    if (handler == null) {
      throw new NotFound();
    }
    return DependencyRegistry.createInstance(handler).dispatch(request);
  }
}