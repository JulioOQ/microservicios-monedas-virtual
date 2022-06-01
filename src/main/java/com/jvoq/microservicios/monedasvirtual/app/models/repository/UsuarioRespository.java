package com.jvoq.microservicios.monedasvirtual.app.models.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jvoq.microservicios.monedasvirtual.app.models.entity.Usuario;

public interface UsuarioRespository extends ReactiveMongoRepository<Usuario, String>{
  
}
