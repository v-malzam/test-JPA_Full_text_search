package ru.sibintek.test.controller;

import java.util.ArrayList;
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

import ru.sibintek.test.model.JsonDto;
import ru.sibintek.test.model.JsonMessage;
import ru.sibintek.test.service.JsonMessageService;

@RestController
@RequestMapping("json_message")
public class JsonMessageController {
    private static final String CUSTOM_HEADER_NAME = "X-Query-Result";
    private HttpHeaders headers = new HttpHeaders();
    @Autowired
    private JsonMessageService jsonMessageService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<JsonDto>> getAll() {
        List<JsonMessage> jsonMessages = jsonMessageService.getAll();

        List<JsonDto> jsonData = new ArrayList<>();
        for (JsonMessage jsonMessage : jsonMessages) {
            jsonData.add(new JsonDto(jsonMessage.getJsonData()));
        }

        headers.clear();
        headers.add(CUSTOM_HEADER_NAME,
                "All objects JsonMessage found. Number of objects " + jsonMessages.size());
        return new ResponseEntity<>(jsonData, headers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<String> getById(@PathVariable Long id) {
        JsonMessage jsonMessage = jsonMessageService.getById(id);
        String jsonData = jsonMessage.getJsonData();

        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Found JsonMessage object with id " + id);
        return new ResponseEntity<>(jsonData, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody String requestBody) {
        JsonMessage jsonMessage = buildEntity(requestBody);
        JsonMessage createdJsonMessage = jsonMessageService.create(jsonMessage);
        String createdJsonData = createdJsonMessage.getJsonData();

        headers.clear();
        headers.add(CUSTOM_HEADER_NAME,
                "Created JsonMessage object with id " + createdJsonMessage.getId());
        return new ResponseEntity<>(createdJsonData, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<JsonMessage> delete(@PathVariable Long id) {
        jsonMessageService.delete(jsonMessageService.getById(id));

        headers.clear();
        headers.add(CUSTOM_HEADER_NAME, "Deleted JsonMessage object with id " + id);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    private JsonMessage buildEntity(String requestBody) {
        JsonMessage jsonMessage = new JsonMessage(null, requestBody);

        int indexKeyId = requestBody.indexOf("id");
        int lastIndexField = requestBody.indexOf(",", indexKeyId);
        String substringWithId = requestBody.substring(indexKeyId, lastIndexField);

        int firstIndexField = indexKeyId + substringWithId.indexOf(":") + 1;
        String substringFieldId = requestBody.substring(firstIndexField, lastIndexField);

        Long id = Long.parseLong(substringFieldId.trim());
        jsonMessage.setId(id);
        return jsonMessage;
    }
}
