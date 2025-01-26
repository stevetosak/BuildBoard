package com.db.finki.www.build_board.service.request;

import com.db.finki.www.build_board.entity.enums.FeedbackFor;
import com.db.finki.www.build_board.entity.enums.Status;
import com.db.finki.www.build_board.entity.requests.ProjectRequests;
import com.db.finki.www.build_board.entity.threads.Project;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.repository.request.ProjectRequestRepo;
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
        return prReqRepo.findById((long)id).get() ;
    }

    public void deny(Integer reqId,String desc,BBUser creator){
        ProjectRequests prReq = getRequestById(reqId);
        prReq.setStatus(Status.DENIED);
        prReq.setFeedback(feedbackService.create(desc,creator,FeedbackFor.P,reqId));
        prReqRepo.save(prReq);
    }

    public void accept(String desc,BBUser creator,Integer reqId) {
        ProjectRequests prReq = getRequestById(reqId);
        prReq.setStatus(Status.ACCEPTED);
        prReq.setFeedback(feedbackService.create(desc,creator,FeedbackFor.P,reqId));
        prReq.getProject().getDevelopers().add(prReq.getCreator());
        prReqRepo.save(prReq);
    }

    public List<ProjectRequests> getByStatusAndProject(Status status,Project project) {
        if(status == null){
            return prReqRepo.findByProject(project);
        }
        return prReqRepo.findByStatusAndProject(status,project);
    }

    public List<ProjectRequests> getByStatusAndUser(Status status, BBUser forUser) {
        if(status == null){
            return prReqRepo.findByCreator(forUser);
        }
        return prReqRepo.findByStatusAndCreator(status,forUser);
    }

    public ProjectRequests createRequestFor(Project project, String reason, BBUser creator) {
        return prReqRepo.save(new ProjectRequests(project,creator,reason));
    }
}
