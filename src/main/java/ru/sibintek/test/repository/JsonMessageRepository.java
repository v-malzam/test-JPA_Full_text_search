package ru.sibintek.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sibintek.test.model.JsonMessage;

public interface JsonMessageRepository extends JpaRepository<JsonMessage, Long> {

//    @Query(value = "SELECT id, json_data FROM json_messages WHERE collateral = ?1", nativeQuery = true)
//    JsonMessage findBySubstring(String substring);

    // ниже пошаговый getLastAssess, для отладки

//    @Query(value = "SELECT MAX(assessed_date) FROM assess WHERE collateral = ?1",
//            nativeQuery = true)
//    LocalDate maxDate(Long collateralId);
//    
//    @Query(value = "SELECT id, assessed_date, assessed_value, collateral FROM assess WHERE collateral = ?1 AND assessed_date = ?2",
//            nativeQuery = true)
//    Assess getAssess(Long collateralId, LocalDate maxDate); 

}
