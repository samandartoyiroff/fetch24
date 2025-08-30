package uz.tenzorsoft.fetch24.mapper;

import org.springframework.stereotype.Component;
import uz.tenzorsoft.fetch24.domain.News;
import uz.tenzorsoft.fetch24.dto.request.NewsCreateDto;
import uz.tenzorsoft.fetch24.dto.request.NewsUpdateDto;
import uz.tenzorsoft.fetch24.dto.response.NewsResponseDto;
import uz.tenzorsoft.fetch24.model.NewsStatus;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewsMapper {

    public News toEntity(String lang, NewsCreateDto dto) {
        News news = new News();
        news.setRedirectUrl(dto.getRedirectUrl());

        news.setTitleUz(dto.getTitleUz());
        news.setTitleRu(dto.getTitleRu());
        news.setTitleEn(dto.getTitleEn());

        news.setDescriptionUz(dto.getDescriptionUz());
        news.setDescriptionRu(dto.getDescriptionRu());
        news.setDescriptionEn(dto.getDescriptionEn());

        news.setContentUz(dto.getContentUz());
        news.setContentRu(dto.getContentRu());
        news.setContentEn(dto.getContentEn());

        news.setPublishedAt(dto.getPublishAt());
        news.setUnpublishAt(dto.getUnpublishAt());

        news.setStatus(NewsStatus.DRAFT);

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
            else title = "No title available"; // Default fallback
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
            else description = "No description available"; // Default fallback
        }

        // --- CONTENT ---
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
            else content = "No content available"; // Default fallback
        }

        NewsResponseDto dto = new NewsResponseDto(
                news.getId(),
                title,
                description,
                content,
                news.getCreatedAt(),
                news.getUpdatedAt(),
                news.getPublishedAt(),
                news.getCreatedBy(),
                news.getUpdatedBy()
        );
        dto.setStatus(news.getStatus());
        dto.setUnpublishAt(news.getUnpublishAt());
        dto.setDeletedAt(news.getDeletedAt());
        return dto;
    }

    public News updateNews(String lang, News news, NewsUpdateDto dto) {
        if (news == null || dto == null) {
            return news;
        }

        if (dto.getTitleUz() != null) news.setTitleUz(dto.getTitleUz());
        if (dto.getTitleEn() != null) news.setTitleEn(dto.getTitleEn());
        if (dto.getTitleRu() != null) news.setTitleRu(dto.getTitleRu());

        if (dto.getDescriptionUz() != null) news.setDescriptionUz(dto.getDescriptionUz());
        if (dto.getDescriptionEn() != null) news.setDescriptionEn(dto.getDescriptionEn());
        if (dto.getDescriptionRu() != null) news.setDescriptionRu(dto.getDescriptionRu());

        if (dto.getContentUz() != null) news.setContentUz(dto.getContentUz());
        if (dto.getContentEn() != null) news.setContentEn(dto.getContentEn());
        if (dto.getContentRu() != null) news.setContentRu(dto.getContentRu());

        if (dto.getRedirectUrl() != null) {
            news.setRedirectUrl(dto.getRedirectUrl());
        }

        if (dto.getPublishAt() != null) {
            news.setPublishedAt(dto.getPublishAt());
        }
        if (dto.getUnpublishAt() != null) {
            news.setUnpublishAt(dto.getUnpublishAt());
        }

        return news;
    }

    public List<NewsResponseDto> toList(String lang, List<News> newsList) {
        List<NewsResponseDto> newsResponseDtos = new ArrayList<>();
        newsList.forEach(news -> newsResponseDtos.add(toDto(news, lang)));
        return newsResponseDtos;
    }
}