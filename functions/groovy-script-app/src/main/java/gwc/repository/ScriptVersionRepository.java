package gwc.repository;

import gwc.entity.ScriptVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScriptVersionRepository extends JpaRepository<ScriptVersion, Long> {
  List<ScriptVersion> findByScriptIdOrderByVersionNumberDesc(Long scriptId);
}
