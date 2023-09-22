package devsu.devops.demo.service;

import devsu.devops.demo.repository.HealthCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckServiceImpl implements HealthCheckService{

    private final HealthCheckRepository healthRepository;
    @Autowired
    public HealthCheckServiceImpl(HealthCheckRepository healthRepository) {
        this.healthRepository = healthRepository;
    }

    @Override
    public String checkConnectionToDB() {
        return healthRepository.checkConnectionToDB();
    }
}
