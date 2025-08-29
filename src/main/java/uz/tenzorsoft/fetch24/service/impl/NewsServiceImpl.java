package uz.tenzorsoft.fetch24.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.tenzorsoft.fetch24.domain.News;
import uz.tenzorsoft.fetch24.dto.request.NewsCreateDto;
import uz.tenzorsoft.fetch24.dto.request.NewsUpdateDto;
import uz.tenzorsoft.fetch24.dto.response.NewsResponseDto;
import uz.tenzorsoft.fetch24.exception.ResourceNotFoundException;
import uz.tenzorsoft.fetch24.mapper.NewsMapper;
import uz.tenzorsoft.fetch24.repository.NewsRepository;
import uz.tenzorsoft.fetch24.service.FileService;
import uz.tenzorsoft.fetch24.service.NewsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;

    private final NewsRepository newsRepository;

    private final FileService fileService;
    @Override
    public ResponseEntity<?> create(NewsCreateDto newsCreateDto, String lang, MultipartFile headImage) {

        News news = newsMapper.toEntity(lang, newsCreateDto, headImage);

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
    public ResponseEntity<?> updateById(Long id, String lang, NewsUpdateDto newsUpdateDto, MultipartFile headImage) {


        News news = newsRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yangilik topilmadi"));

        News newNews = newsMapper.updateNews(lang, news, newsUpdateDto, headImage);

        News save = newsRepository.save(newNews);

        return ResponseEntity.ok(newsMapper.toDto(save, lang));

    }

    @Override
    public ResponseEntity<?> findAllNotDeleted(String lang, Integer page, Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<News> news = newsRepository.findAllByDeletedFalse(pageRequest);

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
    public ResponseEntity<?> findAllDeleted(String lang, Integer page, Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<News> news = newsRepository.findAllByDeletedTrue(pageRequest);

        return ResponseEntity.ok(newsMapper.toList(lang, news));

    }

    @Override
    @Transactional
    public ResponseEntity<?> hardDelete(Long id) {

        newsRepository.deleteById(id);

        return ResponseEntity.ok("News deleted successfully!!!");

    }
}
