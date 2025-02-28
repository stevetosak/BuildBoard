package com.db.finki.www.build_board.repository.thread;

import com.db.finki.www.build_board.entity.thread.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    Optional<Tag> findByName(String name);
    long deleteAllByName(String name); 
}
