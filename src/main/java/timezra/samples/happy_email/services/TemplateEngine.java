package timezra.samples.happy_email.services;

import timezra.samples.happy_email.models.Email;

public interface TemplateEngine {
	String getText(Email email);
}
