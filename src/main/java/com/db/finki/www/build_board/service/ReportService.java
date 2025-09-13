package com.db.finki.www.build_board.service;

import com.db.finki.www.build_board.entity.request.Report;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.ReportRepository;
import com.db.finki.www.build_board.service.thread.itf.TopicService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final TopicService topicService;
    private final UserDetailsService userDetailsService;

    public ReportService(ReportRepository reportRepository, TopicService topicService, UserDetailsService userDetailsService) {this.reportRepository = reportRepository;
        this.topicService = topicService;
        this.userDetailsService = userDetailsService;
    }

    public void createReport(long topicId,String reason, BBUser creator, String reportingUsername)
    {
        Topic topic = topicService.getById(topicId);
        BBUser reportedUser = (BBUser) userDetailsService.loadUserByUsername(reportingUsername);

        reportRepository.save(
                new Report(topic,creator, reason, reportedUser)
                             );
    }
}
