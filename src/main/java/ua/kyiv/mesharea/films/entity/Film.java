package ua.kyiv.mesharea.films.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Film {

    private SimpleIntegerProperty id  = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty year = new SimpleStringProperty("");
    private SimpleStringProperty director = new SimpleStringProperty("");
    private SimpleStringProperty actors = new SimpleStringProperty("");
    private SimpleStringProperty genre = new SimpleStringProperty("");
    private SimpleStringProperty rating = new SimpleStringProperty("");
    private SimpleStringProperty country = new SimpleStringProperty("");

    public Film() {
    }

    public Film(String name, String year, String director, String actors, String genre, String rating, String country) {
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleStringProperty(year);
        this.director = new SimpleStringProperty(director);
        this.actors = new SimpleStringProperty(actors);
        this.genre = new SimpleStringProperty(genre);
        this.rating = new SimpleStringProperty(rating);
        this.country = new SimpleStringProperty(country);
    }

    public Film(int id, String name, String year, String director, String actors, String genre, String rating, String country) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleStringProperty(year);
        this.director = new SimpleStringProperty(director);
        this.actors = new SimpleStringProperty(actors);
        this.genre = new SimpleStringProperty(genre);
        this.rating = new SimpleStringProperty(rating);
        this.country = new SimpleStringProperty(country);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }
    public SimpleStringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public String getYear() {
        return year.get();
    }
    public SimpleStringProperty yearProperty() {
        return year;
    }
    public void setYear(String year) {
        this.year.set(year);
    }

    public String getDirector() {
        return director.get();
    }
    public SimpleStringProperty directorProperty() {
        return director;
    }
    public void setDirector(String director) {
        this.director.set(director);
    }

    public String getActors() {
        return actors.get();
    }
    public SimpleStringProperty actorsProperty() {
        return actors;
    }
    public void setActors(String actors) {
        this.actors.set(actors);
    }

    public String getGenre() {
        return genre.get();
    }
    public SimpleStringProperty genreProperty() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public String getRating() {
        return rating.get();
    }
    public SimpleStringProperty ratingProperty() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating.set(rating);
    }

    public String getCountry() {
        return country.get();
    }
    public SimpleStringProperty countryProperty() {
        return country;
    }
    public void setCountry(String country) {
        this.country.set(country);
    }

    @Override
    public String toString() {
        return "Film{" +
                "name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", genre='" + genre + '\'' +
                ", rating='" + rating + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
