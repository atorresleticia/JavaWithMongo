package validator;

import dao.MovieDAO;
import model.Movie;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class MovieValidator {

    @Inject
    private MovieDAO dao;

    public void validateInsert(Movie movie, ServletContext context) {
        validateMandatoryFields(movie);
        validateDuplicateObject(movie, context);
    }

    private void validateDuplicateObject(Movie movie, ServletContext context) {

        Movie dbMovie = dao.findByName(movie.getName(), context);

        if (dbMovie != null) {
            throw new RuntimeException(EnumExceptions.DUPLICATE_OBJECT_FOUND.getMessage(Movie.class.getSimpleName(), "name", dbMovie.getName()));
        }
    }

    public void validateUpdate(Movie movie, ServletContext context) {
        validateMandatoryFields(movie);
        validateDuplicateObject(movie, context);
    }

    private void validateMandatoryFields(Movie movie) {

        try {
            Class<Movie> movieClass = Movie.class;

            List<String> mandatoryFields = Arrays.asList(
                    movieClass.getDeclaredField("name").getName(),
                    movieClass.getDeclaredField("year").getName()
            );

            Field[] declaredFields = movieClass.getDeclaredFields();
            for (Field field : declaredFields) {

                field.setAccessible(true);

                if (mandatoryFields.contains(field.getName()) && field.get(movie) == null) {
                    throw new RuntimeException(EnumExceptions.FIELD_IS_MANDATORY.getMessage(field.getName()));
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
