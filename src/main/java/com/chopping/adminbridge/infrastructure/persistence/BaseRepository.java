package com.chopping.adminbridge.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 공통 Repository 인터페이스
 * 모든 Repository에서 공통으로 사용할 메서드들을 정의
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    
    /**
     * 엔티티가 존재하는지 확인 (ID로)
     */
    default boolean existsById(ID id) {
        return findById(id).isPresent();
    }
    
    /**
     * 엔티티 개수 조회
     */
    default long countAll() {
        return count();
    }
    
    /**
     * 모든 엔티티 삭제
     */
    default void deleteAllEntities() {
        deleteAll();
    }
}
