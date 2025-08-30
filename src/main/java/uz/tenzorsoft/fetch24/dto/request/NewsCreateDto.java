package uz.tenzorsoft.fetch24.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsCreateDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String content;

    private String redirectUrl;

    @NotBlank(message = "{news.title.notblank}")
    private String titleUz;

    @NotBlank(message = "{news.title.notblank}")
    private String titleRu;

    @NotBlank(message = "{news.title.notblank}")
    private String titleEn;

    @NotBlank(message = "{news.description.notblank}")
    private String descriptionUz;

    @NotBlank(message = "{news.description.notblank}")
    private String descriptionRu;

    @NotBlank(message = "{news.description.notblank}")
    private String descriptionEn;

    @NotBlank(message = "{news.content.notblank}")
    private String contentUz;

    @NotBlank(message = "{news.content.notblank}")
    private String contentRu;

    @NotBlank(message = "{news.content.notblank}")
    private String contentEn;

    private LocalDateTime publishAt;

    private LocalDateTime unpublishAt;
}
