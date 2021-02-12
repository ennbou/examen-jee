package com.ennbou.gestioncomptesoperations.dao;

import com.ennbou.gestioncomptesoperations.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CompteRepository extends JpaRepository<Compte, Long> {
  Compte findByClientId(Long idClient);
}

