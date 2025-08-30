package uz.tenzorsoft.fetch24.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.tenzorsoft.fetch24.domain.News;
import uz.tenzorsoft.fetch24.model.NewsStatus;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByIdAndDeletedFalse(Long id);

    List<News> findAllByDeletedFalse(PageRequest pageRequest);


    List<News> findAllByDeletedTrue(PageRequest pageRequest);

    Optional<News> findById(Long id);

    @Query("SELECT n FROM News n WHERE n.status = 'PUBLISHED' AND n.deleted = FALSE AND " +
            "(n.publishedAt <= :now OR n.publishedAt IS NULL) AND " +
            "(n.unpublishAt >= :now OR n.unpublishAt IS NULL)")
    List<News> findAllPubliclyAvailable(@Param("now") LocalDateTime now);
}

