package com.db.finki.www.build_board.service.request;

import com.db.finki.www.build_board.entity.entity_enum.FeedbackFor;
import com.db.finki.www.build_board.entity.entity_enum.Status;
import com.db.finki.www.build_board.entity.request.ProjectRequests;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.request.ProjectRequestRepo;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectRequestService {
    private final ProjectRequestRepo prReqRepo;
    private final FeedbackService feedbackService;

    public ProjectRequestService(ProjectRequestRepo prReqRepo, FeedbackService feedbackService) {
        this.prReqRepo = prReqRepo;
        this.feedbackService = feedbackService;
    }

    private ProjectRequests getRequestById(Integer id) {
        return prReqRepo.findById((long) id)
                        .get();
    }

    public void deny(Integer reqId, String desc, BBUser creator) {
        ProjectRequests prReq = getRequestById(reqId);
        prReq.setStatus(Status.DENIED);
        prReq.setFeedback(feedbackService.create(desc, creator, FeedbackFor.P, reqId));
        prReqRepo.save(prReq);
    }

    public void accept(BBUser creator, Integer reqId) {
        ProjectRequests prReq = getRequestById(reqId);
        prReq.setStatus(Status.ACCEPTED);
        prReq.setFeedback(feedbackService.create(creator, FeedbackFor.P, reqId));
        prReq.getProject()
             .getDevelopers()
             .add(prReq.getCreator());
        prReqRepo.save(prReq);
    }

    public List<ProjectRequests> getByStatusAndProjectAndLatest(Status status, Project project, String isLatest) {
        return prReqRepo.getLatestRequestByProjectAndStatus(
                project.getId(),
                status == null ? null : status.name(),
                isLatest
        );
    }

    public List<ProjectRequests> getByStatusAndUser(Status status, BBUser forUser) {
        if (status == null) {
            return prReqRepo.findByCreatorOrderByCreatedAtDesc(forUser);
        }
        return prReqRepo.findByStatusAndCreatorOrderByCreatedAtDesc(status, forUser);
    }

    public ProjectRequests createRequestFor(Project project, String reason, BBUser creator) {
        return prReqRepo.save(new ProjectRequests(project, creator, reason));
    }
}
