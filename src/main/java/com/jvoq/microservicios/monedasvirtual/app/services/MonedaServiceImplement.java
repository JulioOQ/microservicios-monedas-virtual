package com.jvoq.microservicios.monedasvirtual.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jvoq.microservicios.monedasvirtual.app.models.entity.Moneda;
import com.jvoq.microservicios.monedasvirtual.app.models.repository.MonedaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MonedaServiceImplement implements MonedaService {

  @Autowired
  private MonedaRepository monedaRepository;

  @Override
  public Flux<Moneda> findAll() {
    return monedaRepository.findAll();
  }

  @Override
  public Mono<Moneda> findById(String id) {
    return monedaRepository.findById(id);
  }

  @Override
  public Mono<Moneda> save(Moneda moneda) {
    return monedaRepository.save(moneda);
  }

  @Override
  public Mono<Moneda> update(Moneda moneda, String id) {
    return monedaRepository.findById(id).flatMap(m -> {
      m.setTasaCompra(moneda.getTasaCompra());
      m.setTipoMoneda(moneda.getTipoMoneda());
      m.setTasaVenta(moneda.getTasaVenta());
      return this.save(m);
    });
  }

  @Override
  public Mono<Void> delete(Moneda moneda) {
    return monedaRepository.delete(moneda);
  }

}
