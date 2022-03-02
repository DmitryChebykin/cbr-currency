package com.example.cbrcurrency.util;

import com.querydsl.core.types.EntityPath;
import com.querydsl.sql.RelationalPath;
import com.querydsl.sql.RelationalPathBase;

import javax.persistence.Table;
import java.lang.reflect.AnnotatedElement;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class QueryDslUtils {

    /**
     * Simple Type cache (If you want another cache strategy, make it your own.)
     */
    private static final ConcurrentMap<EntityPath<?>, RelationalPath<?>> relationalMap = new ConcurrentHashMap<>();

    /**
     * Entity Class to SQLQueryFactory RelationalPath
     *
     * @param entityPath
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> RelationalPath<T> asRelational(EntityPath<T> entityPath) {
        AnnotatedElement annotatedElement = Objects.requireNonNull(Objects.requireNonNull(entityPath, "entityPath is null").getAnnotatedElement(), "no annotation");
        Table table = Objects.requireNonNull(annotatedElement.getAnnotation(Table.class), "no entity table");
        RelationalPath<?> result = relationalMap.get(entityPath);
        if (result == null)
            relationalMap.put(entityPath, result = new RelationalPathBase<T>(entityPath.getType(), entityPath.getMetadata(), table.schema(), table.name()));
        return (RelationalPath<T>) result;
    }
}
