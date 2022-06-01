package com.jvoq.microservicios.monedasvirtual.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jvoq.microservicios.monedasvirtual.app.models.entity.Usuario;
import com.jvoq.microservicios.monedasvirtual.app.models.repository.UsuarioRespository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioServiceImplement implements UsuarioService {
  
  @Autowired
  private UsuarioRespository usuarioRespository;

  @Override
  public Flux<Usuario> findAll() {
    return usuarioRespository.findAll();
  }

  @Override
  public Mono<Usuario> findById(String id) {
    return usuarioRespository.findById(id);
  }

  @Override
  public Mono<Usuario> save(Usuario usuario) {
    return usuarioRespository.save(usuario);
  }

  @Override
  public Mono<Usuario> update(Usuario usuario, String id) {
    
    return usuarioRespository.findById(id).flatMap(u ->{
      u.setCorreo(usuario.getCorreo());
      u.setNumCelular(usuario.getNumCelular());
      u.setNumDocumento(usuario.getNumDocumento());
      u.setTipoDocumento(usuario.getTipoDocumento());
      u.setIdMonedero(usuario.getIdMonedero());
      return this.save(u);
    });
  }

  @Override
  public Mono<Void> delete(Usuario usuario) {
    return usuarioRespository.delete(usuario);
  }
  

}
