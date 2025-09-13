package com.db.finki.www.build_board.service;

import com.db.finki.www.build_board.entity.blacklisted_user.BlacklistedUser;
import com.db.finki.www.build_board.entity.entity_enum.FeedbackFor;
import com.db.finki.www.build_board.entity.entity_enum.Status;
import com.db.finki.www.build_board.entity.request.ProjectRequests;
import com.db.finki.www.build_board.entity.request.Report;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.entity.user_type.Moderator;
import com.db.finki.www.build_board.repository.BlacklistedUserRepo;
import com.db.finki.www.build_board.repository.ModeratorRepository;
import com.db.finki.www.build_board.repository.ReportRepository;
import com.db.finki.www.build_board.service.request.FeedbackService;
import com.db.finki.www.build_board.service.thread.itf.TopicService;
import org.apache.coyote.Request;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final TopicService topicService;
    private final UserDetailsService userDetailsService;
    private final FeedbackService feedbackService;
    private final BlacklistedUserRepo blacklistedUserRepo;
    private final ModeratorRepository moderatorRepository;


    public ReportService(
            ReportRepository reportRepository, TopicService topicService,
            UserDetailsService userDetailsService, FeedbackService feedbackService,
            BlacklistedUserRepo blacklistedUserRepo, ModeratorRepository moderatorRepository
                        ) {
        this.reportRepository = reportRepository;
        this.topicService = topicService;
        this.userDetailsService = userDetailsService;
        this.feedbackService = feedbackService;
        this.blacklistedUserRepo = blacklistedUserRepo;
        this.moderatorRepository = moderatorRepository;
    }

    public void createReport(
            long topicId, String reason, BBUser creator,
            String reportingUsername
                            ) {
        Topic topic = topicService.getById(topicId);
        BBUser reportedUser = (BBUser) userDetailsService.loadUserByUsername(reportingUsername);

        reportRepository.save(
                new Report(topic,
                        creator,
                        reason,
                        reportedUser)
                             );
    }

    public List<Report> getByStatusAndProjectAndLatest(
            Status status, Integer topicId,
            String isLatest
                                                      ) {
        return reportRepository.getLatestRequestByTopicAndStatus(
                topicId,
                status == null ? null : status.name(),
                isLatest
                                                                );
    }

    @Transactional
    public void accept(long reqId, String feedbackDesc, BBUser moderatorAsAUser) {
        Report reqForReqId =
                reportRepository
                        .findById(reqId)
                        .orElseThrow(() -> new RuntimeException("The id " +
                                "is invalid"));
        String reason = reqForReqId.getDescription();
        Moderator moderator =
                moderatorRepository
                        .findById(moderatorAsAUser.getId())
                        .orElseThrow(() -> new RuntimeException("The user is not a moderator"));

        reqForReqId.setStatus(Status.ACCEPTED);

        feedbackService.create(
                feedbackDesc,
                moderator,
                FeedbackFor.R,
                reqForReqId
                              );

        blacklistedUserRepo.save(
                new BlacklistedUser(
                        reqForReqId.getTopic(),
                        moderator,
                        LocalDateTime.now(),
                        reqForReqId.getUser(),
                        reason
                )
                                );
    }

    @Transactional
    public void deny(long reqId, String feedbackDesc, BBUser creator) {
        Report report =
                reportRepository
                        .findById(reqId)
                        .orElseThrow(() -> new RuntimeException("The " +
                                "report doesn't exist"));
        report.setStatus(Status.DENIED);

        feedbackService.create(
                feedbackDesc,
                creator,
                FeedbackFor.R,
                report);

        reportRepository.save(report);
    }
}
