package com.db.finki.www.build_board.repository;

import com.db.finki.www.build_board.entity.user_type.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeratorRepository extends JpaRepository<Moderator,Integer> {
}
