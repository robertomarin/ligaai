package ai.liga.ligaai.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.liga.dao.HibernateDAOFactory;
import ai.liga.ligaai.model.User;
import ai.liga.user.dao.UserDao;

@Service
public class UserService {

	private UserDao userDao;

	@Autowired
	public UserService(HibernateDAOFactory factory) {
		userDao = factory.getUserDao();
	}

	public boolean exists(User user) {
		return userDao.findByEmail(user) != null;
	}

	public User save(User user) {
		user.setCreated(Calendar.getInstance());
		user = userDao.merge(user);
		return user;
	}

	public User login(User user) {
		return userDao.login(user);
	}

}