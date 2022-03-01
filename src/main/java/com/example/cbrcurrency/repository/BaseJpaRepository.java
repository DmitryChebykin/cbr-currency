package com.example.cbrcurrency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseJpaRepository<T, I>
        extends JpaRepository<T, I>,
        JpaSpecificationExecutor<T>,
        QuerydslPredicateExecutor<T> {
}
