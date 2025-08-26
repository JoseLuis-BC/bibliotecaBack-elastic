package com.elasticsearch.entity;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public void run(String... args) throws Exception {
        List<Libro> libros = List.of(
                new Libro("1", "Cien años de soledad", "Gabriel García Márquez", "Una novela sobre la familia Buendía en Macondo.", List.of("Novela", "Realismo mágico")),
                new Libro("2", "El Principito", "Antoine de Saint-Exupéry", "Un cuento filosófico sobre un niño que viaja por planetas.", List.of("Infantil", "Filosofía")),
                new Libro("3", "Don Quijote de la Mancha", "Miguel de Cervantes", "Las aventuras de un caballero loco y su escudero.", List.of("Clásico", "Novela")),
                new Libro("4", "1984", "George Orwell", "Una distopía sobre vigilancia y control totalitario.", List.of("Distopía", "Política")),
                new Libro("5", "La Odisea", "Homero", "La épica travesía de Odiseo para regresar a Ítaca.", List.of("Épico", "Mitología")),
                new Libro("6", "Harry Potter y la piedra filosofal", "J.K. Rowling", "El inicio de la saga del joven mago Harry Potter.", List.of("Fantasía", "Juvenil"))
        );

        for (Libro libro : libros) {
            elasticsearchClient.index(i -> i
                    .index("libros")
                    .id(libro.getId())
                    .document(libro)
            );
        }

        System.out.println("Libros indexados en Elasticsearch.");
    }
}
