package com.librarymanagement.library.services.schedulingService;

import com.librarymanagement.library.services.emailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulingService {
    @Autowired
    EmailService emailService;

}
