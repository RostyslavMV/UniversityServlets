package com.rmv.university.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestUtil {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static <T> T getRequestObject(HttpServletRequest request, Class<T> tClass) {
    try {
      return objectMapper.readValue(request.getReader(), tClass);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
