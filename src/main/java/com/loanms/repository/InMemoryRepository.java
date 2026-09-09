package com.loanms.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class InMemoryRepository<T, ID> implements Repository<T, ID> {
    private final Map<ID, T> data = new ConcurrentHashMap<>();
    private final Function<T, ID> idExtractor;

    public InMemoryRepository(Function<T, ID> idExtractor) {
        this.idExtractor = idExtractor;
    }

    public T save(T entity) {
        ID id = idExtractor.apply(entity);
        data.put(id, entity);
        return entity;
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(data.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public boolean deleteById(ID id) {
        return data.remove(id) != null;
    }

    public void clear() {
        data.clear();
    }

    public int size() {
        return data.size();
    }
}
