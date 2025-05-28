package com.farmacia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.farmacia.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
