package com.ennbou.gestioncomptesoperations.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Compte {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long code;
  private double solde;
  @CreationTimestamp
  private LocalDateTime dateCreation;
  private String type;
  private boolean etat;
  private Long clientId;
  @Transient
  private Client client;
  @OneToMany(mappedBy="compte")
  private List<Operation> operations;
}
