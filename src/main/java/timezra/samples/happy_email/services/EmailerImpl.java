package timezra.samples.happy_email.services;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Repository;

import timezra.samples.happy_email.models.Email;

@Repository
class EmailerImpl implements Emailer {

	@Resource
	JavaMailSender primaryMailSender;

	@Resource
	JavaMailSender secondaryMailSender;

	@Value("timezra.samples.happy_email.Emailer.from")
	String from;

	@Override
	public void send(final Email email) {
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(final MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(
						mimeMessage);
				message.setTo(email.getRecipient().getEmailAddress());
				message.setFrom(from);
				message.setText(email.getBody(), true);
			}
		};
		try {
			primaryMailSender.send(preparator);
		} catch (final MailException ex) {
			ex.printStackTrace();
			secondaryMailSender.send(preparator);
		}
	}
}
