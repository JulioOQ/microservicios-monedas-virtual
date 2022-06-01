package com.jvoq.microservicios.monedasvirtual.app.controllers;

import java.net.URI;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jvoq.microservicios.monedasvirtual.app.models.entity.Transaccion;
import com.jvoq.microservicios.monedasvirtual.app.services.MonedaService;
import com.jvoq.microservicios.monedasvirtual.app.services.MonederoService;
import com.jvoq.microservicios.monedasvirtual.app.services.TransaccionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("transactions")
public class TransaccionController {

  @Autowired
  private TransaccionService transaccionService;

  @Autowired
  private MonedaService monedaService;

  @Autowired
  private MonederoService monederoService;

  @Autowired
  private StreamBridge streamBridge;

  @GetMapping
  public Mono<ResponseEntity<Flux<Transaccion>>> getAll() {
    return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(transaccionService.findAll()));
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Transaccion>> getById(@PathVariable String id) {
    return transaccionService.findById(id).map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(c))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Transaccion>> create(@RequestBody Transaccion transaccion) {
    return monedaService.findById(transaccion.getIdMoneda()).flatMap(m -> {
      transaccion.setFecha(new Date());
      Double total = m.getTasaVenta() * transaccion.getCantMonedaVirtual();
      transaccion.setMonto(total);

      return monederoService.findById(transaccion.getMonederoVOrigen()).flatMap(mo -> {
        Double origen = mo.getCantMonedaVirtual() - transaccion.getCantMonedaVirtual();
        mo.setCantMonedaVirtual(origen);
        monederoService.save(mo).subscribe();
        return monederoService.findById(transaccion.getMonederoVDestino()).flatMap(md -> {
          Double destino = md.getCantMonedaVirtual() + transaccion.getCantMonedaVirtual();
          md.setCantMonedaVirtual(destino);
          monederoService.save(md).subscribe();

          streamBridge.send("transaccion-out-0", transaccion);
          return transaccionService.save(transaccion);
        });
      });

    }).map(c -> ResponseEntity.created(URI.create("/currencies".concat(c.getIdTransaccion())))
        .contentType(MediaType.APPLICATION_JSON).body(c));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Transaccion>> update(@RequestBody Transaccion transaccion, @PathVariable String id) {
    return transaccionService.update(transaccion, id)
        .map(c -> ResponseEntity.created(URI.create("/currencies".concat(c.getIdTransaccion())))
            .contentType(MediaType.APPLICATION_JSON).body(c))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> drop(@PathVariable String id) {
    return transaccionService.findById(id).flatMap(c -> {
      return transaccionService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

}
