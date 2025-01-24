package com.db.finki.www.build_board.repository;

import com.db.finki.www.build_board.entity.user_types.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
    Developer findById(int id);
}
