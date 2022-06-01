package com.jvoq.microservicios.monedasvirtual.app.services;

import com.jvoq.microservicios.monedasvirtual.app.models.entity.Transaccion;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransaccionService {

  public Flux<Transaccion> findAll();

  public Mono<Transaccion> findById(String id);

  public Mono<Transaccion> save(Transaccion transaccion );

  public Mono<Transaccion> update(Transaccion transaccion , String id);

  public Mono<Void> delete(Transaccion transaccion);
}
