package uz.tenzorsoft.fetch24.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.tenzorsoft.fetch24.model.NewsStatus;
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

    @Column(columnDefinition = "text")
    private String headImage;

    @Column(columnDefinition = "text")
    private String titleUz;

    @Column(columnDefinition = "text")
    private String titleRu;

    @Column(columnDefinition = "text")
    private String titleEn;

    @Column(columnDefinition = "text")
    private String descriptionUz;

    @Column(columnDefinition = "text")
    private String descriptionRu;

    @Column(columnDefinition = "text")
    private String descriptionEn;

    @Column(columnDefinition = "text")
    private String contentUz;

    @Column(columnDefinition = "text")
    private String contentRu;

    @Column(columnDefinition = "text")
    private String contentEn;

    private String redirectUrl;

    private LocalDateTime publishedAt;

    private boolean deleted=false;

    @Enumerated(EnumType.STRING)
    private NewsStatus status = NewsStatus.DRAFT;

    private LocalDateTime unpublishAt;

    private LocalDateTime deletedAt;

}
