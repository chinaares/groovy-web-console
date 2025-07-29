package gwc.entity; import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ExecutionLog {
  @Id @GeneratedValue
  private Long id;

  @ManyToOne
  private ScriptVersion version;

  private String executedBy;
  private LocalDateTime executedAt;
  private Boolean success;

  @Lob
  private String output;
  @Lob
  private String errorMessage;
}
