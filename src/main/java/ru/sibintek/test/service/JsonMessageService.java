package ru.sibintek.test.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.sibintek.test.model.JsonMessage;
import ru.sibintek.test.repository.JsonMessageRepository;

@Service
public class JsonMessageService {
    private JsonMessageRepository jsonMessageRepository;
    private EntityManager entityManager;

    @Autowired
    public JsonMessageService(JsonMessageRepository jsonMessageRepository, EntityManager entityManager) {
        this.jsonMessageRepository = jsonMessageRepository;
        this.entityManager = entityManager;
    }

    public JsonMessage create(JsonMessage jsonMessage) {
        return jsonMessageRepository.save(jsonMessage);
    }

    public JsonMessage update(JsonMessage jsonMessage) {
        return jsonMessageRepository.save(jsonMessage);
    }

    public void delete(JsonMessage jsonMessage) {
        jsonMessageRepository.delete(jsonMessage);
    }

    public List<JsonMessage> getAll() {
        entityManager.clear();
        return jsonMessageRepository.findAll();
    }

    public JsonMessage getById(Long id) {
        return jsonMessageRepository.findById(id).get();
    }

    public boolean existsById(Long id) {
        return jsonMessageRepository.existsById(id);
    }
    
    public JsonMessage buildJsonMessage(String requestBody) {
        int indexKeyId = requestBody.indexOf("id");
        int lastIndexField = requestBody.indexOf(",", indexKeyId);
        String substringWithId = requestBody.substring(indexKeyId, lastIndexField);

        int firstIndexField = indexKeyId + substringWithId.indexOf(":") + 1;
        String substringFieldId = requestBody.substring(firstIndexField, lastIndexField);

        Long id = Long.parseLong(substringFieldId.trim());
        return new JsonMessage(id, requestBody);
    }

    public List<JsonMessage> keywordQuery(String keyword) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
                .forEntity(JsonMessage.class).get();

        Query query = queryBuilder.keyword().onField("jsonData").matching(keyword).createQuery();

        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, JsonMessage.class);
        return jpaQuery.getResultList();
    }
}
