package com.Asm.Utils;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Asm.Model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
    
}