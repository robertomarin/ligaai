package ai.liga.ligaai.service;

import java.util.Calendar;
import java.util.Deque;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.liga.dao.HibernateDAOFactory;
import ai.liga.ligaai.dao.LigaAiDao;
import ai.liga.ligaai.model.LigaAi;
import ai.liga.user.model.User;

@Service
public class LigaAiService {

	private LigaAiDao ligaAiDao;

	// private Deque<LigaAi> ligaais;

	@Autowired
	public LigaAiService(HibernateDAOFactory factory) {
		ligaAiDao = factory.getLigaAiDao();
	}

	public LigaAi merge(LigaAi ligaAi) {
		ligaAi = ligaAiDao.merge(ligaAi);
		// if (ligaAi != null && !ligaais.contains(ligaAi)) {
		// ligaais.push(ligaAi);
		// }
		return ligaAi;
	}

	public Deque<LigaAi> getTop() {
		return this.getTop(0);
	}

	public Deque<LigaAi> getTop(int start) {
		if (start < 0) {
			throw new IllegalStateException("Não é permitido inicio menor que 0: " + start);
		}
		// if (ligaais == null)
		// ligaais = ligaAiDao.loadAll();
		return ligaAiDao.getTop(start);
	}

	public LigaAi load(Long id) {
		return ligaAiDao.load(id);
	}

	public boolean topo(Long id) {
		if (id > 0) {
			LigaAi ligaAi = load(id);
			if (ligaAi != null) {
				ligaAi.setTop(Calendar.getInstance());
				// if (ligaais != null) {
				// ligaais.remove(ligaAi);
				// ligaais.push(ligaAi);
				// }
				return true;
			}
		}
		return false;
	}

	public List<LigaAi> getTopFromUser(User user) {
		List<LigaAi> top = ligaAiDao.getTopFromUser(user);
		return top;
	}

	public void delete(Long id) {
		ligaAiDao.delete(ligaAiDao.load(id));
	}

}
