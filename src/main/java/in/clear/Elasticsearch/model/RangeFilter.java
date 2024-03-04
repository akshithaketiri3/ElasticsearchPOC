package in.clear.Elasticsearch.model;

import lombok.Data;

@Data
public class RangeFilter {
    private String key;
    private String min;
    private String max;
}
