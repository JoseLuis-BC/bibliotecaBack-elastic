package com.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

//    @Bean
//    public ElasticsearchClient elasticsearchClient() {
//        //RestClient restClient = RestClient.builder(new HttpHost("localhost", 9300)).build();
//		RestClient restClient = RestClient.builder(
//    new HttpHost("elasticsearch", 9200, "http")
//).build();
//
//        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//        return new ElasticsearchClient(transport);
//    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {

        // Configurar usuario y contraseña
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "cjLDY9dUJHFGISV7BlFMGK2k") // <- reemplaza aquí
        );

        // Crear RestClient con autenticación
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("7848657b6c8b482491ab51a0a45104bd.us-central1.gcp.cloud.es.io", 443, "https") // URL de Elastic Cloud
        ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        });

        // Transport
        RestClientTransport transport = new RestClientTransport(builder.build(), new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}
