package serviceImpl;

import dao.MovieDAO;
import model.Movie;
import service.MovieService;
import validator.MovieValidator;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.xml.validation.Validator;
import java.lang.reflect.Field;
import java.util.List;

public class MovieServiceImpl implements MovieService {

    @Inject
    private MovieDAO dao;

    @Inject
    private MovieValidator validator;

    @Override
    public String insert(Movie movie, ServletContext context) {
        validator.validateInsert(movie, context);
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

        Movie dbMovie = this.find(id, context);
        movie = getEntityToUpdate(dbMovie, movie);

        validator.validateUpdate(movie, context);

        return dao.update(id, movie, context);
    }

    @Override
    public Long delete(String id, ServletContext context) {
        return dao.delete(id, context);
    }

    private Movie getEntityToUpdate(Movie dbMovie, Movie movie) {

        Field[] fields = Movie.class.getDeclaredFields();

        for (Field field : fields) {

            try {
                field.setAccessible(true);

                Object dbValue = field.get(dbMovie);
                Object value = field.get(movie);

                if (value == null || value.equals(dbValue)) {
                    field.set(movie, dbValue);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return movie;
    }
}
