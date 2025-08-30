package com.chopping.adminbridge.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.chopping.adminbridge.common.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

/**
 * 공통 서비스 베이스 클래스
 * 모든 서비스에서 공통으로 사용할 메서드들을 정의
 */
@Slf4j
public abstract class BaseService<T, ID> {

    protected final BaseRepository<T, ID> repository;

    protected BaseService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    /**
     * 엔티티 저장
     */
    @Transactional
    public T save(T entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            log.error("Failed to save entity: {}", e.getMessage(), e);
            throw new CustomException("SAVE_FAILED", "엔티티 저장에 실패했습니다.");
        }
    }

    /**
     * 엔티티 목록 저장
     */
    @Transactional
    public List<T> saveAll(List<T> entities) {
        try {
            return repository.saveAll(entities);
        } catch (Exception e) {
            log.error("Failed to save entities: {}", e.getMessage(), e);
            throw new CustomException("SAVE_ALL_FAILED", "엔티티 목록 저장에 실패했습니다.");
        }
    }

    /**
     * ID로 엔티티 조회
     */
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    /**
     * ID로 엔티티 조회 (존재하지 않으면 예외 발생)
     */
    @Transactional(readOnly = true)
    public T findByIdOrThrow(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException("ENTITY_NOT_FOUND", "엔티티를 찾을 수 없습니다. ID: " + id));
    }

    /**
     * 모든 엔티티 조회
     */
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    /**
     * 페이징된 엔티티 목록 조회
     */
    @Transactional(readOnly = true)
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * 엔티티 존재 여부 확인
     */
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    /**
     * 엔티티 개수 조회
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    /**
     * 엔티티 삭제
     */
    @Transactional
    public void deleteById(ID id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            log.error("Failed to delete entity by ID: {}", e.getMessage(), e);
            throw new CustomException("DELETE_FAILED", "엔티티 삭제에 실패했습니다.");
        }
    }

    /**
     * 엔티티 삭제
     */
    @Transactional
    public void delete(T entity) {
        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("Failed to delete entity: {}", e.getMessage(), e);
            throw new CustomException("DELETE_FAILED", "엔티티 삭제에 실패했습니다.");
        }
    }

    /**
     * 모든 엔티티 삭제
     */
    @Transactional
    public void deleteAll() {
        try {
            repository.deleteAll();
        } catch (Exception e) {
            log.error("Failed to delete all entities: {}", e.getMessage(), e);
            throw new CustomException("DELETE_ALL_FAILED", "모든 엔티티 삭제에 실패했습니다.");
        }
    }

    /**
     * 엔티티 목록 삭제
     */
    @Transactional
    public void deleteAll(List<T> entities) {
        try {
            repository.deleteAll(entities);
        } catch (Exception e) {
            log.error("Failed to delete entities: {}", e.getMessage(), e);
            throw new CustomException("DELETE_ENTITIES_FAILED", "엔티티 목록 삭제에 실패했습니다.");
        }
    }

    /**
     * 엔티티 업데이트
     */
    @Transactional
    public T update(T entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            log.error("Failed to update entity: {}", e.getMessage(), e);
            throw new CustomException("UPDATE_FAILED", "엔티티 업데이트에 실패했습니다.");
        }
    }

    /**
     * 배치 저장
     */
    @Transactional
    public List<T> saveAllBatch(List<T> entities) {
        try {
            return repository.saveAll(entities);
        } catch (Exception e) {
            log.error("Failed to save entities in batch: {}", e.getMessage(), e);
            throw new CustomException("BATCH_SAVE_FAILED", "배치 저장에 실패했습니다.");
        }
    }
}
