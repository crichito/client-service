package com.clientservice.app.service;

import com.clientservice.app.document.Client;
import com.clientservice.app.document.TypeClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ClientService extends RepositoryCrud<Client> {

    Flux<TypeClient> findAllTypeClient();
    Mono<TypeClient> saveTypeClient(TypeClient typeClient);
    Mono<TypeClient> findByIdTypeClient(String id);
    Mono<TypeClient> findByDescription(String description);

}
