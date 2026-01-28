package com.emailnotifier.service;

import com.emailnotifier.dto.EmailMonitorRequest;
import com.emailnotifier.dto.EmailMonitorResponse;
import com.emailnotifier.entity.EmailMonitor;
import com.emailnotifier.repository.EmailMonitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailMonitorServiceTest {

    @Mock
    private EmailMonitorRepository repository;

    @InjectMocks
    private EmailMonitorService service;

    private EmailMonitorRequest request;
    private EmailMonitor monitor;

    @BeforeEach
    void setUp() {
        request = new EmailMonitorRequest();
        request.setName("Test Monitor");
        request.setMonitoredEmail("test@example.com");
        request.setSenderFilter("sender@example.com");
        request.setWhatsappNumber("5511999999999");

        monitor = new EmailMonitor();
        monitor.setId(1L);
        monitor.setName(request.getName());
        monitor.setMonitoredEmail(request.getMonitoredEmail());
        monitor.setSenderFilter(request.getSenderFilter());
        monitor.setWhatsappNumber(request.getWhatsappNumber());
        monitor.setActive(true);
    }

    @Test
    void shouldCreateMonitor() {
        when(repository.save(any(EmailMonitor.class))).thenReturn(monitor);

        EmailMonitorResponse response = service.create(request);

        assertNotNull(response);
        assertEquals("Test Monitor", response.getName());
        assertEquals("test@example.com", response.getMonitoredEmail());
        verify(repository, times(1)).save(any(EmailMonitor.class));
    }

    @Test
    void shouldFindMonitorById() {
        when(repository.findById(1L)).thenReturn(Optional.of(monitor));

        EmailMonitorResponse response = service.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Monitor", response.getName());
    }

    @Test
    void shouldToggleMonitorActive() {
        when(repository.findById(1L)).thenReturn(Optional.of(monitor));
        when(repository.save(any(EmailMonitor.class))).thenReturn(monitor);

        EmailMonitorResponse response = service.toggleActive(1L);

        assertNotNull(response);
        verify(repository, times(1)).save(any(EmailMonitor.class));
    }

    @Test
    void shouldThrowExceptionWhenMonitorNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.findById(999L));
    }
}
