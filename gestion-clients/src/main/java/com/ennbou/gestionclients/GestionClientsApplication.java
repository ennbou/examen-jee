package com.ennbou.gestionclients;

import com.ennbou.gestionclients.dao.ClientRepository;
import com.ennbou.gestionclients.entities.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@SpringBootApplication
public class GestionClientsApplication {

  public static void main(String[] args) {
    SpringApplication.run(GestionClientsApplication.class, args);
  }

  @Bean
  CommandLineRunner start(ClientRepository clientRepository) {
    return args -> {
      clientRepository.save(new Client(null, "name1", "email1@gmail.com"));
      clientRepository.save(new Client(null, "name2", "email2@gmail.com"));
      clientRepository.save(new Client(null, "name3", "email3@gmail.com"));
      clientRepository.findAll().forEach(System.out::println);
    };
  }


  @Bean
  public RepositoryRestConfigurer repositoryRestConfigurer() {
    return RepositoryRestConfigurer.withConfig(config -> {
      config.exposeIdsFor(Client.class);
    });
  }

}
