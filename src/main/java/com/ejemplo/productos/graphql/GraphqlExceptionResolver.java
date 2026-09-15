package com.ejemplo.productos.graphql;

import java.util.List;
import java.util.Map;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import com.ejemplo.productos.exception.ProductoNoEncontradoException;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;

@Component
public class GraphqlExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected List<GraphQLError> resolveToMultipleErrors(
            Throwable exception,
            DataFetchingEnvironment environment) {
        if (exception instanceof ProductoNoEncontradoException) {
            return List.of(error(exception.getMessage(), "NOT_FOUND", environment));
        }
        if (exception instanceof ConstraintViolationException) {
            return List.of(error(exception.getMessage(), "BAD_REQUEST", environment));
        }
        return null;
    }

    private GraphQLError error(String mensaje, String codigo, DataFetchingEnvironment environment) {
        return GraphqlErrorBuilder.newError(environment)
                .message(mensaje)
                .extensions(Map.of("codigo", codigo))
                .build();
    }
}
