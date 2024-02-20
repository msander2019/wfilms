package ua.kyiv.mesharea.films.dao.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.kyiv.mesharea.films.entity.Film;
import ua.kyiv.mesharea.films.dao.FilmsList;
import ua.kyiv.mesharea.films.utils.SQLiteConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollectionFilmsDB implements FilmsList {

    private ObservableList<Film> films = FXCollections.observableArrayList();

    @Override
    public boolean add(Film film) {
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into films.film(name, year, director, actors, genre, rating, country values (?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, film.getName());
            preparedStatement.setString(2, film.getYear());
            preparedStatement.setString(3, film.getDirector());
            preparedStatement.setString(4, film.getActors());
            preparedStatement.setString(5, film.getGenre());
            preparedStatement.setString(6, film.getRating());
            preparedStatement.setString(7, film.getCountry());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                int id = preparedStatement.getGeneratedKeys().getInt(1); // получить сгенерированный id вставленной записи
                film.setId(id);
                films.add(film);
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(CollectionFilmsDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    @Override
    public boolean update(Film film) {
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update film set name=?, year=?, director=?, actors=?, genre=?, rating=?, country=? where id=?")) {
            preparedStatement.setString(1, film.getName());
            preparedStatement.setString(2, film.getYear());
            preparedStatement.setString(3, film.getDirector());
            preparedStatement.setString(4, film.getActors());
            preparedStatement.setString(5, film.getGenre());
            preparedStatement.setString(6, film.getRating());
            preparedStatement.setString(7, film.getCountry());
            preparedStatement.setInt(8, film.getId());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                // обновление происходит автоматически после нажатия ОК
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(CollectionFilmsDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    @Override
    public boolean delete(Film film) {
        try (Connection connection = SQLiteConnection.getConnection();
             Statement statement = connection.createStatement();) {
            int result = statement.executeUpdate("delete from film where id=" + film.getId());
            if (result > 0) {
                films.remove(film);
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(CollectionFilmsDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    @Override
    public ObservableList<Film> findAll() {
        try (Connection connection = SQLiteConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from film")) {
            while (resultSet.next()) {
                Film film = new Film();
                film.setId(resultSet.getInt("id"));
                film.setName(resultSet.getString("name"));
                film.setYear(resultSet.getString("year"));
                film.setDirector(resultSet.getString("director"));
                film.setActors(resultSet.getString("actors"));
                film.setGenre(resultSet.getString("genre"));
                film.setRating(resultSet.getString("rating"));
                film.setCountry(resultSet.getString("country"));
                films.add(film);
            }
        } catch (SQLException e) {
            Logger.getLogger(CollectionFilmsDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return films;
    }

    @Override
    public ObservableList<Film> find(String text) {
        films.clear();
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(
                     "select * from film " +
                             "where name like ? or year like ? or director like ? or actors like ? or genre like ? or rating like ? or country like ?")) {
            String searchString = "%" + text + "%";
            prepareStatement.setString(1, searchString);
            prepareStatement.setString(2, searchString);
            prepareStatement.setString(3, searchString);
            prepareStatement.setString(4, searchString);
            prepareStatement.setString(5, searchString);
            prepareStatement.setString(6, searchString);
            prepareStatement.setString(7, searchString);

            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                Film film = new Film();
                film.setId(resultSet.getInt("id"));
                film.setName(resultSet.getString("name"));
                film.setYear(resultSet.getString("year"));
                film.setDirector(resultSet.getString("director"));
                film.setActors(resultSet.getString("actors"));
                film.setGenre(resultSet.getString("genre"));
                film.setRating(resultSet.getString("rating"));
                film.setCountry(resultSet.getString("country"));
                films.add(film);
            }
        } catch (SQLException e) {
            Logger.getLogger(CollectionFilmsDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return films;
    }

    public ObservableList<Film> getFilms() {
        return films;
    }
}
