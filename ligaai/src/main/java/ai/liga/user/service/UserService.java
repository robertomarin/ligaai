package ai.liga.user.service;

import java.util.Calendar;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.liga.dao.HibernateDAOFactory;
import ai.liga.user.dao.UserDao;
import ai.liga.user.model.User;

@Service
public class UserService {

	private final UserDao userDao;

	private final MailService mailService;

	@Autowired
	public UserService(HibernateDAOFactory factory, final MailService mailService) {
		this.mailService = mailService;
		userDao = factory.getUserDao();
	}

	public boolean exists(User user) {
		return userDao.findByEmail(user) != null;
	}

	public User load(Long id) {
		return userDao.load(id);
	}

	public User save(User user) {
		user.setCreated(Calendar.getInstance());
		user.setPassword(encriptPassword(user.getPassword()));
		user = userDao.merge(user);
		return user;
	}

	public User merge(User user) {
		return userDao.merge(user);
	}

	public User login(User user) {
		user.setPassword(encriptPassword(user.getPassword()));
		return userDao.login(user);
	}

	public boolean giveAvatar(User user) {
		user = userDao.load(user.getId());
		user.setAvatar(true);
		return user != null;
	}

	public void recoverPassword(User user, String subject, String message) throws UserNotFoundException, MailServiceException, AddressException, MessagingException {
		User userBd = userDao.findByEmail(user);

		if (userBd == null) {
			throw new UserNotFoundException("E-mail não cadastrado");
		}

		mailService.send(user.getEmail(), subject, message);

	}

	public String encriptPassword(String password) {
		return DigestUtils.md5Hex(password);
	}
	

}
