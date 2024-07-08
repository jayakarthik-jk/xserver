package com.xserver.exception;

public class UnableToCreateInstance extends Exception {
  public UnableToCreateInstance() {
    super("Unable to instantiate the handler. make sure the constructor of your handler in public.");
  }
}
