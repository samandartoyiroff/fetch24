package uz.tenzorsoft.fetch24.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.tenzorsoft.fetch24.domain.News;
import uz.tenzorsoft.fetch24.dto.request.NewsCreateDto;
import uz.tenzorsoft.fetch24.dto.request.NewsUpdateDto;
import uz.tenzorsoft.fetch24.service.NewsService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createNews(
            @RequestBody @Valid NewsCreateDto newsCreateDto,
            @RequestHeader(name = "lang", defaultValue = "uz") String lang
            ) {

        return newsService.create(newsCreateDto, lang);

    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(
            @PathVariable(name = "id") Long id,
            @RequestHeader(name = "lang", defaultValue = "uz") String lang
    ){
        return newsService.findById(id, lang);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateById(
            @PathVariable Long id,
            @RequestHeader(name = "lang") String lang,
            @RequestBody @Valid NewsUpdateDto newsUpdateDto
            ){
        return newsService.updateById(id, lang, newsUpdateDto);
    }

    @GetMapping("/findAllPagination/notDeleted")
    public ResponseEntity<?> findAllPaginationNotDeleted(
            @RequestHeader(name = "lang", defaultValue = "uz") String lang
    ){
        return newsService.findAllNotDeleted(lang);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDelete(
            @PathVariable Long id,
            @RequestHeader(name = "lang", defaultValue = "uz") String lang
    ){
        return newsService.softDelete(id, lang);
    }

    @GetMapping("/findAllPagination/deleted")
    public ResponseEntity<?> findAllPaginationDeleted(
            @RequestHeader(name = "lang", defaultValue = "uz") String lang
    ){
        return newsService.findAllDeleted(lang);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<?> hardDelete(
            @PathVariable Long id
    ){
        return newsService.hardDelete(id);
    }



}
