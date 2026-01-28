package com.emailnotifier.repository;

import com.emailnotifier.entity.EmailMonitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmailMonitorRepository extends JpaRepository<EmailMonitor, Long> {
    List<EmailMonitor> findByActiveTrue();
}
