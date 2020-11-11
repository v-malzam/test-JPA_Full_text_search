package ru.sibintek.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Indexed
@Table(name = "json_messages")
public class JsonMessage {

    @JsonIgnore
    @Id
    @NotNull(message = "Request must include a id.")
    private Long id;

    @JsonValue
    @JsonRawValue
    @Field
    @Column(name = "json_data")
    private String jsonData;
}
