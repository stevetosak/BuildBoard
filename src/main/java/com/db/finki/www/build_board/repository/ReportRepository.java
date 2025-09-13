package com.db.finki.www.build_board.repository;

import com.db.finki.www.build_board.entity.request.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
