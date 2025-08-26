package com.elasticsearch.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.elasticsearch.entity.Libro;
import com.elasticsearch.entity.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;



    @Autowired
    private ElasticsearchClient elasticsearchClient;

    //@GetMapping("/buscar")
    //public List<Libro> buscar(@RequestParam String q) throws IOException {
    //    SearchResponse<Libro> response = elasticsearchClient.search(s -> s
    //                   .index("libros")
    //                    .query(qb -> qb
    //                            .multiMatch(m -> m
    //                                    .query(q)
    //                                    .fields("titulo", "autor", "descripcion", "categorias")
    //                            )
    //                    ),
    //            Libro.class
    //    );
    //    return response.hits().hits().stream()
    //            .map(Hit::source)   // devuelve objetos Libro
    //            .collect(Collectors.toList());
    //}
    @GetMapping("/buscar")
    public List<Libro> buscar(@RequestParam String q) throws IOException {
        SearchResponse<Libro> response = elasticsearchClient.search(s -> s
                        .index("libros")
                        .query(qb -> qb
                                .multiMatch(m -> m
                                        .query(q)
                                        .type(TextQueryType.PhrasePrefix)
                                        .fields("titulo", "autor", "descripcion", "categorias")
                                )
                        ),
                Libro.class
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }


    @PostMapping("/agregar")
    public Libro agregarLibro(@RequestBody Libro libro) {
        return libroRepository.save(libro);
    }

}
