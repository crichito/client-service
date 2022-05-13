package com.clientservice.app;

import com.clientservice.app.document.Client;
import com.clientservice.app.document.TypeClient;
import com.clientservice.app.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.Date;
import java.util.UUID;
@EnableEurekaClient
@SpringBootApplication
public class ClientServiceApplication /*implements CommandLineRunner*/ {

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
	}

}
