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
import org.bson.types.ObjectId;
import utils.converter.Constants;

import javax.servlet.ServletContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MovieDAOImpl implements MovieDAO {

    private static final Logger LOGGER = Logger.getLogger(MovieDAOImpl.class.getName());

    @Override
    public String insert(Movie movie, ServletContext context) {
        getCollection(context).insertOne(movie);
        return movie.getId().toString();
    }

    @Override
    public Movie find(String id, ServletContext context) {

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", new ObjectId(id));

        FindIterable<Movie> movie = getCollection(context).find(searchQuery);
        if (movie != null) {
            MongoCursor<Movie> iterator = movie.iterator();

            while (iterator.hasNext()) {
                return iterator.next();
            }
        }

        return null;
    }

    @Override
    public List<Movie> find(ServletContext context) {

        List<Movie> movies = new ArrayList<>();

        FindIterable<Movie> moviesDB = getCollection(context).find();

        if (moviesDB != null) {
            MongoCursor<Movie> iterator = moviesDB.iterator();

            while (iterator.hasNext()) {
                movies.add(iterator.next());
            }
        }

        return movies;
    }

    @Override
    public String update(String id, Movie movie, ServletContext context) {

        Movie dbMovie = this.find(id, context);
        movie = getEntityToUpdate(dbMovie, movie);

        Document filterById = new Document("_id", new ObjectId(id));
        FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);

        Movie updatedMovie = getCollection(context).findOneAndReplace(filterById, movie, returnDocAfterReplace);

        return updatedMovie.getId().toString();
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

    @Override
    public Long delete(String id, ServletContext context) {

        Document filterById = new Document("_id", new ObjectId(id));
        DeleteResult result = getCollection(context).deleteOne(filterById);

        return result.getDeletedCount();
    }

    private MongoClient getMongoClient(ServletContext context) {
        return (MongoClient) context.getAttribute(Constants.MONGO_CLIENT);
    }

    private MongoCollection<Movie> getCollection(ServletContext context) {

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        return getMongoClient(context).getDatabase("initial").getCollection("movies", Movie.class).withCodecRegistry(pojoCodecRegistry);
    }
}
