package uz.tenzorsoft.fetch24.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.tenzorsoft.fetch24.model.NewsStatus;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsHistoryResponseDto {
    private Long id;
    private Long newsId;
    private Long changedByUserId;
    private String changedByUsername;
    private NewsStatus fromStatus;
    private NewsStatus toStatus;
    private String diffJson;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime changedAt;
}