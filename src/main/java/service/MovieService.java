package service;

import model.Movie;

import java.util.List;

public interface MovieService {

    String insert(Movie movie);

    Movie find(String id);

    List<Movie> find();

    String update(Movie movie);

    Long delete(String id);

}
