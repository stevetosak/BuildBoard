package com.db.finki.www.build_board.repository;

import com.db.finki.www.build_board.entity.BBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BBUser, Long> {
    Optional<BBUser> findByUsername(String username);
}
