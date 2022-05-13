package com.clientservice.app.service;

import com.clientservice.app.document.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RepositoryCrud <T> {
    
	public Mono<T> findById(String id);
	public Mono<T> save(T document);
	public Mono<T> update(T document, String id);/*Agregamos esto*/
	public Mono<Void> delete(String id);
	public Flux<T> findAll();

}
