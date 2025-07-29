package gwc.controller;

import com.google.cloud.functions.HttpRequest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SimpleHttpRequest implements HttpRequest {

  private final String body;
  private final Map<String, List<String>> headers;

  public SimpleHttpRequest(String body) {
    this.body = body;
    this.headers = Map.of("Content-Type", List.of("application/json"));
  }

  @Override
  public BufferedReader getReader() {
    return new BufferedReader(new StringReader(body));
  }

  @Override
  public InputStream getInputStream() {
    return new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public Map<String, List<String>> getHeaders() {
    return headers;
  }

  @Override
  public String getMethod() {
    return "POST";
  }

  @Override
  public String getUri() {
    return "/gcf/run";
  }

  @Override
  public String getPath() {
    return "/gcf/run";
  }

  @Override
  public Optional<String> getQuery() {
    return Optional.empty();
  }

  @Override
  public Map<String, List<String>> getQueryParameters() {
    return Map.of();
  }

  @Override
  public Map<String, HttpPart> getParts() {
    return Map.of();
  }

  @Override
  public Optional<String> getContentType() {
    return Optional.of("application/json");
  }

  @Override
  public long getContentLength() {
    return body.length();
  }

  @Override
  public Optional<String> getCharacterEncoding() {
    return Optional.of("UTF-8");
  }
}
