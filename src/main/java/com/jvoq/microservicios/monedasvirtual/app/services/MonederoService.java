package com.jvoq.microservicios.monedasvirtual.app.services;

import com.jvoq.microservicios.monedasvirtual.app.models.entity.Monedero;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MonederoService {
  
  public Flux<Monedero> findAll();

  public Mono<Monedero> findById(String id);

  public Mono<Monedero> save(Monedero Monedero);

  public Mono<Monedero> update(Monedero Monedero, String id);

  public Mono<Void> delete(Monedero Monedero);

}
