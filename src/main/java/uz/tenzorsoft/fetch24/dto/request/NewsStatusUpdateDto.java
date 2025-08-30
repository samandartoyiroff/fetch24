package uz.tenzorsoft.fetch24.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.tenzorsoft.fetch24.model.NewsStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsStatusUpdateDto {

    @NotNull(message = "Yangi status maydonchasi bo'sh bo'lishi mumkin emas")
    private NewsStatus toStatus;
}