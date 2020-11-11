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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.sibintek.test.model.JsonMessage;
import ru.sibintek.test.service.JsonMessageService;

@RestController
@RequestMapping("json_message")
public class JsonMessageController {
    private HttpHeaders headers = new HttpHeaders();
    @Autowired
    private JsonMessageService jsonMessageService;

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

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<JsonMessage> add(@RequestBody String requestBody) {

        JsonMessage createdJsonMessage = jsonMessageService.create(buildJsonMessage(requestBody));
        return new ResponseEntity<>(createdJsonMessage, headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<JsonMessage> delete(@PathVariable Long id) {

        jsonMessageService.delete(jsonMessageService.getById(id));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    private JsonMessage buildJsonMessage(String requestBody) {
        int indexKeyId = requestBody.indexOf("id");
        int lastIndexField = requestBody.indexOf(",", indexKeyId);
        String substringWithId = requestBody.substring(indexKeyId, lastIndexField);

        int firstIndexField = indexKeyId + substringWithId.indexOf(":") + 1;
        String substringFieldId = requestBody.substring(firstIndexField, lastIndexField);

        Long id = Long.parseLong(substringFieldId.trim());
        return new JsonMessage(id, requestBody);
    }
}
