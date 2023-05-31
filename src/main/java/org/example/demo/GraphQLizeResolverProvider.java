package org.example.demo;

import org.graphqlize.java.GraphQLizeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Component
public class GraphQLizeResolverProvider {
    @Resource
    private final DataSource dataSource;
    private GraphQLizeResolver graphQLizeResolver;

    public GraphQLizeResolverProvider(DataSource dataSource) {
        this.dataSource = dataSource;
        this.graphQLizeResolver = new GraphQLizeResolver(dataSource);

    }

    @Bean
    public GraphQLizeResolver graphQLizeResolver() {
        return this.graphQLizeResolver;
    }
}
