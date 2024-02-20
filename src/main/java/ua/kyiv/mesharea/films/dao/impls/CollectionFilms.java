package ua.kyiv.mesharea.films.dao.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.kyiv.mesharea.films.entity.Film;
import ua.kyiv.mesharea.films.dao.FilmsList;

public class CollectionFilms implements FilmsList {

    private ObservableList<Film> films = FXCollections.observableArrayList();

    @Override
    public boolean add(Film film) {
        films.add(film);
        return true;
    }

    @Override
    public boolean update(Film film) {
        return false;
    }

    @Override
    public boolean delete(Film film) {
        films.remove(film);
        return true;
    }

    @Override
    public ObservableList<Film> findAll() {
        return null;
    }

    @Override
    public ObservableList<Film> find(String text) {
        return null;
    }

    public ObservableList<Film> getFilms() {
        return films;
    }

    public void print() {
        int number = 0;
        System.out.println();
        for (Film film : films) {
            number++;
            System.out.println(number + ") name = " + film.getName() + "; year = " + film.getYear() + "; director = " + film.getDirector());
        }
    }

    public void fillTestData() {
        films.add(new Film("Che", "2008", "Стивен Содерберг", "Бенисио дель Торо, Демиан Бичир, Родриго Санторо", "биография/драма", "6.5", "Spain/USA"));
        films.add(new Film("The Edge", "1997", "Ли Тамахори", "Энтони Хопкинс, Алек Болдуин, Эль Макферсон, Гарольд Перрино", "триллер/драма", "5.8", "USA"));
    }
}
