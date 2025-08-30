package uz.tenzorsoft.fetch24.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import uz.tenzorsoft.fetch24.dto.request.NewsCreateDto;
import uz.tenzorsoft.fetch24.dto.request.NewsStatusUpdateDto;
import uz.tenzorsoft.fetch24.dto.request.NewsUpdateDto;

public interface NewsService    {
    ResponseEntity<?> create(@Valid NewsCreateDto newsCreateDto, String lang);

    ResponseEntity<?> findById(Long id, String lang);

    ResponseEntity<?> updateById(Long id, String lang, @Valid NewsUpdateDto newsUpdateDto);

    ResponseEntity<?> findAllNotDeleted(String lang);

    ResponseEntity<?> softDelete(Long id, String lang);


    ResponseEntity<?> findAllDeleted(String lang);

    ResponseEntity<?> hardDelete(Long id);

    ResponseEntity<?> updateNewsStatus(Long id, @Valid NewsStatusUpdateDto statusUpdateDto);

    ResponseEntity<?> restoreNews(Long id, String lang);

    ResponseEntity<?> getNewsHistory(Long id);

    ResponseEntity<?> findAllPublicNews(String lang);
}
