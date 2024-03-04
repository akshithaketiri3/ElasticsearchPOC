package in.clear.Elasticsearch.model;

import lombok.Data;

import java.util.List;

@Data

public class EqualFilter {

    private String key;
    private String equalsIn;
}
