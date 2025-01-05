package com.db.finki.www.build_board.repository.threads;

import com.db.finki.www.build_board.entity.threads.BBThread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BBThreadRepository extends JpaRepository<BBThread, Long> {
}
