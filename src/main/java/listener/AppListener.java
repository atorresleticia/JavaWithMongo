package listener;

import com.mongodb.MongoClient;
import utils.converter.Constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.text.MessageFormat;
import java.util.logging.Logger;

@WebListener
public class AppListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(AppListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        String host = context.getInitParameter(Constants.MONGO_HOST);
        String port = context.getInitParameter(Constants.MONGO_PORT);

        MongoClient mongoClient = new MongoClient(host, Integer.parseInt(port));
        sce.getServletContext().setAttribute(Constants.MONGO_CLIENT, mongoClient);

        LOGGER.info(MessageFormat.format("Successfully connected to MongoDB {0}:{1}", host, port));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();
        String host = context.getInitParameter(Constants.MONGO_HOST);
        String port = context.getInitParameter(Constants.MONGO_PORT);

        MongoClient mongoClient = (MongoClient) context.getAttribute(Constants.MONGO_CLIENT);
        mongoClient.close();

        LOGGER.info(MessageFormat.format("Successfully closed connection to MongoDB {0}:{1}", host, port));
    }

}
