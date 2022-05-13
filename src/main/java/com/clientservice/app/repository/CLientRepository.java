package com.clientservice.app.repository;
import com.clientservice.app.document.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CLientRepository extends ReactiveMongoRepository<Client,String> {
}
