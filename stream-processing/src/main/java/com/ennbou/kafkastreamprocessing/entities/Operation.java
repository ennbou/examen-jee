package com.ennbou.kafkastreamprocessing.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
  private Long numero;
  private String date;
  private double montant;
  private String type;
  private Long clienId;
}
