package service;


import dao.SchoolDao;
import domain.School;

import java.sql.SQLException;
import java.util.Collection;

public final class SchoolService {
	private static SchoolDao schoolDao= SchoolDao.getInstance();
	private static SchoolService schoolService=new SchoolService();
	
	
	public static SchoolService getInstance(){
		return schoolService;
	}

	public Collection<School> findAll() throws SQLException {
		return schoolDao.findAll();
	}
	
	public School find(Integer id) throws SQLException {
		return schoolDao.find(id);
	}

	public void update(School school) throws SQLException {
		schoolDao.update(school);
	}
	
	public void add(School school) throws SQLException {
		schoolDao.add(school);
	}

	public void delete(Integer id) throws SQLException {
		School school = this.find(id);
		this.delete(school);
	}
	
//	public boolean delete(School school){
//		//获得所有下一级单位（Department）
//		Collection<Department> departmentSet =
//				DepartmentService.getInstance().getAll(school);
//		//若没有二级单位，则能够删除
//		if(departmentSet.size()==0){
//			return schoolDao.delete(school);
//		}else {
//			return false;
//		}
//	}

	public void delete(School school) throws SQLException {
		schoolDao.delete(school);
	}
}
