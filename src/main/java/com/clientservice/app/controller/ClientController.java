package com.clientservice.app.controller;

import com.clientservice.app.document.Client;
import com.clientservice.app.service.ClientService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public Mono<ResponseEntity<Flux<Client>>> findAll(){

        return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(clientService.findAll())
				); 
    }
    @CircuitBreaker(name = "clientCB",fallbackMethod = "metodoAlternativo")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Client>> findById(@PathVariable String id){
        return clientService.findById(id).map(c -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Client>> update2(@RequestBody Client client, @PathVariable String id){
        return clientService.update(client,id)
                .map(c -> ResponseEntity.created(URI.create("/api/client".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletev2(@PathVariable String id){
        return clientService.delete(id).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND)));
    }

    @PostMapping("/")
    public Mono<ResponseEntity<Client>> save( @RequestBody Client client){
        if(client.getCreateAt()==null){
            client.setCreateAt(new Date());
        }
        if(client.getUpdateDate()== null){
            client.setUpdateDate(new Date());
        }
        return clientService.save(client).map(c -> ResponseEntity
                .created(URI.create("/api/client/".concat(c.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(c));
    }

    @PostMapping("/v2")
    public Mono<ResponseEntity<Map<String,Object>>> saveV2(@Valid @RequestBody Mono<Client> monoclient){
        Map<String,Object> respuesta = new HashMap<String,Object>();
        return monoclient.flatMap(client ->{
            if(client.getCreateAt()==null){
                client.setCreateAt(new Date());
        }
            return clientService.save(client).map(c -> {
                respuesta.put("client",c);
                respuesta.put("message","Cliente creado exitosamente");
                return ResponseEntity
                        .created(URI.create("/api/client/".concat(c.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(respuesta);
            });
        }).onErrorResume(t -> {
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(errors -> Flux.fromIterable(errors))
                    .map(e -> "El campo " + e.getField() + " " + e.getDefaultMessage())
                    .collectList()
                    .flatMap(list ->{
                        respuesta.put("errors",list);
                        respuesta.put("status",HttpStatus.BAD_REQUEST.value());
                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
                    });
        });
    }
    public void metodoAlternativo(){
        System.out.println("Falla en el sistema");
    }

}
