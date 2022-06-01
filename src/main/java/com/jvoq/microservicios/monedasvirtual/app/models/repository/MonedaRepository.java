package com.jvoq.microservicios.monedasvirtual.app.models.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jvoq.microservicios.monedasvirtual.app.models.entity.Moneda;

public interface MonedaRepository extends ReactiveMongoRepository<Moneda, String>{

}
