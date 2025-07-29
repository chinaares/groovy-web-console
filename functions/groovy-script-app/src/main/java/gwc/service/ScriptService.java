package gwc.service;

import gwc.entity.ExecutionLog;
import gwc.entity.Script;
import gwc.entity.ScriptVersion;
import gwc.repository.ExecutionLogRepository;
import gwc.repository.ScriptRepository;
import gwc.repository.ScriptVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScriptService {
  private final ScriptRepository scriptRepo;
  private final ScriptVersionRepository versionRepo;
  private final ExecutionLogRepository logRepo;
  private final GroovyExecutorService executor;

  public Script createScript(String name, String content, String createdBy) {
    Script script = new Script();
    script.setName(name);
    script.setCreatedAt(LocalDateTime.now());
    script.setUpdatedAt(LocalDateTime.now());
    scriptRepo.save(script);

    ScriptVersion version = new ScriptVersion();
    version.setScript(script);
    version.setVersionNumber(1);
    version.setContent(content);
    version.setCreatedBy(createdBy);
    version.setCreatedAt(LocalDateTime.now());
    versionRepo.save(version);

    return script;
  }

  public Page<Script> listScripts(Pageable pageable) {
    return scriptRepo.findAll(pageable);
  }

  public Script updateScript(Long id, String content, String updatedBy) {
    Script script = scriptRepo.findById(id).orElseThrow();
    script.setUpdatedAt(LocalDateTime.now());
    scriptRepo.save(script);

    int nextVersion = versionRepo.findByScriptIdOrderByVersionNumberDesc(id)
      .stream().findFirst().map(v -> v.getVersionNumber() + 1).orElse(1);

    ScriptVersion version = new ScriptVersion();
    version.setScript(script);
    version.setVersionNumber(nextVersion);
    version.setContent(content);
    version.setCreatedBy(updatedBy);
    version.setCreatedAt(LocalDateTime.now());
    versionRepo.save(version);

    return script;
  }

  public void deleteScript(Long id) {
    scriptRepo.deleteById(id);
  }

  public List<ScriptVersion> getVersions(Long scriptId) {
    return versionRepo.findByScriptIdOrderByVersionNumberDesc(scriptId);
  }

  public List<ExecutionLog> getLogs(Long scriptId) {
    return logRepo.findByVersionScriptIdOrderByExecutedAtDesc(scriptId);
  }

  public String executeScript(Long scriptId, String executedBy) {
    ScriptVersion version = versionRepo.findByScriptIdOrderByVersionNumberDesc(scriptId).get(0);
    String result;
    boolean success = true;
    String error = null;
    try {
      result = executor.execute(version.getContent());
    } catch (Exception e) {
      success = false;
      result = "";
      error = e.getMessage();
    }
    ExecutionLog log = new ExecutionLog();
    log.setVersion(version);
    log.setExecutedBy(executedBy);
    log.setExecutedAt(LocalDateTime.now());
    log.setSuccess(success);
    log.setOutput(result);
    log.setErrorMessage(error);
    logRepo.save(log);
    return success ? result : error;
  }
}
