package uz.tenzorsoft.fetch24.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    @PostMapping(value = "/create",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createNews(
            @RequestPart(name = "newsCreateDto") @Valid NewsCreateDto newsCreateDto,
            @RequestPart(name = "headImage") MultipartFile headImage,
            @RequestHeader(name = "lang", defaultValue = "uz") String lang
            ) {

        return newsService.create(newsCreateDto, lang, headImage);

    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(
            @PathVariable(name = "id") Long id,
            @RequestHeader(name = "lang", defaultValue = "uz") String lang
    ){
        return newsService.findById(id, lang);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/updateById/{id}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateById(
            @PathVariable Long id,
            @RequestPart(name = "headImage", required = false) MultipartFile headImage,
            @RequestHeader(name = "lang") String lang,
            @RequestPart @Valid NewsUpdateDto newsUpdateDto
            ){
        return newsService.updateById(id, lang, newsUpdateDto, headImage);
    }

    @GetMapping("/findAllPagination/notDeleted")
    public ResponseEntity<?> findAllPaginationNotDeleted(
            @RequestHeader(name = "lang", defaultValue = "uz") String lang,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestHeader(name = "size", defaultValue = "10") Integer size
    ){
        return newsService.findAllNotDeleted(lang, page, size);
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
            @RequestHeader(name = "lang", defaultValue = "uz") String lang,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ){
        return newsService.findAllDeleted(lang, page, size);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<?> hardDelete(
            @PathVariable Long id
    ){
        return newsService.hardDelete(id);
    }



}
