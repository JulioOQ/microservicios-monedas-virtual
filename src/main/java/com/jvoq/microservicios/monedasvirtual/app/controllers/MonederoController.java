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
import com.jvoq.microservicios.monedasvirtual.app.models.entity.Monedero;
import com.jvoq.microservicios.monedasvirtual.app.services.MonederoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("wallets")
public class MonederoController {

  @Autowired
  private MonederoService monederoService;

  @GetMapping
  public Mono<ResponseEntity<Flux<Monedero>>> getAll() {
    return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(monederoService.findAll()));
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Monedero>> getById(@PathVariable String id) {
    return monederoService.findById(id).map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(c))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Monedero>> create(@RequestBody Monedero monedero) {
    return monederoService.save(monedero).map(c -> ResponseEntity
        .created(URI.create("/clients".concat(c.getIdMonedero()))).contentType(MediaType.APPLICATION_JSON).body(c));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Monedero>> update(@RequestBody Monedero monedero, @PathVariable String id) {
    return monederoService.update(monedero, id).map(c -> ResponseEntity
        .created(URI.create("/clients".concat(c.getIdMonedero()))).contentType(MediaType.APPLICATION_JSON).body(c))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> drop(@PathVariable String id) {
    return monederoService.findById(id).flatMap(c -> {
      return monederoService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

}
