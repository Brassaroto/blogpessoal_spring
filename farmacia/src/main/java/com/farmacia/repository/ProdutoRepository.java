package com.farmacia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.farmacia.model.Produto;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllByCategoriaId(Long categoriaId);
}
