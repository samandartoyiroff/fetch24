package uz.tenzorsoft.fetch24.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class News extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titleUz;

    private String titleRu;

    private String titleEn;

    private String descriptionUz;

    private String descriptionRu;

    private String descriptionEn;

    private String contentUz;

    private String contentRu;

    private String contentEn;

    private String redirectUrl;

    private LocalDateTime publishedAt;

    private boolean deleted=false;



}
