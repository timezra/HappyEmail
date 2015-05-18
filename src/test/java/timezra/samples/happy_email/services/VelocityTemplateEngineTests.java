package timezra.samples.happy_email.services;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Locale;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;

import timezra.samples.happy_email.models.Email;
import timezra.samples.happy_email.models.Recipient;

public class VelocityTemplateEngineTests {

	private VelocityTemplateEngine velocityTemplateEngine;

	@Before
	public void setUp() {
		velocityTemplateEngine = new VelocityTemplateEngine();
		final Properties properties = new Properties();
		properties.setProperty("resource.loader", "class");
		properties
				.setProperty("class.resource.loader.class",
						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityTemplateEngine.velocityEngine = new VelocityEngine(properties);
	}

	@Test
	public void itShouldConfigureTheTemplateFromTheClasspath() {
		// Arrange
		final Email email = new Email();
		email.setType("birthday");
		email.setLocale(new Locale("en", "US"));
		final Recipient recipient = new Recipient();
		recipient.setFirstName("first");
		recipient.setLastName("last");
		email.setRecipient(recipient);

		// Act
		final String text = velocityTemplateEngine.getText(email);

		// Assert
		assertThat(
				text.replaceAll("\\n\\s*", ""),
				is("<html><body><h3>Happy birthday, first last!</h3></body></html>"));
	}

}
