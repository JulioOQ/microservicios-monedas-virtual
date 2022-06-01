package com.jvoq.microservicios.monedasvirtual.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jvoq.microservicios.monedasvirtual.app.models.entity.Monedero;
import com.jvoq.microservicios.monedasvirtual.app.models.repository.MonederoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MonederoServiceImplement implements MonederoService{
  
  @Autowired
  private MonederoRepository monederoRepository;

  @Override
  public Flux<Monedero> findAll() {
    return monederoRepository.findAll();
  }

  @Override
  public Mono<Monedero> findById(String id) {
    return monederoRepository.findById(id);
  }

  @Override
  public Mono<Monedero> save(Monedero monedero) {
    return monederoRepository.save(monedero);
  }

  @Override
  public Mono<Monedero> update(Monedero monedero, String id) {
    return monederoRepository.findById(id).flatMap(m -> {
      m.setNombreMonedero(monedero.getNombreMonedero());
      m.setMonedas(monedero.getMonedas());   
      m.setIdUsuario(monedero.getIdUsuario());
      m.setCantMonedaVirtual(monedero.getCantMonedaVirtual());
      return this.save(m);
    });
  }

  @Override
  public Mono<Void> delete(Monedero monedero) {
    return monederoRepository.delete(monedero);
  }

}
