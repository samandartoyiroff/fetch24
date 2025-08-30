package uz.tenzorsoft.fetch24.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.tenzorsoft.fetch24.domain.News;
import uz.tenzorsoft.fetch24.domain.NewsHistory;
import uz.tenzorsoft.fetch24.dto.request.NewsCreateDto;
import uz.tenzorsoft.fetch24.dto.request.NewsStatusUpdateDto;
import uz.tenzorsoft.fetch24.dto.request.NewsUpdateDto;
import uz.tenzorsoft.fetch24.dto.response.NewsHistoryResponseDto;
import uz.tenzorsoft.fetch24.dto.response.NewsResponseDto;
import uz.tenzorsoft.fetch24.exception.ResourceNotFoundException;
import uz.tenzorsoft.fetch24.mapper.NewsHistoryMapper;
import uz.tenzorsoft.fetch24.mapper.NewsMapper;
import uz.tenzorsoft.fetch24.repository.NewsHistoryRepository;
import uz.tenzorsoft.fetch24.repository.NewsRepository;
import uz.tenzorsoft.fetch24.repository.UserRepository;
import uz.tenzorsoft.fetch24.service.NewsService;
import uz.tenzorsoft.fetch24.model.NewsStatus;
import uz.tenzorsoft.fetch24.util.SecurityUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;
    private final NewsRepository newsRepository;
    private final NewsHistoryRepository newsHistoryRepository;
    private final UserRepository userRepository;
    private final NewsHistoryMapper newsHistoryMapper;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public ResponseEntity<?> create(NewsCreateDto newsCreateDto, String lang) {
        News news = newsMapper.toEntity(lang, newsCreateDto);
        News savedNews = newsRepository.save(news);

        saveNewsHistory(savedNews, null, NewsStatus.DRAFT, "Initial creation", SecurityUtils.getCurrentUserId().orElse(null));

        NewsResponseDto dto = newsMapper.toDto(savedNews, lang);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Override
    public ResponseEntity<?> findById(Long id, String lang) {
        News news = newsRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yangilik topilmadi"));

        NewsResponseDto newsResponseDto = newsMapper.toDto(news, lang);

        return ResponseEntity.ok(newsResponseDto);
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateById(Long id, String lang, NewsUpdateDto newsUpdateDto) {
        News existingNews = newsRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yangilik topilmadi"));

        String oldNewsJson = null;
        try {
            oldNewsJson = objectMapper.writeValueAsString(newsMapper.toDto(existingNews, lang));
        } catch (JsonProcessingException e) {
            // Log error
        }

        News updatedNews = newsMapper.updateNews(lang, existingNews, newsUpdateDto);
        News savedNews = newsRepository.save(updatedNews);

        String newNewsJson = null;
        try {
            newNewsJson = objectMapper.writeValueAsString(newsMapper.toDto(savedNews, lang));
        } catch (JsonProcessingException e) {
            // Log error
        }

        String diffJson = null;
        if (oldNewsJson != null && newNewsJson != null) {
            if (!oldNewsJson.equals(newNewsJson)) {
                diffJson = "Changes detected in " + lang + " content.";
            }
        }
        saveNewsHistory(savedNews, savedNews.getStatus(), savedNews.getStatus(), diffJson, SecurityUtils.getCurrentUserId().orElse(null));


        return ResponseEntity.ok(newsMapper.toDto(savedNews, lang));
    }

    @Override
    public ResponseEntity<?> findAllNotDeleted(String lang) {
        List<News> news = newsRepository.findAllByDeletedFalse();
        return ResponseEntity.ok(newsMapper.toList(lang, news));
    }

    @Override
    @Transactional
    public ResponseEntity<?> softDelete(Long id, String lang) {
        News news = newsRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yangilik topilmadi"));

        news.setDeleted(true);
        news.setDeletedAt(LocalDateTime.now());
        News savedNews = newsRepository.save(news);

        saveNewsHistory(savedNews, news.getStatus(), news.getStatus(), "News soft deleted", SecurityUtils.getCurrentUserId().orElse(null));

        return ResponseEntity.ok(newsMapper.toDto(savedNews, lang));
    }

    @Override
    public ResponseEntity<?> findAllDeleted(String lang) {
        List<News> news = newsRepository.findAllByDeletedTrue();
        return ResponseEntity.ok(newsMapper.toList(lang, news));
    }

    @Override
    @Transactional
    public ResponseEntity<?> hardDelete(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yangilik topilmadi"));

        newsHistoryRepository.deleteAll(newsHistoryRepository.findAllByNewsIdOrderByChangedAtDesc(id));

        newsRepository.deleteById(id);

        return ResponseEntity.ok("News deleted successfully!!!");
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateNewsStatus(Long id, NewsStatusUpdateDto statusUpdateDto) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yangilik topilmadi"));

        NewsStatus oldStatus = news.getStatus();
        NewsStatus newStatus = statusUpdateDto.getToStatus();

        if (oldStatus == newStatus) {
            return ResponseEntity.badRequest().body("Yangilik allaqachon '" + newStatus.name() + "' holatida.");
        }

        if (oldStatus == NewsStatus.DRAFT && newStatus == NewsStatus.PUBLISHED) {
            return ResponseEntity.badRequest().body("DRAFT holatidan to'g'ridan-to'g'ri PUBLISHED ga o'tish mumkin emas. Avval REVIEW holatiga o'tkazing.");
        }

        news.setStatus(newStatus);
        LocalDateTime now = LocalDateTime.now();

        if (newStatus == NewsStatus.PUBLISHED) {
            if (news.getPublishedAt() == null) {
                news.setPublishedAt(now);
            }
            } else if (newStatus == NewsStatus.UNPUBLISHED) {
            news.setUnpublishAt(now);
        }

        News savedNews = newsRepository.save(news);
        saveNewsHistory(savedNews, oldStatus, newStatus, "Status changed from " + oldStatus + " to " + newStatus, SecurityUtils.getCurrentUserId().orElse(null));

        return ResponseEntity.ok(newsMapper.toDto(savedNews, "en"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> restoreNews(Long id, String lang) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yangilik topilmadi"));

        if (!news.isDeleted()) {
            return ResponseEntity.badRequest().body("Yangilik allaqachon o'chirilmagan.");
        }

        news.setDeleted(false);
        news.setDeletedAt(null);
        News savedNews = newsRepository.save(news);

        saveNewsHistory(savedNews, savedNews.getStatus(), savedNews.getStatus(), "News restored from soft delete", SecurityUtils.getCurrentUserId().orElse(null));

        return ResponseEntity.ok(newsMapper.toDto(savedNews, lang));
    }

    @Override
    public ResponseEntity<?> getNewsHistory(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Yangilik topilmadi");
        }
        List<NewsHistory> historyList = newsHistoryRepository.findAllByNewsIdOrderByChangedAtDesc(id);
        List<NewsHistoryResponseDto> dtoList = newsHistoryMapper.toList(historyList);
        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity<?> findAllPublicNews(String lang) {
        List<News> publicNews = newsRepository.findAllPubliclyAvailable(LocalDateTime.now());
        return ResponseEntity.ok(newsMapper.toList(lang, publicNews));
    }

    private void saveNewsHistory(News news, NewsStatus fromStatus, NewsStatus toStatus, String diffJson, Long changedByUserId) {
        NewsHistory history = NewsHistory.builder()
                .news(news)
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .diffJson(diffJson)
                .changedAt(LocalDateTime.now())
                .build();

        if (changedByUserId != null) {
            userRepository.findById(changedByUserId).ifPresent(history::setChangedBy);
        }

        newsHistoryRepository.save(history);
    }
}