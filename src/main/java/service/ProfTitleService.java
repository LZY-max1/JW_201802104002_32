package service;


import dao.ProfTitleDao;
import domain.ProfTitle;

import java.sql.SQLException;
import java.util.Collection;

public final class ProfTitleService {
	private static ProfTitleDao profTitleDao= ProfTitleDao.getInstance();
	private static ProfTitleService profTitleService=new ProfTitleService();
	private ProfTitleService(){}

	public static ProfTitleService getInstance(){
		return profTitleService;
	}

	public Collection<ProfTitle> findAll() throws SQLException {
		return profTitleDao.findAll();
	}

	public ProfTitle find(Integer id) throws SQLException {
		return profTitleDao.find(id);
	}

	public void update(ProfTitle profTitle) throws SQLException {
		profTitleDao.update(profTitle);
	}

	public void add(ProfTitle profTitle) throws SQLException {
		profTitleDao.add(profTitle);
	}

	public void delete(Integer id) throws SQLException {
		ProfTitle profTitle = this.find(id);
		profTitleDao.delete(profTitle);
	}

	public void delete(ProfTitle profTitle) throws SQLException {
		profTitleDao.delete(profTitle);
	}
}

