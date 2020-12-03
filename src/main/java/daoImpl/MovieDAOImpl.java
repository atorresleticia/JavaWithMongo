package daoImpl;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.result.DeleteResult;
import dao.MovieDAO;
import model.Movie;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import utils.converter.Constants;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MovieDAOImpl implements MovieDAO {

    @Context
    private ServletContext context;

    @Override
    public String insert(Movie movie) {
        getCollection().insertOne(movie);
        //TODO need to see how to get the ID
        return null;
    }

    @Override
    public Movie find(String id) {

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", id);

        FindIterable<Movie> movie = getCollection().find(searchQuery);
        if (movie != null) {
            MongoCursor<Movie> iterator = movie.iterator();

            while (iterator.hasNext()) {
                return iterator.next();
            }
        }

        return null;
    }

    @Override
    public List<Movie> find() {

        List<Movie> movies = new ArrayList<>();

        FindIterable<Movie> moviesDB = getCollection().find();

        if (moviesDB != null) {
            MongoCursor<Movie> iterator = moviesDB.iterator();

            while (iterator.hasNext()) {
                movies.add(iterator.next());
            }
        }

        return movies;
    }

    @Override
    public String update(Movie movie) {

        Document filterById = new Document("_id", movie.getId());
        FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);

        Movie updatedMovie = getCollection().findOneAndReplace(filterById, movie, returnDocAfterReplace);

        return updatedMovie.getId().toString();
    }

    @Override
    public Long delete(String id) {

        Document filterById = new Document("_id", id);
        DeleteResult result = getCollection().deleteOne(filterById);

        return result.getDeletedCount();
    }

    private MongoClient getMongoClient() {
        return (MongoClient) context.getAttribute(Constants.MONGO_CLIENT);
    }

    private MongoCollection<Movie> getCollection() {

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        return getMongoClient().getDatabase("initial").getCollection("movies", Movie.class).withCodecRegistry(pojoCodecRegistry);
    }
}
