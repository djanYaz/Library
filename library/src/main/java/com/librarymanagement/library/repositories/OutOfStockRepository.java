package com.librarymanagement.library.repositories;

import com.librarymanagement.library.entities.OutOfStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutOfStockRepository extends JpaRepository<OutOfStock, Long> {
}
