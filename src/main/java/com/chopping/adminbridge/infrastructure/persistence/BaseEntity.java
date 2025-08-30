package com.chopping.adminbridge.infrastructure.persistence;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * 공통 엔티티 베이스 클래스
 * 모든 엔티티에서 공통으로 사용할 필드들을 정의
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    /**
     * 생성일시
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 수정일시
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 사용 여부 (Y: 사용, N: 미사용)
     */
    @Column(name = "use_yn", length = 1, nullable = false)
    private String useYn = "Y";

    /**
     * 삭제 여부 (Y: 삭제, N: 미삭제)
     */
    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn = "N";

    /**
     * 생성자 ID
     */
    @Column(name = "created_by", length = 50)
    private String createdBy;

    /**
     * 수정자 ID
     */
    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    /**
     * 엔티티가 활성 상태인지 확인
     */
    public boolean isActive() {
        return "Y".equals(useYn) && "N".equals(delYn);
    }

    /**
     * 엔티티를 비활성화
     */
    public void deactivate() {
        this.useYn = "N";
    }

    /**
     * 엔티티를 활성화
     */
    public void activate() {
        this.useYn = "Y";
    }

    /**
     * 엔티티를 삭제 상태로 변경 (논리적 삭제)
     */
    public void delete() {
        this.delYn = "Y";
        this.useYn = "N";
    }

    /**
     * 엔티티를 복구
     */
    public void restore() {
        this.delYn = "N";
        this.useYn = "Y";
    }

    /**
     * 엔티티가 삭제되었는지 확인
     */
    public boolean isDeleted() {
        return "Y".equals(delYn);
    }
}
