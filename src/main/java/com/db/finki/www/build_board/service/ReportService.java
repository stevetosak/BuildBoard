package com.db.finki.www.build_board.service;

import com.db.finki.www.build_board.entity.entity_enum.FeedbackFor;
import com.db.finki.www.build_board.entity.entity_enum.Status;
import com.db.finki.www.build_board.entity.request.ProjectRequests;
import com.db.finki.www.build_board.entity.request.Report;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.ReportRepository;
import com.db.finki.www.build_board.service.request.FeedbackService;
import com.db.finki.www.build_board.service.thread.itf.TopicService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final TopicService topicService;
    private final UserDetailsService userDetailsService;
    private final FeedbackService feedbackService;

    public ReportService(
            ReportRepository reportRepository, TopicService topicService,
            UserDetailsService userDetailsService, FeedbackService feedbackService
                        ) {
        this.reportRepository = reportRepository;
        this.topicService = topicService;
        this.userDetailsService = userDetailsService;
        this.feedbackService = feedbackService;
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
    void accept(){
        //TODO: add vo blacklisted user
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
