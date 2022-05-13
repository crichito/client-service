package com.clientservice.app.serviceImpl;

import com.clientservice.app.document.Client;
import com.clientservice.app.document.TypeClient;
import com.clientservice.app.repository.CLientRepository;
import com.clientservice.app.repository.TypeClientRepository;
import com.clientservice.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private CLientRepository clientRepository;
    @Autowired
    private TypeClientRepository typeClientRepository;

    @Override
    public Mono<Client> findById(String id) {
        return clientRepository.findById(id);
    }

    @Override
    public Mono<Client> save(Client document) {
        return clientRepository.save(document);
    }

    @Override
    public Mono<Client> update(Client document, String id) {
        return clientRepository.findById(id).flatMap(c ->{
            c.setFirtName(document.getFirtName());
            c.setLastName(document.getLastName());
            c.setDocument(document.getDocument());
            c.setTypeClient(document.getTypeClient());
            c.setUpdateDate(new Date());
            return clientRepository.save(c);
        });
    }

    @Override
    public Flux<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Mono<Void> delete(String id) {
        return clientRepository.deleteById(id);
    }


    @Override
    public Flux<TypeClient> findAllTypeClient() {
        return typeClientRepository.findAll();
    }

    @Override
    public Mono<TypeClient> saveTypeClient(TypeClient typeClient) {
        return typeClientRepository.save(typeClient);
    }

    @Override
    public Mono<TypeClient> findByIdTypeClient(String id) {
        return typeClientRepository.findById(id);
    }

    @Override
    public Mono<TypeClient> findByDescription(String description) {
        return typeClientRepository.findByDescription(description);
    }


}
