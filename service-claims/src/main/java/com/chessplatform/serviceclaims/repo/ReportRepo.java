package com.chessplatform.serviceclaims.repo;


import com.chessplatform.serviceclaims.repo.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<Report, Long> {
    Report findBySenderUsername(String username);
    Report findByReportedUsername(String username);
}
