package ru.sibintek.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Long id;

    @Column(name = "message_body")
    private String messageBody;

}
