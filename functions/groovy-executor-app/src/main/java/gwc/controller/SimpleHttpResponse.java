package gwc.controller;

import com.google.cloud.functions.HttpResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SimpleHttpResponse implements HttpResponse {

  private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
  private final BufferedWriter writer;
  private final Map<String, List<String>> headers = new HashMap<>();
  private int statusCode = 200;
  private String contentType;

  public SimpleHttpResponse(OutputStream outputStream) {
    this.writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
  }
  @Override
  public BufferedWriter getWriter() {
    return writer;
  }

  @Override
  public OutputStream getOutputStream() {
    return buffer;
  }

  @Override
  public void setStatusCode(int code) {
    this.statusCode = code;
  }

  @Override
  public void setStatusCode(int code, String message) {
    this.statusCode = code;
  }

  @Override
  public void appendHeader(String name, String value) {
    headers.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
  }

  @Override
  public Map<String, List<String>> getHeaders() {
    return headers;
  }

  @Override
  public void setContentType(String contentType) {
    this.contentType = contentType;
    appendHeader("Content-Type", contentType);
  }

  @Override
  public Optional<String> getContentType() {
    return Optional.ofNullable(contentType);
  }

  // ✅ 添加：获取最终输出字符串（用于 Controller 中返回）
  public String getBodyAsString() throws IOException {
    writer.flush();
    return buffer.toString(StandardCharsets.UTF_8);
  }
}
