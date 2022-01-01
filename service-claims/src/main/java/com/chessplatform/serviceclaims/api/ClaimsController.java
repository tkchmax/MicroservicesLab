package com.chessplatform.serviceclaims.api;

import com.chessplatform.serviceclaims.dto.ReportDto;
import com.chessplatform.serviceclaims.repo.model.Report;
import com.chessplatform.serviceclaims.service.ClaimsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ClaimsController {
    private final ClaimsService claimsService;

    @GetMapping("/t")
    public void t()

    {
        claimsService.test();
    }
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ReportDto reportDto)
    {
        try {
            claimsService.addReport(reportDto.getMessage(), reportDto.getReportedUsername(), reportDto.getSenderUsername());
            return ResponseEntity.created(URI.create("")).build();
        }catch(Exception ex)
        {
            return ResponseEntity.status(409).body(ex.getMessage());
        }
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<ReportDto>> getAll()
    {
        List<Report> reports = claimsService.getReports();
        List<ReportDto> reportsDto = new ArrayList<>();
        for(Report report : reports)
            reportsDto.add(new ReportDto(report));
        return ResponseEntity.ok(reportsDto);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteById(@RequestParam long id)
    {
        try
        {
            claimsService.deleteById(id);
        }catch(EntityNotFoundException ex)
        {
            return ResponseEntity.status(409).body(ex.getMessage());
        }
        return ResponseEntity.noContent().build();
    }



}
