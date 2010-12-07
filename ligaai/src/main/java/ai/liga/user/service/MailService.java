package ai.liga.user.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class MailService {

	private static final String FROM = "liga@liga.ai";

	private static final String SERVIDOR_SMTP = "mail.liga.ai";

	public void send(String to, String subject, String message) throws MailServiceException, AddressException,
			MessagingException {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", SERVIDOR_SMTP);

		Session session = Session.getDefaultInstance(props, null);

		MimeMessage messageM = new MimeMessage(session);
		messageM.setFrom(new InternetAddress(FROM));
		messageM.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		messageM.setSubject(subject);
		messageM.setText(message);
		
		Transport.send(messageM);

	}

}
