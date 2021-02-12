package com.ennbou.gestioncomptesoperations;

import com.ennbou.gestioncomptesoperations.entities.Compte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@SpringBootApplication
@EnableFeignClients
public class GestionComptesOperationsApplication {

  public static void main(String[] args) {
    SpringApplication.run(GestionComptesOperationsApplication.class, args);
  }

  List<Compte> comptes = Arrays.asList(
          new Compte(1L,11000,LocalDateTime.now(),"EPARGNE",true,1L,null,new ArrayList<>()),
          new Compte(2L,12050,LocalDateTime.now(),"EPARGNE",true,2L,null,new ArrayList<>()),
          new Compte(3L,41070,LocalDateTime.now(),"COURANT",true,3L,null,new ArrayList<>()),
          new Compte(4L,1090,LocalDateTime.now(),"COURANT",true,4L,null,new ArrayList<>())
  );


@Bean
public Supplier<OperationK> sendOperation(){
  return ()->{
    Random r = new Random();
    int id = r.nextInt(comptes.size());
    String type = (r.nextInt(2) == 1) ? "CREDIT" :  "DEBIT";
    Compte c = comptes.get(id);
    double mountant = 100+r.nextDouble()*1000;

    OperationK o =new OperationK(1L, LocalDateTime.now(),mountant, type,c.getCode());
    System.out.println("send : "+o);
    return o;
  };
}

}

@Data @AllArgsConstructor @NoArgsConstructor
class OperationK {
  private Long numero;
  private LocalDateTime date;
  private double montant;
  private String type;
  private Long clienId;
}

