package ru.sibintek.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sibintek.test.model.JsonMessage;

public interface JsonMessageRepository extends JpaRepository<JsonMessage, Long> {

}
