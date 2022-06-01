package com.jvoq.microservicios.monedasvirtual.app.models.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("monederos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Monedero {
  
  @Id
  private String idMonedero; 
  private String nombreMonedero;
  private String idUsuario;
  private Double cantMonedaVirtual;
  private List<Moneda> monedas;

  

}
