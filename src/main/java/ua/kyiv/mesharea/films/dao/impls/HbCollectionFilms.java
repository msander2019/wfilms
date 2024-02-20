package ua.kyiv.mesharea.films.dao.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import ua.kyiv.mesharea.films.entity.Film;
import ua.kyiv.mesharea.films.dao.FilmsList;

public class HbCollectionFilms implements FilmsList {

    private ObservableList<Film> films = FXCollections.observableArrayList();
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    public HbCollectionFilms() {
        findAll();
        sessionFactory = getSessionFactory();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    @Override
    public boolean add(Film film) {
        Session session = sessionFactory.openSession();
        session.save(film);
        session.flush();
        session.close();
        return true;
    }

    @Override
    public boolean update(Film film) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(film);
        session.flush();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Film film) {
        Session session = sessionFactory.openSession();
        session.delete(film);
        session.flush();
        session.close();
        return true;
    }

    @Override
    public ObservableList<Film> findAll() {
        films.clear();
        Session session = getSessionFactory().openSession();
        films.addAll(session.createCriteria(Film.class).list());
        session.close();
        return films;
    }

    @Override
    public ObservableList<Film> find(String text) {
        films.clear();
        Session session = getSessionFactory().openSession();
//        Criterion crit1 = Restrictions.like("name", text + "%");
//        Criterion crit2 = Restrictions.like("year", text + "%");
//        Criterion crit3 = Restrictions.like("director", text + "%");
//        Criterion crit4 = Restrictions.like("actors", text + "%");
//        Criterion crit5 = Restrictions.like("genre", text + "%");
//        Criterion crit6 = Restrictions.like("rating", text + "%");
//        Criterion crit7 = Restrictions.like("country", text + "%");

        Criterion crit1 = Restrictions.ilike("name", "%" + text + "%");
        Criterion crit2 = Restrictions.ilike("year", "%" + text + "%");
        Criterion crit3 = Restrictions.ilike("director", "%" + text + "%");
        Criterion crit4 = Restrictions.ilike("actors", "%" + text + "%");
        Criterion crit5 = Restrictions.ilike("genre", "%" + text + "%");
        Criterion crit6 = Restrictions.ilike("rating", "%" + text + "%");
        Criterion crit7 = Restrictions.ilike("country", "%" + text + "%");

        films.addAll(session
                .createCriteria(Film.class)
                .add(Restrictions.or(crit1, crit2, crit3, crit4, crit5, crit6, crit7)).list());
        session.close();
        return films;
    }

    public ObservableList<Film> getFilms() {
        return films;
    }
}
