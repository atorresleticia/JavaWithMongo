package resource;

import utils.converter.MovieConverter;
import dto.MovieDTO;
import model.Movie;
import service.MovieService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("movie")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    private MovieService service;
    
    @POST
    public String insert(MovieDTO dto) {
        return service.insert(new MovieConverter().toModel(dto));
    }

    @GET
    @Path("{id}")
    public MovieDTO find(@PathParam("id") String id) {

        Movie movie = service.find(id);

        if (movie != null) {
            return new MovieConverter().toDTO(movie);
        }

        return null;
    }

    @GET
    public List<MovieDTO> find() {
        List<Movie> movies = service.find();

        if (movies != null && !movies.isEmpty()) {
            return new MovieConverter().toDTO(movies);
        }

        return Collections.emptyList();
    }

    @PUT
    public String update(MovieDTO dto) {
        return service.update(new MovieConverter().toModel(dto));
    }

    @DELETE
    public Long delete(String id) {
        return service.delete(id);
    }
}
