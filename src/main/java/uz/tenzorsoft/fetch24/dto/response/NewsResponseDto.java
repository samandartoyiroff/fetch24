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
public class NewsResponseDto {
    private Long id;
    private String title;
    private String description;
    private String content;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime updatedAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime publishedDate;

    private Long createdBy;
    private Long updatedBy;

    private NewsStatus status;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime unpublishAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime deletedAt;

    public NewsResponseDto(Long id, String title, String description, String content,
                           LocalDateTime createdDate, LocalDateTime updatedAt, LocalDateTime publishedDate,
                           Long createdBy, Long updatedBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedAt = updatedAt;
        this.publishedDate = publishedDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}