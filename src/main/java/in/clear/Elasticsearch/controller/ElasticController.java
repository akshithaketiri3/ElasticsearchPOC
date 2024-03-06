package in.clear.Elasticsearch.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import in.clear.Elasticsearch.model.SearchDTO;
import in.clear.Elasticsearch.service.ElasticsearchService;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.get.GetResult;
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

//   @GetMapping("/globalSearch")
//    public SearchResponse globalSearch(@RequestParam("indexName") String name, @RequestParam("text") String text, @RequestParam("fieldName") String fieldName) throws IOException {
//        return service.globalSearch(name, text, fieldName);
//   }

   @PostMapping("/createDocument")
    public IndexResponse createDocument(@RequestParam("indexName") String indexName, @RequestBody Map<String, Object>fields) throws IOException {
       ObjectMapper objectMapper = new ObjectMapper();
       String jsonString = objectMapper.writeValueAsString(fields);
        return service.createDocument(indexName, jsonString);
   }

   @DeleteMapping("/deleteDocument")
    public DeleteResponse deleteDocument(@RequestParam("indexName") String indexName, @RequestParam("id") String id) throws IOException {
        return service.deleteDocument(indexName, id);
   }

   @PutMapping("/updateDocument")
    public UpdateResponse updateDocument(@RequestParam("indexName") String indexName, @RequestParam("id") String id, @RequestBody Map<String, Object>fields) throws IOException {
       ObjectMapper objectMapper = new ObjectMapper();
       String jsonString = objectMapper.writeValueAsString(fields);
        return service.updateDocument(indexName, id, jsonString);
   }








}
