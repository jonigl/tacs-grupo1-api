package ar.com.tacsutn.grupo1.eventapp.config;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import javax.annotation.Nonnull;

@EnableAutoConfiguration(
    exclude = {
        EmbeddedMongoAutoConfiguration.class,
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
    })
@Configuration
public class FongoConfig extends AbstractMongoConfiguration  {

    @Override
    @Nonnull
    protected String getDatabaseName() {
        return "testdb";
    }

    @Override
    @Nonnull
    public MongoClient mongoClient() {
        return new Fongo(getDatabaseName()).getMongo();
    }
}
