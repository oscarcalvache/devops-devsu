package devsu.devops.demo.service;

import org.springframework.stereotype.Service;


public interface HealthCheckService {
    public String checkConnectionToDB();
}
