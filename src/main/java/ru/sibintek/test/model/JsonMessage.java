package ru.sibintek.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "json_messages")
public class JsonMessage {

    @Id
    @NotNull(message = "Request must include a id.")
    private Long id;

    @Transient
    private HttpEntity<String> httpEntity;

    @JsonIgnore
    @Column(name = "json_data")
    private String jsonData;
}
