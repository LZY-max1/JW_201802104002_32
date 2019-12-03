package service;


import dao.DegreeDao;
import domain.Degree;

import java.sql.SQLException;
import java.util.Collection;

public final class DegreeService {
    private static DegreeDao degreeDao;

    static {
        try {
            degreeDao = DegreeDao.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DegreeService degreeService
            =new DegreeService();
    private DegreeService(){}

    public static DegreeService getInstance(){
        return degreeService;
    }

    public Collection<Degree> findAll() throws SQLException {
        return degreeDao.findAll();
    }

    public Degree find(Integer id) throws SQLException {
        return degreeDao.find(id);
    }

    public void update(Degree degree) throws SQLException {
        degreeDao.update(degree);
    }

    public void add(Degree degree) throws SQLException {
        degreeDao.add(degree);

    }

    public void delete(Integer id) throws SQLException {
        Degree degree = this.find(id);

        degreeDao.delete(degree);

    }

    public void delete(Degree degree) throws SQLException {
        degreeDao.delete(degree);

    }
}

