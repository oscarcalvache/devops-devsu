package devsu.devops.demo.repository;

import devsu.devops.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCheckRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT 'OK' FROM users",nativeQuery = true)
    String checkConnectionToDB();
}
