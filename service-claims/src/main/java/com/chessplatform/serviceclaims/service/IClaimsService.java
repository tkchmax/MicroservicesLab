package com.chessplatform.serviceclaims.service;

import com.chessplatform.serviceclaims.repo.model.Report;

import java.util.List;

public interface IClaimsService {
    Report getReportByReportedUsername(String username);
    Report getReportBySenderUsername(String username);
    Report addReport(String msg, String reportedUsername, String senderUsername) throws Exception;
    List<Report> getReports();
    void deleteReportBySenderUsername();
    void deleteReportByReportedUsername();
    void deleteById(long id) throws Exception;
}
