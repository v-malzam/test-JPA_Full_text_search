package ru.sibintek.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sibintek.test.model.JsonMessage;
import ru.sibintek.test.service.JsonMessageService;

import java.util.List;

@RestController
@RequestMapping("json_message")
public class JsonMessageController {
    private final HttpHeaders headers = new HttpHeaders();
    private final JsonMessageService jsonMessageService;

    @Autowired
    public JsonMessageController(JsonMessageService jsonMessageService) {
        this.jsonMessageService = jsonMessageService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<JsonMessage>> getAll() {

        List<JsonMessage> jsonMessages = jsonMessageService.getAll();
        return new ResponseEntity<>(jsonMessages, headers, HttpStatus.OK);
    }

    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<JsonMessage> getById(@PathVariable Long id) {

        JsonMessage jsonMessage = jsonMessageService.getById(id);
        return new ResponseEntity<>(jsonMessage, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/keyword/{keyword}", produces = "application/json")
    public ResponseEntity<List<JsonMessage>> getByKeyword(@PathVariable String keyword) {

        List<JsonMessage> jsonMessages = jsonMessageService.keywordQuery(keyword);
        return new ResponseEntity<>(jsonMessages, headers, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<JsonMessage> add(@RequestBody String requestBody) {

        JsonMessage createdJsonMessage = jsonMessageService
                .create(jsonMessageService.buildJsonMessage(requestBody));
        return new ResponseEntity<>(createdJsonMessage, headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<JsonMessage> delete(@PathVariable Long id) {

        jsonMessageService.delete(jsonMessageService.getById(id));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
