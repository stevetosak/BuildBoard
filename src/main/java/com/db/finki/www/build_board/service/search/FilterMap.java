package com.db.finki.www.build_board.service.search;

import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.Tag;
import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class FilterMap<T extends NamedThread> {
    private final Map<String, Function<String, Specification<T>>> filterMap;

    public FilterMap() {
        filterMap = new HashMap<>();
        filterMap.put("title", (param) -> {
            if (param == null) {
                return (root, query, cb) -> null;
            } else {
                return (root, query, cb) -> cb.like(cb.lower(root.get("title")), param.toLowerCase() + "%");
            }
        });
        filterMap.put("content", (param) -> {
            if (param == null) {
                return (root, query, cb) -> null;
            } else {
                return (root, query, cb) -> cb.like(cb.lower(root.get("content")), "%" + param.toLowerCase() + "%");
            }
        });
        filterMap.put("all", (param) -> {
            Specification<T> spec = (root, query, cb) -> null;
            return spec.or(filterMap.get("title").apply(param)).or(filterMap.get("content").apply(param));
        });
    }

    public Function<String, Specification<T>> getFilter(String filter) {
        return filterMap.get(filter);
    }
}
