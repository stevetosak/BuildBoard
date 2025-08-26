package com.db.finki.www.build_board.bb_users.types.repos;

import com.db.finki.www.build_board.bb_users.types.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
    Developer findById(int id);
    Developer findByUsername(String username);
}
