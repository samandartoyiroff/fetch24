package uz.tenzorsoft.fetch24.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.tenzorsoft.fetch24.dto.request.NewsCreateDto;
import uz.tenzorsoft.fetch24.dto.request.NewsStatusUpdateDto;
import uz.tenzorsoft.fetch24.dto.request.NewsUpdateDto;
import uz.tenzorsoft.fetch24.service.NewsService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_AUTHOR')")
    @PostMapping("/create")
    public ResponseEntity<?> createNews(
            @RequestPart @Valid NewsCreateDto newsCreateDto,
            @RequestPart @Valid MultipartFile headImage,
            @RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang
    ) {
        return newsService.create(newsCreateDto, lang, headImage);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(
            @PathVariable(name = "id") Long id,
            @RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang
    ) {
        return newsService.findById(id, lang);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_AUTHOR')")
    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateById(
            @PathVariable Long id,
            @RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
            @RequestPart @Valid NewsUpdateDto newsUpdateDto,
            @RequestPart(required = false) @Valid MultipartFile headImage

    ) {
        return newsService.updateById(id, lang, newsUpdateDto, headImage );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_AUTHOR')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateNewsStatus(
            @PathVariable Long id,
            @RequestBody @Valid NewsStatusUpdateDto statusUpdateDto
    ) {
        return newsService.updateNewsStatus(id, statusUpdateDto);
    }

    @GetMapping("/findAllPagination/notDeleted")
    public ResponseEntity<?> findAllPaginationNotDeleted(
            @RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        return newsService.findAllNotDeleted(lang, page, size);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDelete(
            @PathVariable Long id,
            @RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang
    ) {
        return newsService.softDelete(id, lang);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{id}/restore")
    public ResponseEntity<?> restoreNews(
            @PathVariable Long id,
            @RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang
    ) {
        return newsService.restoreNews(id, lang);
    }

    @GetMapping("/findAllPagination/deleted")
    public ResponseEntity<?> findAllPaginationDeleted(
            @RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        return newsService.findAllDeleted(lang,  page, size);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<?> hardDelete(
            @PathVariable Long id
    ) {
        return newsService.hardDelete(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_AUTHOR')")
    @GetMapping("/{id}/history")
    public ResponseEntity<?> getNewsHistory(
            @PathVariable Long id
    ) {
        return newsService.getNewsHistory(id);
    }

    @GetMapping("/public")
    public ResponseEntity<?> findAllPublicNews(
            @RequestHeader(name = "Accept-Language", defaultValue = "uz") String lang
    ) {
        return newsService.findAllPublicNews(lang);
    }
}