package com.jvoq.microservicios.monedasvirtual.app.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.jvoq.microservicios.monedasvirtual.app.models.entity.Moneda;
import com.jvoq.microservicios.monedasvirtual.app.services.MonedaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("currencies")
public class MonedaController {
  
  @Autowired
  private MonedaService monedaService;
  
  @GetMapping
  public Mono<ResponseEntity<Flux<Moneda>>> getAll() {
    return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(monedaService.findAll()));
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Moneda>> getById(@PathVariable String id) {
    return monedaService.findById(id).map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(c))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Moneda>> create(@RequestBody Moneda clientDto) {
    return monedaService.save(clientDto).map(c -> ResponseEntity
        .created(URI.create("/currencies".concat(c.getIdMonedaVirtual()))).contentType(MediaType.APPLICATION_JSON).body(c));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Moneda>> update(@RequestBody Moneda moneda, @PathVariable String id) {
    return monedaService.update(moneda, id).map(c -> ResponseEntity
        .created(URI.create("/currencies".concat(c.getIdMonedaVirtual()))).contentType(MediaType.APPLICATION_JSON).body(c))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> drop(@PathVariable String id) {
    return monedaService.findById(id).flatMap(c -> {
      return monedaService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

}
