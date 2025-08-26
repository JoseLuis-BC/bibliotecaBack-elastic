package com.elasticsearch.entity;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LibroRepository extends ElasticsearchRepository<Libro, String> {
    List<Libro> findByTituloContaining(String titulo);
}
