package gwc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ScriptVersion {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Script script;

  private Integer versionNumber;

  @Lob
  private String content;

  private String createdBy;
  private LocalDateTime createdAt;
}
