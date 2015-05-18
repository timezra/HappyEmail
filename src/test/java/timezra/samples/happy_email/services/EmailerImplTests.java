package timezra.samples.happy_email.services;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import timezra.samples.happy_email.models.Email;
import timezra.samples.happy_email.models.Recipient;

import com.sun.mail.smtp.SMTPMessage;

public class EmailerImplTests {

	private EmailerImpl emailer;

	@Captor
	ArgumentCaptor<MimeMessagePreparator> captor;

	@Before
	public void setUp() {
		emailer = new EmailerImpl();
		emailer.primaryMailSender = mock(JavaMailSender.class);
		emailer.secondaryMailSender = mock(JavaMailSender.class);

		initMocks(this);
	}

	@Test
	public void itShouldSendAMessageToThePrimaryEmailer() {
		// Act
		emailer.send(new Email());

		// Assert
		verify(emailer.primaryMailSender).send(
				notNull(MimeMessagePreparator.class));
		verify(emailer.secondaryMailSender, never()).send(
				any(MimeMessagePreparator.class));
	}

	@Test
	public void itShouldSendAMessageToTheSecondaryEmailer() {
		// Arrange
		doThrow(MailSendException.class).when(emailer.primaryMailSender).send(
				any(MimeMessagePreparator.class));

		// Act
		emailer.send(new Email());

		// Assert
		verify(emailer.primaryMailSender).send(
				notNull(MimeMessagePreparator.class));
		verify(emailer.secondaryMailSender).send(
				notNull(MimeMessagePreparator.class));
	}

	@Test
	public void itShouldSetTheEmailAddress() throws Exception {
		// Arrange
		emailer.from = "from@test.com";
		final Email email = new Email();
		email.setBody("test body");
		final Recipient recipient = new Recipient();
		recipient.setEmailAddress("to@test.com");
		email.setRecipient(recipient);
		emailer.send(email);
		verify(emailer.primaryMailSender).send(captor.capture());
		final MimeMessagePreparator preparator = captor.getValue();
		final SMTPMessage mimeMessage = new SMTPMessage(
				Session.getDefaultInstance(new Properties()));

		// Act
		preparator.prepare(mimeMessage);

		// Assert
		assertThat(asList(mimeMessage.getRecipients(Message.RecipientType.TO))
				.stream().map(a -> a.toString()).toArray(),
				arrayContaining(recipient.getEmailAddress()));
	}

	@Test
	public void itShouldSetTheSenderAddress() throws Exception {
		// Arrange
		emailer.from = "from@test.com";
		final Email email = new Email();
		email.setBody("test body");
		final Recipient recipient = new Recipient();
		recipient.setEmailAddress("to@test.com");
		email.setRecipient(recipient);
		emailer.send(email);
		verify(emailer.primaryMailSender).send(captor.capture());
		final MimeMessagePreparator preparator = captor.getValue();
		final SMTPMessage mimeMessage = new SMTPMessage(
				Session.getDefaultInstance(new Properties()));

		// Act
		preparator.prepare(mimeMessage);

		// Assert
		assertThat(mimeMessage.getHeader("From"), arrayContaining(emailer.from));
	}

	@Test
	public void itShouldSetTheTextBody() throws Exception {
		// Arrange
		emailer.from = "from@test.com";
		final Email email = new Email();
		email.setBody("test body");
		final Recipient recipient = new Recipient();
		recipient.setEmailAddress("to@test.com");
		email.setRecipient(recipient);
		emailer.send(email);
		verify(emailer.primaryMailSender).send(captor.capture());
		final MimeMessagePreparator preparator = captor.getValue();
		final SMTPMessage mimeMessage = new SMTPMessage(
				Session.getDefaultInstance(new Properties()));

		// Act
		preparator.prepare(mimeMessage);

		// Assert
		assertThat(mimeMessage.getContent(), is(email.getBody()));
	}
}
