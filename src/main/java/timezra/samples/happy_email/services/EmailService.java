package timezra.samples.happy_email.services;

import java.util.UUID;

import timezra.samples.happy_email.models.Email;

public interface EmailService {
	UUID send(Email email);

	Email find(UUID id);
}
