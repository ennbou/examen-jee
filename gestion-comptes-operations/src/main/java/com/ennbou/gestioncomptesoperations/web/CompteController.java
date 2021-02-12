package com.ennbou.gestioncomptesoperations.web;

import com.ennbou.gestioncomptesoperations.dao.CompteRepository;
import com.ennbou.gestioncomptesoperations.entities.Client;
import com.ennbou.gestioncomptesoperations.entities.Compte;
import com.ennbou.gestioncomptesoperations.feign.ServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comptes")
public class CompteController {

  final private ServiceClient serviceClient;
  final private CompteRepository compteRepository;

  public CompteController(ServiceClient serviceClient, CompteRepository compteRepository) {
    this.serviceClient = serviceClient;
    this.compteRepository = compteRepository;
  }


@GetMapping("/client/{id}")
public Compte getCompte(@PathVariable Long id) {
  Client client = serviceClient.getClientById(id);
  Compte compte = compteRepository.findByClientId(id);
  compte.setClient(client);
  return compte;
}

@GetMapping("/client/{id}/change-etat")
public Boolean compteStatu(@PathVariable Long id) {
  Compte compte = compteRepository.findByClientId(id);
  compte.setEtat(!compte.isEtat());
  Compte c = compteRepository.save(compte);
  return c.isEtat();
}

}

