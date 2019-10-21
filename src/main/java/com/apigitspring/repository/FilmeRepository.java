package com.apigitspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apigitspring.model.Filme;


@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long>{

}
