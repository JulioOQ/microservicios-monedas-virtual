package com.jvoq.microservicios.monedasvirtual.app.models.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jvoq.microservicios.monedasvirtual.app.models.entity.Monedero;

public interface MonederoRepository extends ReactiveMongoRepository<Monedero, String> {

}
