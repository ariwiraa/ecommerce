package com.personal.ecommerce.repository;

import com.personal.ecommerce.model.entity.RoleEntity;
import com.personal.ecommerce.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);

    @Query(value = """
            SELECT r.* FROM roles r
            JOIN user_role ur ON r.role_id = ur.role_id
            WHERE ur.user_id = :userId
            """, nativeQuery = true)
    List<RoleEntity> findByUserId(@Param("userId") Long userId);
}
