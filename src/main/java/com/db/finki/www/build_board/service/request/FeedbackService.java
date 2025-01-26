package com.db.finki.www.build_board.service.request;

import com.db.finki.www.build_board.entity.enums.FeedbackFor;
import com.db.finki.www.build_board.entity.requests.Feedback;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.repository.request.FeedbackRepo;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    private final FeedbackRepo feedbackRepo;

    public FeedbackService(FeedbackRepo feedbackRepo) {this.feedbackRepo = feedbackRepo;}

    public Feedback create(String description, BBUser creator, FeedbackFor feedbackFor, Integer reqId){
        return feedbackRepo.save(new Feedback(feedbackFor, creator, description,reqId));
    }
}
