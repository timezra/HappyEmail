package timezra.samples.happy_email.models;

import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Email implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;

	private Recipient recipient;

	@Column
	private String type;

	@Column
	private Locale locale;

	private String body;

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(final Recipient recipient) {
		this.recipient = recipient;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public String getBody() {
		return body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

}
