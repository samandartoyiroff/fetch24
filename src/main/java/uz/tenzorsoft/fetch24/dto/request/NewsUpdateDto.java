package uz.tenzorsoft.fetch24.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

}
