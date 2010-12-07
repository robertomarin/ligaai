package ai.liga.user.service;

import org.junit.Test;

public class MailServiceTest {

	@Test
	public void deveEnviarEmail() throws Exception {
		new MailService().send("alexandrenavarro@gmail.com", "Hello JavaMail", "Welcome to JavaMail");
	}

}
