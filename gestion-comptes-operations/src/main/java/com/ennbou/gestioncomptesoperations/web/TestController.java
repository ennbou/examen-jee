package com.ennbou.gestioncomptesoperations.web;

import com.ennbou.gestioncomptesoperations.dao.CompteRepository;
import com.ennbou.gestioncomptesoperations.dao.OperationRepository;
import com.ennbou.gestioncomptesoperations.entities.Client;
import com.ennbou.gestioncomptesoperations.entities.Compte;
import com.ennbou.gestioncomptesoperations.entities.Operation;
import com.ennbou.gestioncomptesoperations.feign.ServiceClient;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TestController {

  final private ServiceClient serviceClient;
  final private CompteRepository compteRepository;
  final private OperationRepository operationRepository;

  public TestController(ServiceClient serviceClient, CompteRepository compteRepository, OperationRepository operationRepository) {
    this.serviceClient = serviceClient;
    this.compteRepository = compteRepository;
    this.operationRepository = operationRepository;
  }

  @PostMapping("/operation/{type}")
  public Operation versement(@RequestBody OperationFrom form, @PathVariable String type) {
    Client client = serviceClient.getClientById(form.getClientId());
    Compte compte = compteRepository.findByClientId(client.getCode());
    Operation operation = new Operation(null, LocalDateTime.now(), form.getMountant(), type.toUpperCase(), compte);
    if (type.equals("credit")) {
      compte.setSolde(compte.getSolde() - form.getMountant());
    } else {
      compte.setSolde(compte.getSolde() + form.getMountant());
    }
    compteRepository.save(compte);
    Operation o = operationRepository.save(operation);
    return o;
  }


  @PostMapping("/virement")
  public String versement(@RequestBody CFrom form) {
    Client clientFrom = serviceClient.getClientById(form.getClientIdFrom());
    Client clientTo = serviceClient.getClientById(form.getClientIdTo());
    Compte compteFrom = compteRepository.findByClientId(clientFrom.getCode());
    Compte compteTo = compteRepository.findByClientId(clientTo.getCode());
    Operation operationCREDIT = new Operation(null, LocalDateTime.now(), form.getMountant(), "CREDIT", compteFrom);
    Operation operationDEBIT = new Operation(null, LocalDateTime.now(), form.getMountant(), "DEBIT", compteTo);
    compteFrom.setSolde(compteFrom.getSolde() - form.getMountant());
    compteTo.setSolde(compteTo.getSolde() + form.getMountant());
    Operation o1 = operationRepository.save(operationCREDIT);
    Operation o2 = operationRepository.save(operationDEBIT);
    return "Done";
  }

  @GetMapping("/compte/client/{id}")
  public Compte getCompte(@PathVariable Long id){
    Client client = serviceClient.getClientById(id);
    Compte compte = compteRepository.findByClientId(id);
    compte.setClient(client);
    return compte;
  }

  @GetMapping("/compte/client/{id}/change-etat")
  public Boolean compteStatu(@PathVariable Long id){
    Compte compte = compteRepository.findByClientId(id);
    compte.setEtat(!compte.isEtat());
    Compte c = compteRepository.save(compte);
    return c.isEtat();
  }

  @GetMapping("/alloperations/{id}")
  public Page<Operation> findAllOperation(@PathVariable(name="id") Long clientId){
    Compte compte = compteRepository.findByClientId(clientId);
    Page<Operation> operations = operationRepository.findOperationByCompte(compte, PageRequest.of(0,10));
    return operations;
  }


  @GetMapping("/clients/{id}")
  public Client getCLientById(@PathVariable(value = "id") Long id) {
    return serviceClient.getClientById(id);
  }
}

@Data
class OperationFrom {
  private Long clientId;
  private double mountant;
}


@Data
class CFrom {
  private Long clientIdFrom;
  private Long clientIdTo;
  private double mountant;
}

