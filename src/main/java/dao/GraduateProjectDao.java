package dao;


import domain.GraduateProject;

import java.util.*;

public final class GraduateProjectDao {
	private static GraduateProjectDao graduateProjectDao = new GraduateProjectDao();
	static Set<GraduateProject> projects;


	private GraduateProjectDao() {
	}

	public static GraduateProjectDao getInstance() {
		return graduateProjectDao;
	}


	public Collection<GraduateProject> findAll() {
		return projects;
	}

	public void addGraduateProject(GraduateProject project) {
		projects.add(project);
	}

	public void update(GraduateProject project) {
		projects.remove(project);
		this.addGraduateProject(project);
	}

	public GraduateProject find(Integer id) {
		GraduateProject desiredGraduateProject = null;
		for (GraduateProject graduateProject : projects) {
			if (id.equals(graduateProject.getId())) {
				desiredGraduateProject = graduateProject;
				break;
			}
		}
		return desiredGraduateProject;

	}

	public void delete( int id) {
		Iterator<GraduateProject> it = projects.iterator();
		while(it.hasNext()){
			GraduateProject g = it.next();
			if(g.getId()==id){
				it.remove();
			}
		}		
	}
}
