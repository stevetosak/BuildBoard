package com.db.finki.www.build_board.repository.request;

import com.db.finki.www.build_board.entity.request.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
}
