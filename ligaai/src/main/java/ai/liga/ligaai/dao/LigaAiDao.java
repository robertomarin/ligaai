package ai.liga.ligaai.dao;

import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Deque;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ai.liga.dao.GenericHibernateDAO;
import ai.liga.ligaai.model.LigaAi;
import ai.liga.user.model.User;

public class LigaAiDao extends GenericHibernateDAO<LigaAi> {

	public LigaAiDao(Class<LigaAi> persistentClass, SessionFactory sessionFactory) {
		super(persistentClass, sessionFactory);
	}

	@Override
	public LigaAi merge(LigaAi ligaAi) {
		Session session = sf.getCurrentSession();

		ligaAi.setCreated(Calendar.getInstance());
		ligaAi.setTop(Calendar.getInstance());
		ligaAi = (LigaAi) session.merge(ligaAi);

		return ligaAi;
	}

	@SuppressWarnings("unchecked")
	public Deque<LigaAi> getTop(int start) {
		Criteria c = super.getCriteria(true);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		c.setFetchMode("ligaai", FetchMode.JOIN);
		c.addOrder(Order.desc("top"));
		if (start >= 0) {
			c.setFirstResult(start);
		}
		c.setMaxResults(20);

		return new ArrayDeque<LigaAi>(c.list());
	}

	@SuppressWarnings("unchecked")
	public List<LigaAi> getTopFromUser(User user) {
		if (user == null || user.getId() == null) {
			return null;
		}
		Criteria c = super.getCriteria(true);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		c.add(Restrictions.eq("user.id", user.getId()));

		return c.list();
	}

}
