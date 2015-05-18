package timezra.samples.happy_email.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import timezra.samples.happy_email.models.Email;

public interface EmailRepository extends CrudRepository<Email, UUID> {
}
