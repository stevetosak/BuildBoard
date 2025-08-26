package com.db.finki.www.build_board.bb_users.types.repos;

import com.db.finki.www.build_board.bb_users.BBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BBUser, Integer> {
    Optional<BBUser> findByUsername(String username);

    BBUser findById(long id);

    // select * from user join developer_asso

    @Query("""
                SELECT d.developer
                FROM DeveloperAssociatedWithProject d
                WHERE d.project.id = :projectId
                AND d.endedAt IS NULL
            """)
    List<BBUser> findAllActiveDevelopersForProject(@Param("projectId") int projectId);

}
