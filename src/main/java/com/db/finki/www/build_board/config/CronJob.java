package com.db.finki.www.build_board.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronJob {

    @PersistenceContext
    private EntityManager em;

    //    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    @Scheduled(cron = "0 0 0 1 * ?")
    public void markRejectedPendingPrRequests() {
        em
                .createNativeQuery("CALL mark_denied_pr_requests_older_than_1month();")
                .executeUpdate();
    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 * ?")
    public void markRejectedPendingReports() {
        em
                .createNativeQuery("CALL mark_denied_reports_older_than_1month();")
                .executeUpdate();
    }
}
