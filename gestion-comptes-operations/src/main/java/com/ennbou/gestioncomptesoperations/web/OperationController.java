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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/operations")
public class OperationController {

  final private ServiceClient serviceClient;
  final private CompteRepository compteRepository;
  final private OperationRepository operationRepository;

  public OperationController(ServiceClient serviceClient, CompteRepository compteRepository, OperationRepository operationRepository) {
    this.serviceClient = serviceClient;
    this.compteRepository = compteRepository;
    this.operationRepository = operationRepository;
  }

@PostMapping("/versement")
public Operation versement(@RequestBody OperationFrom form) {
  Client client = serviceClient.getClientById(form.getClientId());
  Compte compte = compteRepository.findByClientId(client.getCode());
  Operation operation = new Operation(null, LocalDateTime.now(), form.getMountant(), "DEBIT", compte);
  compte.setSolde(compte.getSolde() + form.getMountant());
  compteRepository.save(compte);
  return operationRepository.save(operation);
}

@PostMapping("/retrait")
public Operation retrait(@RequestBody OperationFrom form) {
  Client client = serviceClient.getClientById(form.getClientId());
  Compte compte = compteRepository.findByClientId(client.getCode());
  Operation operation = new Operation(null, LocalDateTime.now(), form.getMountant(), "CREDIT", compte);

    compte.setSolde(compte.getSolde() - form.getMountant());
  compteRepository.save(compte);
  return operationRepository.save(operation);
}


@PostMapping("/virement")
public String virement(@RequestBody CFrom form) {
  Client clientFrom = serviceClient.getClientById(form.getClientIdFrom());
  Client clientTo = serviceClient.getClientById(form.getClientIdTo());
  Compte compteFrom = compteRepository.findByClientId(clientFrom.getCode());
  Compte compteTo = compteRepository.findByClientId(clientTo.getCode());
  Operation operationCREDIT = new Operation(null, LocalDateTime.now(), form.getMountant(), "CREDIT", compteFrom);
  Operation operationDEBIT = new Operation(null, LocalDateTime.now(), form.getMountant(), "DEBIT", compteTo);
  compteFrom.setSolde(compteFrom.getSolde() - form.getMountant());
  compteTo.setSolde(compteTo.getSolde() + form.getMountant());
  operationRepository.save(operationCREDIT);
  operationRepository.save(operationDEBIT);
  return "Done";
}

@GetMapping("/{id}")
public Page<Operation> findAllOperation(@PathVariable(name = "id") Long clientId, @RequestParam(defaultValue = "0") int page,  @RequestParam(defaultValue = "10") int size ) {
  Compte compte = compteRepository.findByClientId(clientId);
  return operationRepository.findOperationByCompte(compte, PageRequest.of(page, size));
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
