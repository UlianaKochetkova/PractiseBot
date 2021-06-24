package com.example.FirstBot.entities;

import org.springframework.data.repository.CrudRepository;

/**
 * Репозиторий, благодаря которому можно будет взаимодействовать с БД
 */
public interface VisitorRepo extends CrudRepository<Visitor, Integer> {
}
