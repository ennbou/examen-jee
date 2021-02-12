package com.ennbou.gestioncomptesoperations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GestionComptesOperationsApplication {

  public static void main(String[] args) {
    SpringApplication.run(GestionComptesOperationsApplication.class, args);
  }

}
