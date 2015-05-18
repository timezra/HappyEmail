package timezra.samples.happy_email.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "timezra.samples.happy_email.PrimaryMailSender")
class PrimaryMailSender extends JavaMailSenderImpl {
}
