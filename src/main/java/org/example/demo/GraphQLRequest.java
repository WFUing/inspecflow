package org.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class GraphQLRequest {
    private String query;
    private Map<String, Object> variables;
}
