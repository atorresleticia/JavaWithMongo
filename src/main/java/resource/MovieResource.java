package resource;

import utils.converter.MovieConverter;
import dto.MovieDTO;
import model.Movie;
import service.MovieService;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("movie")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    private MovieService service;

    @Context
    private ServletContext context;

    @POST
    public Response insert(MovieDTO dto) {
        String idInserted = service.insert(new MovieConverter().toModel(dto), context);
        return Response.ok(idInserted).build();
    }

    @GET
    @Path("{id}")
    public MovieDTO find(@PathParam("id") String id) {

        Movie movie = service.find(id, context);

        if (movie != null) {
            return new MovieConverter().toDTO(movie);
        }

        return null;
    }

    @GET
    public List<MovieDTO> find() {
        List<Movie> movies = service.find(context);

        if (movies != null && !movies.isEmpty()) {
            return new MovieConverter().toDTO(movies);
        }

        return Collections.emptyList();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") String id, MovieDTO dto) {
        String idUpdated = service.update(id, new MovieConverter().toModel(dto), context);
        return Response.ok(idUpdated).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id) {
        Long deleteResult = service.delete(id, context);
        return Response.ok(deleteResult).build();
    }
}
