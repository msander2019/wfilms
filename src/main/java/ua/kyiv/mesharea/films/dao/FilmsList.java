package ua.kyiv.mesharea.films.dao;

import javafx.collections.ObservableList;
import ua.kyiv.mesharea.films.entity.Film;

public interface FilmsList {

    boolean add(Film film);

    boolean update(Film film);

    boolean delete(Film film);

    ObservableList<Film> findAll();

    ObservableList<Film> find(String text);
}
