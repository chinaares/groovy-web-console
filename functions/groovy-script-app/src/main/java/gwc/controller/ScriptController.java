package gwc.controller;

import gwc.entity.ExecutionLog;
import gwc.entity.Script;
import gwc.entity.ScriptVersion;
import gwc.service.ScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scripts")
@RequiredArgsConstructor
public class ScriptController {
  private final ScriptService scriptService;

  @PostMapping
  public Script createScript(@RequestParam String name, @RequestParam String content, @RequestParam String user) {
    return scriptService.createScript(name, content, user);
  }

  @GetMapping
  public Page<Script> listScripts(@RequestParam int page, @RequestParam int size) {
    return scriptService.listScripts(PageRequest.of(page, size));
  }

  @PutMapping("/{id}")
  public Script updateScript(@PathVariable Long id, @RequestParam String content, @RequestParam String user) {
    return scriptService.updateScript(id, content, user);
  }

  @DeleteMapping("/{id}")
  public void deleteScript(@PathVariable Long id) {
    scriptService.deleteScript(id);
  }

  @GetMapping("/{id}/versions")
  public List<ScriptVersion> getVersions(@PathVariable Long id) {
    return scriptService.getVersions(id);
  }

  @GetMapping("/{id}/logs")
  public List<ExecutionLog> getLogs(@PathVariable Long id) {
    return scriptService.getLogs(id);
  }

  @PostMapping("/{id}/execute")
  public String executeScript(@PathVariable Long id, @RequestParam String user) {
    return scriptService.executeScript(id, user);
  }
}
