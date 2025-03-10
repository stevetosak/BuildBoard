package com.db.finki.www.build_board.repository.thread;

import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BBThreadRepository extends JpaRepository<BBThread, Long> {
    BBThread findById(long id);
}
