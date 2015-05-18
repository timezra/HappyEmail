package timezra.samples.happy_email.services;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import timezra.samples.happy_email.models.Email;
import timezra.samples.happy_email.repository.EmailRepository;

@Service
class EmailServiceImpl implements EmailService {

	@Resource
	Emailer emailer;

	@Resource
	EmailRepository emailRepository;

	@Override
	public UUID send(final Email email) {
		emailer.send(email);
		final UUID id = UUID.randomUUID();
		email.setId(id);
		emailRepository.save(email);
		return id;
	}

	@Override
	public Email find(final UUID id) {
		return emailRepository.findOne(id);
	}
}
