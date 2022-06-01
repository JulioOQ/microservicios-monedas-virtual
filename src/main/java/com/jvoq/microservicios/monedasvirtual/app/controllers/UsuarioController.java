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
import com.jvoq.microservicios.monedasvirtual.app.models.entity.Usuario;
import com.jvoq.microservicios.monedasvirtual.app.services.UsuarioService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("users")
public class UsuarioController {
  
  @Autowired
  private UsuarioService usuarioService;

  
  @GetMapping
  public Mono<ResponseEntity<Flux<Usuario>>> getAll() {
    return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(usuarioService.findAll()));
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Usuario>> getById(@PathVariable String id) {
    return usuarioService.findById(id).map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(c))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Usuario>> create(@RequestBody Usuario usuario) {
    return usuarioService.save(usuario).map(c -> ResponseEntity
        .created(URI.create("/users".concat(c.getIdUsuario()))).contentType(MediaType.APPLICATION_JSON).body(c));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Usuario>> update(@RequestBody Usuario usuario, @PathVariable String id) {
    return usuarioService.update(usuario, id).map(c -> ResponseEntity
        .created(URI.create("/users".concat(c.getIdUsuario()))).contentType(MediaType.APPLICATION_JSON).body(c))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> drop(@PathVariable String id) {
    return usuarioService.findById(id).flatMap(c -> {
      return usuarioService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }


}
