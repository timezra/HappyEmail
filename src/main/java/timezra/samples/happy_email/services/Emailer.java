package timezra.samples.happy_email.services;

import timezra.samples.happy_email.models.Email;

public interface Emailer {
	void send(Email email);
}
