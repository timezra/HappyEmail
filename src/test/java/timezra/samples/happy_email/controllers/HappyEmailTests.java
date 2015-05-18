package timezra.samples.happy_email.controllers;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Locale;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import timezra.samples.happy_email.models.Email;
import timezra.samples.happy_email.models.Recipient;
import timezra.samples.happy_email.services.EmailService;
import timezra.samples.happy_email.services.TemplateEngine;

public class HappyEmailTests {

	private HappyEmail controller;

	@Before
	public void setUp() {
		controller = new HappyEmail();
		controller.emailService = mock(EmailService.class);
		controller.templateEngine = mock(TemplateEngine.class);
	}

	@Test
	public void itShouldSendAnEmail() {
		// Act
		controller.createEmail(null, null, null);

		// Assert
		verify(controller.emailService).send(refEq(new Email()));
	}

	@Test
	public void itShouldSetTheEmailRecipient() {
		// Arrange
		final Recipient recipient = new Recipient();
		final Email expectedEmail = new Email();
		expectedEmail.setRecipient(recipient);

		// Act
		controller.createEmail(null, recipient, null);

		// Assert
		verify(controller.emailService).send(refEq(expectedEmail));
	}

	@Test
	public void itShouldSetTheEmailType() {
		// Arrange
		final String type = "test type";
		final Email expectedEmail = new Email();
		expectedEmail.setType(type);

		// Act
		controller.createEmail(type, null, null);

		// Assert
		verify(controller.emailService).send(refEq(expectedEmail));
	}

	@Test
	public void itShouldSetTheLocale() {
		// Arrange
		final Locale locale = Locale.CHINESE;
		final Email expectedEmail = new Email();
		expectedEmail.setLocale(locale);

		// Act
		controller.createEmail(null, null, locale);

		// Assert
		verify(controller.emailService).send(refEq(expectedEmail));
	}

	@Test
	public void itShouldSetTheEmailBody() {
		// Arrange
		final String text = "test text";
		final Email expectedEmail = new Email();
		expectedEmail.setBody(text);
		when(controller.templateEngine.getText(any(Email.class))).thenReturn(
				text);

		// Act
		controller.createEmail(null, null, null);

		// Assert
		verify(controller.emailService).send(refEq(expectedEmail));
	}

	@Test
	public void itShouldReturnAnIdForTheEmail() {
		// Arrange
		final UUID expectedUuid = UUID.randomUUID();
		when(controller.emailService.send(any(Email.class))).thenReturn(
				expectedUuid);

		// Act
		final ResponseEntity<UUID> response = controller.createEmail(null,
				null, null);

		// Assert
		assertThat(response.getBody(), is(expectedUuid));
	}

	@Test
	public void itShouldSetTheLocationHeader() {
		// Arrange
		final UUID expectedUuid = UUID.randomUUID();
		when(controller.emailService.send(any(Email.class))).thenReturn(
				expectedUuid);

		// Act
		final ResponseEntity<UUID> response = controller.createEmail(null,
				null, null);

		// Assert
		assertThat(response.getHeaders().get("Location"), contains("/email/"
				+ expectedUuid));
	}

	@Test
	public void itShouldReturnACreatedStatus() {
		// Arrange
		final UUID expectedUuid = UUID.randomUUID();
		when(controller.emailService.send(any(Email.class))).thenReturn(
				expectedUuid);

		// Act
		final ResponseEntity<UUID> response = controller.createEmail(null,
				null, null);

		// Assert
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
	}

	@Test
	public void itShouldReturnTheFoundEmail() {
		// Arrange
		final UUID id = UUID.randomUUID();
		final Email email = new Email();
		when(controller.emailService.find(id)).thenReturn(email);

		// Act
		final ResponseEntity<Email> response = controller.findEmail(id);

		// Assert
		assertThat(response.getBody(), is(email));
	}

	@Test
	public void itShouldIndicateThatTheEmailCouldNotBeFound() {
		// Arrange
		when(controller.emailService.find(any(UUID.class))).thenReturn(null);

		// Act
		final ResponseEntity<Email> response = controller.findEmail(null);

		// Assert
		assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}
}
