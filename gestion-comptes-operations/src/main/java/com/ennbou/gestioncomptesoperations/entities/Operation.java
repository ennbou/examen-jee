package com.ennbou.gestioncomptesoperations.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Operation {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long numero;
  private LocalDateTime date;
  private double montant;
  private String type; // DEBIT ou CREDIT
  @JsonIgnore
  @ManyToOne
  private Compte compte;
}
