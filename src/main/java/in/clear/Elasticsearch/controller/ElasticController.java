package in.clear.Elasticsearch.controller;


import in.clear.Elasticsearch.model.SearchDTO;
import in.clear.Elasticsearch.service.ElasticsearchService;
import org.elasticsearch.action.search.SearchResponse;
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

   @GetMapping("/search")
    public SearchResponse search(@RequestParam("indexName") String name, @RequestBody SearchDTO searchDTO) throws IOException {
        return service.search(name, searchDTO);
   }


   @GetMapping("/getCount")
    public String count(@RequestParam("indexName") String name) throws IOException {
        return service.count(name);
   }

   @GetMapping("/globalSearch")
    public SearchResponse globalSearch(@RequestParam("indexName") String name, @RequestParam("text") String text, @RequestParam("fieldName") String fieldName) throws IOException {
        return service.globalSearch(name, text, fieldName);
   }






}
