package serviceImpl;

import dao.MovieDAO;
import model.Movie;
import service.MovieService;

import javax.inject.Inject;
import java.util.List;

public class MovieServiceImpl implements MovieService {

    @Inject
    private MovieDAO dao;

    @Override
    public String insert(Movie movie) {
        return dao.insert(movie);
    }

    @Override
    public Movie find(String id) {
        return dao.find(id);
    }

    @Override
    public List<Movie> find() {
        return dao.find();
    }

    @Override
    public String update(Movie movie) {
        return dao.update(movie);
    }

    @Override
    public Long delete(String id) {
        return dao.delete(id);
    }

}
