package ru.sibintek.test.service;

import java.util.List;

import javax.persistence.EntityManager;

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
}
