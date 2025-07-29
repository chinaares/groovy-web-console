package gwc.repository;

import gwc.entity.ExecutionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionLogRepository extends JpaRepository<ExecutionLog, Long> {
  List<ExecutionLog> findByVersionScriptIdOrderByExecutedAtDesc(Long scriptId);
}
