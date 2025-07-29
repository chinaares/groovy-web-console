package gwc.controller;

import com.google.cloud.functions.HttpRequest;
  import com.google.cloud.functions.HttpResponse;
  import gwc.GFunctionExecutor;
  import org.springframework.http.ResponseEntity;
  import org.springframework.web.bind.annotation.*;

  import java.io.*;
  import java.util.Map;

@RestController
public class FunctionController {

  private final GFunctionExecutor executor = new GFunctionExecutor();
  @RequestMapping(value = {"/gcf/run","/"}, method = RequestMethod.OPTIONS)
  public ResponseEntity<Void> corsPreflight() {
    return ResponseEntity.ok().build();
  }
  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PostMapping(value={"/gcf/run","/"})
  public ResponseEntity<String> runFunction(@RequestBody Map<String, Object> payload) throws Exception {
    String jsonInput = new com.google.gson.Gson().toJson(payload);

    // 构造模拟请求
    HttpRequest mockRequest = new SimpleHttpRequest(jsonInput);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    HttpResponse mockResponse = new SimpleHttpResponse(outStream);

    // 调用原始 HttpFunction
    executor.service(mockRequest, mockResponse);

    return ResponseEntity.ok(outStream.toString());
  }
}
