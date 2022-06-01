package com.jvoq.microservicios.monedasvirtual.app.models.entity;



import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("transacciones")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transaccion {
  
  @Id
  private String idTransaccion;
  private String monederoVOrigen;
  private String monederoVDestino;
  private String medioPago;
  private Double cantMonedaVirtual;
  //se calcula solo
  private Double monto;
  private String numeroTransaccion;
  private String idCuenta;
  private String idMonederoMovil;
  private String idMoneda;
  private Date fecha;


}
