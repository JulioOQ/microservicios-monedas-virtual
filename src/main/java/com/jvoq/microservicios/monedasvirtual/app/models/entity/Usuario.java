package com.jvoq.microservicios.monedasvirtual.app.models.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
  
  @Id
  private String idUsuario;
  private String tipoDocumento; 
  private String numDocumento; 
  private String numCelular;
  private String correo;
  private List<String> idMonedero;
  

}
