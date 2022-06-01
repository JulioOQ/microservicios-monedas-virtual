package com.jvoq.microservicios.monedasvirtual.app.services;

import com.jvoq.microservicios.monedasvirtual.app.models.entity.Moneda;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MonedaService {
  
  public Flux<Moneda> findAll();

  public Mono<Moneda> findById(String id);

  public Mono<Moneda> save(Moneda moneda );

  public Mono<Moneda> update(Moneda moneda , String id);

  public Mono<Void> delete(Moneda moneda);
}
