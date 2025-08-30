package uz.tenzorsoft.fetch24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tenzorsoft.fetch24.domain.NewsHistory;
import java.util.List;

@Repository
public interface NewsHistoryRepository extends JpaRepository<NewsHistory, Long> {
    List<NewsHistory> findAllByNewsIdOrderByChangedAtDesc(Long newsId);
}