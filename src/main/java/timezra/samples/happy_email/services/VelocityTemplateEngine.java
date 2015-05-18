package timezra.samples.happy_email.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import timezra.samples.happy_email.models.Email;

@Service
class VelocityTemplateEngine implements TemplateEngine {

	@Resource
	VelocityEngine velocityEngine;

	@Override
	public String getText(final Email email) {
		final Map<String, Object> model = new HashMap<>();
		model.put("email", email);
		final String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine,
				"/templates/" + email.getType() + "/" + email.getLocale() + "/"
						+ "message.vm", "utf-8", model);
		return text;
	}

}
