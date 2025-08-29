package uz.tenzorsoft.fetch24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tenzorsoft.fetch24.domain.News;


import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByIdAndDeletedFalse(Long id);

    List<News> findAllByDeletedFalse();


    List<News> findAllByDeletedTrue();

}

