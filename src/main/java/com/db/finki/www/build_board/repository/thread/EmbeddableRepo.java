package com.db.finki.www.build_board.repository.thread;

import com.db.finki.www.build_board.entity.thread.EmbeddableThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmbeddableRepo extends JpaRepository<EmbeddableThread,Long> {
}
