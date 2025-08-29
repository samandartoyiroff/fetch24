package uz.tenzorsoft.fetch24.mapper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.tenzorsoft.fetch24.domain.News;
import uz.tenzorsoft.fetch24.dto.request.NewsCreateDto;
import uz.tenzorsoft.fetch24.dto.request.NewsUpdateDto;
import uz.tenzorsoft.fetch24.dto.response.NewsResponseDto;
import uz.tenzorsoft.fetch24.service.FileService;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsMapper {

    private final FileService fileService;

    public NewsMapper(FileService fileService) {
        this.fileService = fileService;
    }

    public News toEntity(String lang, NewsCreateDto dto, MultipartFile headImage) {
        News news = new News();
        news.setRedirectUrl(dto.getRedirectUrl());

        switch (lang.toLowerCase()) {
            case "en" -> {
                news.setTitleEn(dto.getTitle());
                news.setDescriptionEn(dto.getDescription());
                news.setContentEn(dto.getContent());
            }
            case "uz" -> {
                news.setTitleUz(dto.getTitle());
                news.setDescriptionUz(dto.getDescription());
                news.setContentUz(dto.getContent());
            }
            case "ru" -> {
                news.setTitleRu(dto.getTitle());
                news.setDescriptionRu(dto.getDescription());
                news.setContentRu(dto.getContent());
            }
            default -> throw new IllegalArgumentException("Unsupported language: " + lang);
        }

        String base64 = fileService.base64(headImage);

        news.setHeadImage(base64);

        return news;
    }


    public NewsResponseDto toDto(News news, String lang) {
        String title = null;
        String description = null;
        String content = null;

        // --- TITLE ---
        if ("uz".equalsIgnoreCase(lang) && news.getTitleUz() != null) {
            title = news.getTitleUz();
        } else if ("en".equalsIgnoreCase(lang) && news.getTitleEn() != null) {
            title = news.getTitleEn();
        } else if ("ru".equalsIgnoreCase(lang) && news.getTitleRu() != null) {
            title = news.getTitleRu();
        }

        if (title == null) { // fallback
            if (news.getTitleUz() != null) title = news.getTitleUz();
            else if (news.getTitleEn() != null) title = news.getTitleEn();
            else if (news.getTitleRu() != null) title = news.getTitleRu();
        }

        // --- DESCRIPTION ---
        if ("uz".equalsIgnoreCase(lang) && news.getDescriptionUz() != null) {
            description = news.getDescriptionUz();
        } else if ("en".equalsIgnoreCase(lang) && news.getDescriptionEn() != null) {
            description = news.getDescriptionEn();
        } else if ("ru".equalsIgnoreCase(lang) && news.getDescriptionRu() != null) {
            description = news.getDescriptionRu();
        }

        if (description == null) {
            if (news.getDescriptionUz() != null) description = news.getDescriptionUz();
            else if (news.getDescriptionEn() != null) description = news.getDescriptionEn();
            else if (news.getDescriptionRu() != null) description = news.getDescriptionRu();
        }

        if ("uz".equalsIgnoreCase(lang) && news.getContentUz() != null) {
            content = news.getContentUz();
        } else if ("en".equalsIgnoreCase(lang) && news.getContentEn() != null) {
            content = news.getContentEn();
        } else if ("ru".equalsIgnoreCase(lang) && news.getContentRu() != null) {
            content = news.getContentRu();
        }

        if (content == null) {
            if (news.getContentUz() != null) content = news.getContentUz();
            else if (news.getContentEn() != null) content = news.getContentEn();
            else if (news.getContentRu() != null) content = news.getContentRu();
        }

        return new NewsResponseDto(
                news.getId(),
                news.getHeadImage(),
                title,
                description,
                content,
                news.getCreatedAt(),
                news.getUpdatedAt(),
                news.getPublishedAt(),
                news.getCreatedBy(),
                news.getUpdatedBy()
        );
    }

    public News updateNews(String lang, News news, NewsUpdateDto dto, MultipartFile headImage) {
        if (news == null || dto == null) {
            return news;
        }

        switch (lang.toLowerCase()) {
            case "uz" -> {
                if (dto.getTitle() != null) news.setTitleUz(dto.getTitle());
                if (dto.getDescription() != null) news.setDescriptionUz(dto.getDescription());
                if (dto.getContent() != null) news.setContentUz(dto.getContent());
            }
            case "en" -> {
                if (dto.getTitle() != null) news.setTitleEn(dto.getTitle());
                if (dto.getDescription() != null) news.setDescriptionEn(dto.getDescription());
                if (dto.getContent() != null) news.setContentEn(dto.getContent());
            }
            case "ru" -> {
                if (dto.getTitle() != null) news.setTitleRu(dto.getTitle());
                if (dto.getDescription() != null) news.setDescriptionRu(dto.getDescription());
                if (dto.getContent() != null) news.setContentRu(dto.getContent());
            }
            default -> throw new IllegalArgumentException("Unsupported language: " + lang);
        }

        if (dto.getRedirectUrl() != null) {
            news.setRedirectUrl(dto.getRedirectUrl());
        }

        if (headImage != null) {

            String base64 = fileService.base64(headImage);

            news.setHeadImage(base64);

        }

        return news;
    }


    public List<NewsResponseDto> toList(String lang, List<News> news) {


        List<NewsResponseDto> newsResponseDtos = new ArrayList<>();

        news.forEach(newsDto -> {

            newsResponseDtos.add(toDto(newsDto, lang));

        });

        return newsResponseDtos;

    }
}
