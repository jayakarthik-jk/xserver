package com.xserver.core;

import com.xserver.request.Request;
import com.xserver.response.Response;
import com.xserver.response.error.XServerException;
import com.xserver.response.error.client.MethodNotAllowed;

public interface Handler {
  default Response get(Request request) throws XServerException {
    throw new MethodNotAllowed();
  }

  default Response post(Request request) throws XServerException {
    throw new MethodNotAllowed();
  }

  default Response put(Request request) throws XServerException {
    throw new MethodNotAllowed();
  }

  default Response patch(Request request) throws XServerException {
    throw new MethodNotAllowed();
  }

  default Response delete(Request request) throws XServerException {
    throw new MethodNotAllowed();
  }

  default Response dispatch(Request request) throws XServerException {
    if (!request.getEndPoints().isEmpty()) {
      throw new MethodNotAllowed();
    }
    return switch (request.getMethod()) {
      case GET -> get(request);
      case POST -> post(request);
      case PUT -> put(request);
      case PATCH -> patch(request);
      case DELETE -> delete(request);
    };
  }
}
