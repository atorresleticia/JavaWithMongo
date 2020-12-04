package service;

import model.Movie;

import javax.servlet.ServletContext;
import java.util.List;

public interface MovieService {

    String insert(Movie movie, ServletContext context);

    Movie find(String id, ServletContext context);

    List<Movie> find(ServletContext context);

    String update(String id, Movie movie, ServletContext context);

    Long delete(String id, ServletContext context);
}
