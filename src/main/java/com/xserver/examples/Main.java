package com.xserver.examples;

import java.io.IOException;

import com.xserver.core.Server;

public class Main {
  public static void main(String[] args) throws IOException {
    Server.getInstance()
        .register(HomeHandler.class)
        .register("api", ApiRouter.class)
        .run(3000);
  }
}
