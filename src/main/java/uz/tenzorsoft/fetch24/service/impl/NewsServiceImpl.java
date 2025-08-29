package uz.tenzorsoft.fetch24.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.tenzorsoft.fetch24.domain.News;
import uz.tenzorsoft.fetch24.dto.request.NewsCreateDto;
import uz.tenzorsoft.fetch24.dto.request.NewsUpdateDto;
import uz.tenzorsoft.fetch24.dto.response.NewsResponseDto;
import uz.tenzorsoft.fetch24.exception.ResourceNotFoundException;
import uz.tenzorsoft.fetch24.mapper.NewsMapper;
import uz.tenzorsoft.fetch24.repository.NewsRepository;
import uz.tenzorsoft.fetch24.service.NewsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;

    private final NewsRepository newsRepository;
    @Override
    public ResponseEntity<?> create(NewsCreateDto newsCreateDto, String lang) {

        News news = newsMapper.toEntity(lang, newsCreateDto);

        News save = newsRepository.save(news);

        NewsResponseDto dto = newsMapper.toDto(save, lang);

        return ResponseEntity.ok(dto);

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


        News news = newsRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yangilik topilmadi"));

        News newNews = newsMapper.updateNews(lang, news, newsUpdateDto);

        News save = newsRepository.save(newNews);

        return ResponseEntity.ok(newsMapper.toDto(save, lang));

    }

    @Override
    public ResponseEntity<?> findAllNotDeleted(String lang) {

        List<News> news = newsRepository.findAllByDeletedFalse();

        return ResponseEntity.ok( newsMapper.toList(lang, news));

    }

    @Override
    @Transactional
    public ResponseEntity<?> softDelete(Long id, String lang) {

        News news = newsRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yangilik topilmadi"));

        news.setDeleted(true);

        newsRepository.save(news);

        return ResponseEntity.ok(newsMapper.toDto(news, lang));

    }

    @Override
    public ResponseEntity<?> findAllDeleted(String lang) {

        List<News> news = newsRepository.findAllByDeletedTrue();

        return ResponseEntity.ok(newsMapper.toList(lang, news));

    }

    @Override
    @Transactional
    public ResponseEntity<?> hardDelete(Long id) {

        newsRepository.deleteById(id);

        return ResponseEntity.ok("News deleted successfully!!!");

    }
}
