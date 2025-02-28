package com.db.finki.www.build_board.service.request;

import com.db.finki.www.build_board.entity.entity_enum.FeedbackFor;
import com.db.finki.www.build_board.entity.request.Feedback;
import com.db.finki.www.build_board.entity.request.Submission;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.request.FeedbackRepo;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    private final FeedbackRepo feedbackRepo;

    public FeedbackService(FeedbackRepo feedbackRepo) {this.feedbackRepo = feedbackRepo;}

    public Feedback create(String description, BBUser creator, FeedbackFor feedbackFor, Submission submission) {
        return feedbackRepo.save(new Feedback(feedbackFor, creator, description,submission));
    }
    public Feedback create(BBUser creator, FeedbackFor feedbackFor, Submission sub){
        return feedbackRepo.save(new Feedback(feedbackFor, creator,sub));
    }
}
