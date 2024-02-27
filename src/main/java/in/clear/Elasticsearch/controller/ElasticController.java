package in.clear.Elasticsearch.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import in.clear.Elasticsearch.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


@RestController
public class ElasticController {

    @Autowired
    ElasticsearchService service;

    @PostMapping("/createIndex")
   public String createIndex(@RequestParam("name") String indexName, @RequestBody Map<String, Object>fields) throws IOException {

       return service.createIndex(indexName, fields);
   }






}
