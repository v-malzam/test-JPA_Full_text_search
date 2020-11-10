package ru.sibintek.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.sibintek.test.model.JsonMessage;
import ru.sibintek.test.service.JsonMessageService;

@RestController
@RequestMapping("json_message")
public class JsonMessageController {
    private static final String CUSTOM_HEADER_NAME = "X-Query-Result";
    private HttpHeaders headers = new HttpHeaders();
    @Autowired
    private JsonMessageService jsonMessageService;

    @GetMapping
    public ResponseEntity<List<JsonMessage>> getAll() {
        List<JsonMessage> jsonMessages = jsonMessageService.getAll();
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME,
                "All objects JsonMessage found. Number of objects " + jsonMessages.size());
        return new ResponseEntity<>(jsonMessages, headers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<JsonMessage> getById(@PathVariable Long id) {
        JsonMessage jsonMessages = jsonMessageService.getById(id);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Found JsonMessage object with id " + id);
        return new ResponseEntity<>(jsonMessages, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JsonMessage> add(@RequestBody JsonMessage jsonMessage) {
        JsonMessage createdJsonMessage = jsonMessageService.create(jsonMessage);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME,
                "Created JsonMessage object with id " + createdJsonMessage.getId());
        return new ResponseEntity<>(createdJsonMessage, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<JsonMessage> update(@RequestBody JsonMessage jsonMessage) {
        JsonMessage updatedJsonMessage = jsonMessageService.update(jsonMessage);
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME,
                "Updated JsonMessage object with id " + updatedJsonMessage.getId());
        return new ResponseEntity<>(updatedJsonMessage, headers, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<JsonMessage> delete(@PathVariable Long id) {
        jsonMessageService.delete(jsonMessageService.getById(id));
        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Deleted JsonMessage object with id " + id);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
