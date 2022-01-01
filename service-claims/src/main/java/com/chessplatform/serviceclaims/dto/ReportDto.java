package com.chessplatform.serviceclaims.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {

    private Long id;
    private String reportedUsername;
    private String senderUsername;
    private String message;
    private String status;

    public String getReportedUsername() {
        return reportedUsername;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }


    public ReportDto(com.chessplatform.serviceclaims.repo.model.Report report)
    {
        this.reportedUsername = report.getReportedUsername();
        this.senderUsername = report.getSenderUsername();
        this.message = report.getMessage();
        this.id = report.getId();
        //this.status = report.getStatus();
    }
}
