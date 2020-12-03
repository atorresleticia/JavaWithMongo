package utils.converter;

import dto.MovieDTO;
import model.Movie;
import org.apache.commons.beanutils.BeanUtils;
import org.bson.types.ObjectId;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MovieConverter {

    public Movie toModel(MovieDTO dto) {
        Movie movie = new Movie();

        try {
            BeanUtils.copyProperties(movie, dto);

            if (dto.getId() != null) {
                movie.setId(new ObjectId(dto.getId()));
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return movie;
    }

    public MovieDTO toDTO(Movie movie) {

        MovieDTO dto = new MovieDTO();

        try {
            BeanUtils.copyProperties(dto, movie);
            dto.setId(movie.getId().toString());

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return dto;
    }

    public List<Movie> toModel(List<MovieDTO> dto) {

        List<Movie> movies = new ArrayList<>();
        dto.forEach(o -> {
            movies.add(toModel(o));
        });

        return movies;
    }

    public List<MovieDTO> toDTO(List<Movie> movies) {

        List<MovieDTO> dtos = new ArrayList<>();
        movies.forEach(o -> {
            dtos.add(toDTO(o));
        });

        return dtos;
    }

}
