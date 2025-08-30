package uz.tenzorsoft.fetch24.domain;

import jakarta.persistence.*;
import lombok.*;
import uz.tenzorsoft.fetch24.model.NewsStatus;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "news_history")
public class NewsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by")
    private User changedBy;

    private NewsStatus fromStatus;

    private NewsStatus toStatus;

    private String diffJson;

    private LocalDateTime changedAt;

    protected void onCreate() {
        changedAt = LocalDateTime.now();
    }
}