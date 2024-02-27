package in.clear.Elasticsearch.service;


import co.elastic.clients.elasticsearch.ElasticsearchClient;

import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.rest.RestHandler;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Service;


import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ElasticsearchService {
    public ElasticsearchService() throws Exception {
    }


//    private RestTemplate restTemplate = new RestTemplate();
//
//    private String url = "https://localhost:9200";
//    private String username = "elastic";
//    private String password = "*4kiD_gbmFS*yTP4ZMZi";
//
//    private ObjectMapper objectMapper = new ObjectMapper();


    public BasicCredentialsProvider getCredentialsProvider(String username, String password){
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        return credentialsProvider;
    }

    BasicCredentialsProvider credentialsProvider = getCredentialsProvider("elastic", "jdspUBYJe=3kBEg+UuKa");

    public static RestHighLevelClient createHighLevelClient(BasicCredentialsProvider credentialsProvider) throws Exception {
//        SSLContext sslContext = SSLContextBuilder
//                .create()
//                .loadTrustMaterial(new File("/Users/ketiriakshitha/Downloads/elasticsearch-8.12.2/config/certs/http_ca.crt"))
//                .build();

        SSLContext sslContext = SSLContext.getDefault();

        return new RestHighLevelClient(RestClient.builder(new org.apache.http.HttpHost("localhost", 9200, "https"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                                .setSSLContext(sslContext))
        );
    }

    RestHighLevelClient restClient = createHighLevelClient(credentialsProvider);

    ElasticsearchTransport transport = new RestClientTransport(restClient.getLowLevelClient(), new JacksonJsonpMapper());
    ElasticsearchClient client = new ElasticsearchClient(transport);


    public String createIndex(String indexName, Map<String, Object>fields) throws JsonProcessingException ,IOException, ElasticsearchException {
        Map<String, Object> properties = new HashMap<>();
        properties.put("properties", fields);
        Map<String, Object> mappings = new HashMap<>();
        mappings.put("mappings", properties);
        String map = new ObjectMapper().writeValueAsString(mappings);
        BytesArray source = new BytesArray(map);
        String response = "";
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.source(source, XContentType.JSON);
        try {
            CreateIndexResponse createIndexResponse = restClient.indices().create(request, RequestOptions.DEFAULT);
            if (createIndexResponse.isAcknowledged()) {
                response = "Index created successfully: " + indexName;
            } else {
                response = "Failed to create index: " + indexName;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return response;
    }

//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(username, password);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//                Map<String, Object>properties = new HashMap<>();
//        properties.put("properties", fields);
//        Map<String, Object>mappings = new HashMap<>();
//        mappings.put("mappings", properties);
//        String map = new ObjectMapper().writeValueAsString(mappings);
//        BytesArray source = new BytesArray(map);
//        CreateIndexRequest request = new CreateIndexRequest(indexName);
//        request.source(source, XContentType.JSON);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("indexName", indexName);
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(request), headers);
//
//        String uriWithParams = url + "?" + "indexName" + "=" + indexName;
//
//
//        RestTemplate restTemplate = new RestTemplate();
//
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                uriWithParams,
//                HttpMethod.PUT,
//                requestEntity,
//                String.class
//        );
//
//
//        return response.getBody();
//    }


}
