package com.xserver.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.xserver.exception.UnableToCreateInstance;

public class DependencyRegistry {

  private static Map<Class<?>, Optional<?>> singletons = new HashMap<>();

  public static <T> T createInstance(Class<T> clazz) {
    if (singletons.containsKey(clazz)) {
      return createSingletonInstance(clazz);
    }
    return createTransientInstance(clazz);
  }

  private static <T> T createTransientInstance(Class<T> clazz) {
    Constructor<T> constructor;
    try {
      constructor = clazz.getDeclaredConstructor();
    } catch (NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
      System.exit(1);
      return null;
    }
    var parameterTypes = constructor.getParameterTypes();
    var parameters = new Object[constructor.getParameterCount()];

    for (int i = 0; i < parameters.length; i++) {
      Class<?> parameterType = parameterTypes[i];
      parameters[i] = createInstance(parameterType);
    }

    try {
      return constructor.newInstance(parameters);
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      new UnableToCreateInstance().printStackTrace();
      System.exit(1);
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T createSingletonInstance(Class<T> clazz) {
    if (!singletons.containsKey(clazz)) {
      singletons.put(clazz, Optional.empty());
    }
    Optional<?> value = singletons.get(clazz);
    if (value.isEmpty()) {
      synchronized (clazz) {
        if (value.isEmpty()) {
          value = Optional.of(createTransientInstance(clazz));
          singletons.put(clazz, value);
        }
      }
    }

    return (T) value.get();
  }

  public static void registerSingleton(Class<?> clazz) {
    singletons.put(clazz, Optional.empty());
  }
}
