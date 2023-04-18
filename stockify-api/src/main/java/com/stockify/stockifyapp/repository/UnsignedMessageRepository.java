package com.stockify.stockifyapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stockify.stockifyapp.model.UnsignedMessage;

@Repository
public interface UnsignedMessageRepository extends CrudRepository<UnsignedMessage, Integer>{
    
    
}
