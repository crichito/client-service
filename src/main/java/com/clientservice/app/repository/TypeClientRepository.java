package com.clientservice.app.repository;

import com.clientservice.app.document.TypeClient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TypeClientRepository extends ReactiveMongoRepository<TypeClient,String> {
    Mono<TypeClient> findByDescription(String description);
}
