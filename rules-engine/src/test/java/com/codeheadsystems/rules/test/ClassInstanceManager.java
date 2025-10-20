package com.codeheadsystems.rules.test;

import java.util.HashMap;
import java.util.Optional;

public class ClassInstanceManager {

  private final HashMap<Class<?>, Object> instances = new HashMap<>();

  public <T> Optional<T> get(final Class<T> clazz) {
    return optionalIfSet(clazz, instances.get(clazz));
  }

  public <T> Optional<T> remove(final Class<T> clazz) {
    return optionalIfSet(clazz, instances.remove(clazz));
  }

  public <T> void removeAndClose(final Class<T> clazz) {
    remove(clazz).ifPresent(instance -> {
      if (instance instanceof AutoCloseable closeable) {
        try {
          closeable.close();
        } catch (final Throwable e) {
          // ignore
        }
      }
    });
  }

  private <T> Optional<T> optionalIfSet(final Class<T> clazz, final Object instance) {
    if (instance == null) {
      return Optional.empty();
    } else {
      return Optional.of(clazz.cast(instance));
    }
  }

  public <T> void put(final Class<T> clazz, final T instance) {
    instances.put(clazz, instance);
  }

  public <T> void put(final T instance) {
    instances.put(instance.getClass(), instance);
  }

  public void clear() {
    instances.clear();
  }

  public void clearAndClose() {
    instances.values().forEach(instance -> {
      if (instance instanceof AutoCloseable closeable) {
        try {
          closeable.close();
        } catch (final Throwable e) {
          // ignore
        }
      }
    });
    instances.clear();
  }

  public boolean hasInstance(final Class<?> clazz) {
    return instances.containsKey(clazz);
  }
}
