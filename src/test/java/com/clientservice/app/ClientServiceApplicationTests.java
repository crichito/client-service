package com.clientservice.app;

import com.clientservice.app.controller.ClientController;
import com.clientservice.app.document.Client;
import com.clientservice.app.document.TypeClient;
import com.clientservice.app.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import java.util.Collections;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ClientController.class)
class ClientServiceApplicationTests {
	@MockBean
	ClientService clientService;
	@Autowired
	WebTestClient webTestClient;

	@Test
	public void addClientTest(){
		TypeClient typeClient = new TypeClient("1","Personal");
		Client client = new Client("12","Clever","Salvador",typeClient,"75858553","Jr. Andahuylas",new Date(),new Date());
		when(clientService.save(client)).thenReturn(Mono.just(client));
		webTestClient.post().uri("/api/client/")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(client),Client.class)
				.exchange()
				.expectStatus().isCreated();
	}
	@Test
	public void findAllTest(){
		TypeClient personal = new TypeClient("1","Personal");
		TypeClient bussiness = new TypeClient("2","Bussiness");
		Flux<Client> clientFlux = Flux.just(
				new Client("12","Clever","Salvador",personal,"75858553","Jr. Andahuylas",new Date(),new Date()),
				new Client("13","Ronaldo","Salvador",bussiness,"aff498996564","Jr. Andahuylas",new Date(),new Date())
		);
		when(clientService.findAll()).thenReturn(clientFlux);
		webTestClient.get().uri("/api/client/")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBodyList(Client.class);
	}

	@Test
	public void findByIdTest(){
		TypeClient personal = new TypeClient("1","Personal");
		Mono<Client> clientMono = Mono.just(new Client("12","Clever","Salvador",personal,"75858553","Jr. Andahuylas",new Date(),new Date()));
		when(clientService.findById(any())).thenReturn(clientMono);

		Flux<Client> responseBody = webTestClient.get().uri("/api/client/12")
				.exchange()
				.expectStatus().isOk()
				.returnResult(Client.class)
				.getResponseBody();
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(c -> c.getFirtName().equals("Clever"))
				.verifyComplete();
	}

	@Test
	public void updateTest(){
		TypeClient personal = new TypeClient("1","Personal");
		Client client = new Client("12","Clever","Salvador",personal,"75858553","Jr. Andahuylas",new Date(),new Date());
		when(clientService.update(client,"12")).thenReturn(Mono.just(client));

		webTestClient.put().uri("/api/client/v2/12")
				.body(Mono.just(client),Client.class)
				.exchange()
				.expectStatus().isCreated();
	}
	@Test
	public void deleteTest(){
		given(clientService.delete(any())).willReturn(Mono.empty());
		webTestClient.delete()
				.uri("/api/client/12")
				.exchange()
				.expectStatus().isNoContent();
	}
}
