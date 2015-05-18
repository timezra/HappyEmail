package timezra.samples.happy_email.services;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import timezra.samples.happy_email.models.Email;
import timezra.samples.happy_email.repository.EmailRepository;

public class EmailServiceImplTests {

	private EmailServiceImpl emailService;

	@Before
	public void setUp() {
		emailService = new EmailServiceImpl();
		emailService.emailer = mock(Emailer.class);
		emailService.emailRepository = mock(EmailRepository.class);
	}

	@Test
	public void itShouldSendTheEmail() {
		// Arrange
		final Email email = new Email();

		// Act
		emailService.send(email);

		// Assert
		verify(emailService.emailer).send(email);
	}

	@Test
	public void itShouldSaveTheEmail() {
		// Arrange
		final Email email = new Email();

		// Act
		emailService.send(email);

		// Assert
		verify(emailService.emailRepository).save(email);
	}

	@Test
	public void itShouldGiveTheEmailAnId() {
		// Arrange
		final Email email = new Email();

		// Act
		emailService.send(email);

		// Assert
		assertThat(email.getId(), not(is((UUID) null)));
	}

	@Test
	public void itShouldLookForTheEmailById() {
		// Arrange
		final UUID id = UUID.randomUUID();

		// Act
		emailService.find(id);

		// Assert
		verify(emailService.emailRepository).findOne(id);
	}

}
