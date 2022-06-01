package com.jvoq.microservicios.monedasvirtual.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jvoq.microservicios.monedasvirtual.app.models.entity.Transaccion;
import com.jvoq.microservicios.monedasvirtual.app.models.repository.TransaccionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransaccionServiceImplement implements TransaccionService {

  @Autowired
  private TransaccionRepository transaccionRepository;

  @Override
  public Flux<Transaccion> findAll() {
    return transaccionRepository.findAll();
  }

  @Override
  public Mono<Transaccion> findById(String id) {
    return transaccionRepository.findById(id);
  }

  @Override
  public Mono<Transaccion> save(Transaccion transaccion) {
    return transaccionRepository.save(transaccion);

  }

  @Override
  public Mono<Transaccion> update(Transaccion transaccion, String id) {

    return transaccionRepository.findById(id).flatMap(t -> {
      t.setMonederoVOrigen(transaccion.getMonederoVOrigen());
      t.setMonederoVDestino(transaccion.getMonederoVDestino());
      t.setMedioPago(transaccion.getMedioPago());
      t.setMonto(transaccion.getMonto());
      t.setNumeroTransaccion(transaccion.getNumeroTransaccion());
      t.setCantMonedaVirtual(transaccion.getCantMonedaVirtual());
      t.setIdCuenta(transaccion.getIdCuenta());
      t.setIdMoneda(transaccion.getIdMoneda());
      t.setIdMonederoMovil(transaccion.getIdCuenta());
      return this.save(transaccion);
    });
  }

  @Override
  public Mono<Void> delete(Transaccion transaccion) {
    return transaccionRepository.delete(transaccion);
  }

}
