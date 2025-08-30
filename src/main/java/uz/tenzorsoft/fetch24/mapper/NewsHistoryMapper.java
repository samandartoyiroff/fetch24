package uz.tenzorsoft.fetch24.mapper;

import org.springframework.stereotype.Component;
import uz.tenzorsoft.fetch24.domain.NewsHistory;
import uz.tenzorsoft.fetch24.dto.response.NewsHistoryResponseDto;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsHistoryMapper {

    public NewsHistoryResponseDto toDto(NewsHistory history) {
        NewsHistoryResponseDto dto = new NewsHistoryResponseDto();
        dto.setId(history.getId());
        dto.setNewsId(history.getNews().getId());
        dto.setChangedByUserId(history.getChangedBy() != null ? history.getChangedBy().getId() : null);
        dto.setChangedByUsername(history.getChangedBy() != null ? history.getChangedBy().getUsername() : "System");
        dto.setFromStatus(history.getFromStatus());
        dto.setToStatus(history.getToStatus());
        dto.setDiffJson(history.getDiffJson());
        dto.setChangedAt(history.getChangedAt());
        return dto;
    }

    public List<NewsHistoryResponseDto> toList(List<NewsHistory> historyList) {
        return historyList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}