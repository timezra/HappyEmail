package timezra.samples.happy_email.controllers;

import java.util.Locale;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import timezra.samples.happy_email.models.Email;
import timezra.samples.happy_email.models.Recipient;
import timezra.samples.happy_email.services.EmailService;
import timezra.samples.happy_email.services.TemplateEngine;

@RestController
@RequestMapping("/email")
public class HappyEmail {

	@Resource
	EmailService emailService;

	@Resource
	TemplateEngine templateEngine;

	@RequestMapping(value = "/{type}", method = RequestMethod.POST)
	@ResponseBody
	ResponseEntity<UUID> createEmail(
			@PathVariable("type") final String type,
			@RequestBody final Recipient recipient,
			@RequestParam(defaultValue = "en_US", value = "locale") final Locale locale) {
		final Email email = new Email();
		email.setRecipient(recipient);
		email.setType(type);
		email.setLocale(locale);
		email.setBody(templateEngine.getText(email));
		final UUID id = emailService.send(email);
		final MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add("Location", "/email/" + id);
		final ResponseEntity<UUID> response = new ResponseEntity<UUID>(id,
				headers, HttpStatus.CREATED);
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	ResponseEntity<Email> findEmail(@PathVariable("id") final UUID id) {
		final Email found = emailService.find(id);
		if (found == null) {
			return new ResponseEntity<Email>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Email>(found, HttpStatus.OK);
	}
}
