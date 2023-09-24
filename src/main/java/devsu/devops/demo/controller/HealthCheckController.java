package devsu.devops.demo.controller;

import devsu.devops.demo.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class HealthCheckController {
    private final HealthCheckService healthCheckService;
    @Autowired
    public HealthCheckController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }
    @GetMapping
    public ResponseEntity<String> healtCheck(){
        String check = healthCheckService.checkConnectionToDB();
        return new ResponseEntity<>(check, HttpStatus.OK);
    }
}
