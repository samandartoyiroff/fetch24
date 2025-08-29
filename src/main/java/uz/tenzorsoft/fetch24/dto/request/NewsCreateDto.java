package uz.tenzorsoft.fetch24.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

}
