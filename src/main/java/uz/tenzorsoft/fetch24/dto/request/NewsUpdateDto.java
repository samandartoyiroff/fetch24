package uz.tenzorsoft.fetch24.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class NewsUpdateDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String content;

    private String redirectUrl;

    private String titleUz;

    private String titleRu;

    private String titleEn;

    private String descriptionUz;

    private String descriptionRu;

    private String descriptionEn;

    private String contentUz;

    private String contentRu;

    private String contentEn;

    private LocalDateTime publishAt;

    private LocalDateTime unpublishAt;
}