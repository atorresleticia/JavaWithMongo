package serviceImpl;

import dao.MovieDAO;
import model.Movie;
import service.MovieService;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.util.List;

public class MovieServiceImpl implements MovieService {

    @Inject
    private MovieDAO dao;

    @Override
    public String insert(Movie movie, ServletContext context) {
        return dao.insert(movie, context);
    }

    @Override
    public Movie find(String id, ServletContext context) {
        return dao.find(id, context);
    }

    @Override
    public List<Movie> find(ServletContext context) {
        return dao.find(context);
    }

    @Override
    public String update(String id, Movie movie, ServletContext context) {
        return dao.update(id, movie, context);
    }

    @Override
    public Long delete(String id, ServletContext context) {
        return dao.delete(id, context);
    }

}
