package com.db.finki.www.build_board.service.request;

import com.db.finki.www.build_board.entity.entity_enum.FeedbackFor;
import com.db.finki.www.build_board.entity.entity_enum.Status;
import com.db.finki.www.build_board.entity.request.ProjectRequests;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.request.ProjectRequestRepo;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectRequestService {
    private final ProjectRequestRepo prReqRepo;
    private final FeedbackService feedbackService;
    private final ProjectService projectService;


    public ProjectRequestService(ProjectRequestRepo prReqRepo, FeedbackService feedbackService,ProjectService projectService) {
        this.prReqRepo = prReqRepo;
        this.feedbackService = feedbackService;
        this.projectService = projectService;
    }

    private ProjectRequests getRequestById(Integer id) {
        return prReqRepo.findById((long) id)
                .get();
    }

    @Transactional
    public void deny(Integer reqId, String desc, BBUser creator) {
        ProjectRequests prReq = getRequestById(reqId);
        prReq.setStatus(Status.DENIED);
        feedbackService.create(desc,creator,FeedbackFor.P,prReq);
        prReqRepo.save(prReq);
    }

    @Transactional
    public void accept(BBUser creator, Integer reqId) {
        ProjectRequests prReq = getRequestById(reqId);
        prReq.setStatus(Status.ACCEPTED);

        feedbackService.create(creator,FeedbackFor.P,prReq);
        projectService.addDeveloperToProject(prReq.getProject(), prReq.getCreator());
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
