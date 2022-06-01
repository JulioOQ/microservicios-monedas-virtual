package com.jvoq.microservicios.monedasvirtual.app.services;

import com.jvoq.microservicios.monedasvirtual.app.models.entity.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsuarioService {

  public Flux<Usuario> findAll();

  public Mono<Usuario> findById(String id);

  public Mono<Usuario> save(Usuario usuario );

  public Mono<Usuario> update(Usuario usuario , String id);

  public Mono<Void> delete(Usuario usuario);
}
