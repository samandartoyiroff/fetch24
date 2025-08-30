package uz.tenzorsoft.fetch24.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import uz.tenzorsoft.fetch24.model.NewsStatus;

import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Column(insertable = false)
    private Long updatedBy;

}
