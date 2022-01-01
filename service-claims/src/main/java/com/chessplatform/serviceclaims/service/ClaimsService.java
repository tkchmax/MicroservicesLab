package com.chessplatform.serviceclaims.service;

import com.chessplatform.serviceclaims.dto.UserDto;
import com.chessplatform.serviceclaims.repo.ReportRepo;
import com.chessplatform.serviceclaims.repo.model.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClaimsService implements IClaimsService{
    private final ReportRepo reportRepo;

    public void test()
    {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<UserDto[]> usersArray = restTemplate.getForEntity("http://localhost:8080/user/get_by_name", UserDto[].class);
            UserDto[] s = usersArray.getBody();
        }catch(Exception ex)
        {}


/*        HttpHeaders headers = new HttpHeaders();


        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<User> responseEntity = restTemplate
                .exchange("http://localhost:8080/user/show?username=user2", HttpMethod.GET, entity, User.class);
        int a=0;*/

    }

    @Override
    public Report getReportByReportedUsername(String username) {
        return null;
    }

    @Override
    public Report getReportBySenderUsername(String username) {
        return null;
    }

    boolean isUserExist(String username)
    {
        RestTemplate restTemplate = new RestTemplate();
        try
        {
            String usernameUrl = "http://192.168.189.50:30001/user/get_by_name?username="+username;
            ResponseEntity<UserDto> maybeUser = restTemplate.getForEntity(usernameUrl, UserDto.class);

        }catch (Exception ex)
        {
            log.error("Reported user {} does not exist!", username);
            return false;
        }
        return true;
    }

    @Override
    public Report addReport(String message, String reportedUsername, String senderUsername) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        if(!isUserExist(reportedUsername))
            throw new Exception("ADD REPORT: reportedUser does not exist!");
        if(!isUserExist(senderUsername))
            throw new Exception("ADD REPORT: senderUser does not exist!");

        Report report = new Report(null, reportedUsername, senderUsername, message);
        reportRepo.save(report);
        log.info("User '{}' reported '{}'", senderUsername, reportedUsername);
        return report;
    }

    @Override
    public void deleteReportBySenderUsername() {

    }

    @Override
    public void deleteReportByReportedUsername() {

    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException {
        Optional<Report> report = reportRepo.findById(id);

        if(!report.isPresent()) {
            log.error("DELETE: report with id '" + id + "' does not exist!");
            throw new EntityNotFoundException("report id "+id+" does not exsist!");
        }
        reportRepo.delete(report.get());
        log.info("DELETE: report with id '" + id + "' deleted");
    }

    @Override
    public List<Report> getReports() {
        return reportRepo.findAll();
    }
}
