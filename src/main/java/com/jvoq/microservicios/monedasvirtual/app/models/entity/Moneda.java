package com.jvoq.microservicios.monedasvirtual.app.models.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("monedas_virtuales")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Moneda {
  
  @Id
  private String idMonedaVirtual;
  private String tipoMoneda;
  private Double tasaCompra;  
  private Double tasaVenta;


}
