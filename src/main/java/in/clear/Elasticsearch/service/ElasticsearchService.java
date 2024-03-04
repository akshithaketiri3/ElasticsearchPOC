package in.clear.Elasticsearch.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.clear.Elasticsearch.model.EqualFilter;
import in.clear.Elasticsearch.model.RangeFilter;
import in.clear.Elasticsearch.model.SearchDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthScope;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Service;


import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ElasticsearchService {
    public ElasticsearchService() throws Exception {
    }

    public BasicCredentialsProvider getCredentialsProvider(String username, String password){
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        return credentialsProvider;
    }

    BasicCredentialsProvider credentialsProvider = getCredentialsProvider("elastic", "jdspUBYJe=3kBEg+UuKa");

    public static RestHighLevelClient createHighLevelClient(BasicCredentialsProvider credentialsProvider) throws Exception {
//        SSLContext sslContext = SSLContextBuilder
//                .create()
//                .loadTrustMaterial(new File("/Users/ketiriakshitha/Downloads/elasticsearch-8.12.2/config/certs/http_ca.crt"), "Cleartax@321".toCharArray())
//                .build();

        SSLContext sslContext = SSLContext.getDefault();

        return new RestHighLevelClient(RestClient.builder(new org.apache.http.HttpHost("localhost", 9200, "http"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                                .setSSLContext(sslContext))
        );
    }

    RestHighLevelClient restClient = createHighLevelClient(credentialsProvider);


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

    public SearchResponse search(String name, SearchDTO searchDTO) throws IOException {


        SearchRequest searchRequest = new SearchRequest(name);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        for(EqualFilter ef :  searchDTO.getEqualFilters()){
            boolQueryBuilder.must(QueryBuilders.wildcardQuery(ef.getKey(), "*" + ef.getEqualsIn() + "*"));
        }


        for(RangeFilter rf : searchDTO.getRangeFilters()){
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(rf.getKey())
                    .gt(rf.getMin())
                    .lt(rf.getMax());
            boolQueryBuilder.filter(rangeQuery);
        }


        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(searchDTO.getSize());
        searchSourceBuilder.from((searchDTO.getPage() - 1) * searchDTO.getSize());
        searchRequest.source(searchSourceBuilder);


        SearchResponse searchResponse = restClient.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse;
    }

    public String count(String name) throws IOException {

        CountRequest countRequest = new CountRequest(name);
            CountResponse countResponse = restClient.count(countRequest, RequestOptions.DEFAULT);
            long totalCount = countResponse.getCount();
           return "Total number of documents in index '" + name + "': " + totalCount;
    }

    public SearchResponse globalSearch(String indexName, String text, String fieldName) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.regexpQuery(fieldName, ".*" + text + ".*"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restClient.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse;
    }


}
