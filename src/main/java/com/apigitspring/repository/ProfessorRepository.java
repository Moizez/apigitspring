package com.apigitspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apigitspring.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>{ }
