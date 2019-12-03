package dao;


import domain.Major;

import java.util.Collection;

public final class MajorDao {
	private static MajorDao majorDao = new MajorDao();
	private MajorDao(){}
	public static MajorDao getInstance(){
		return majorDao;
	}
	private static Collection<Major> majors;

	public Collection<Major> findAll(){
		return MajorDao.majors;
	}
	
	public Major find(Integer id){
		Major desiredMajor = null;
		for (Major major : majors) {
			if(id.equals(major.getId())){
				desiredMajor =  major; 
			}
		}
		return desiredMajor;
	}
	
	public boolean update(Major major){
		majors.remove(major);
		return majors.add(major);		
	}
	
	public boolean add(Major major){
		return majors.add(major);		
	}

	public boolean delete(Integer id){
		Major major = this.find(id);
		return this.delete(major);
	}
	
	public boolean delete(Major major){
		return majors.remove(major);
	}
	
	
	public static void main(String[] args){
		MajorDao dao = new MajorDao();
		Collection<Major> majors = dao.findAll();
		display(majors);
//		Major major = new Major(2,"副教�?","02","");
//		major.setNo("02x");
//		dao.update(major);
//		display(majors);
//	
//		dao.delete(major);
//		dao.delete(3);
//		display(majors);
//		dao.add(new Major(2,"工程�?","04",""));
//		display(majors);
//		System.out.println("2="+dao.find(2));
	}

	private static void display(Collection<Major> majors) {
		for (Major major : majors) {
			System.out.println(major);
		}
	}	
}
