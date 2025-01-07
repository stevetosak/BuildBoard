package com.db.finki.www.build_board.service.search;

import com.db.finki.www.build_board.entity.threads.interfaces.NamedThread;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Scope("prototype")
public class FilterMap<T extends NamedThread> {
    private final Map<String, Function<String, Specification<T>>> filterMap;
    public FilterMap() {
        filterMap = new HashMap<>();
        filterMap.put("all", s -> Specification.where(null));
        filterMap.put("title", (param) -> {
            if (param == null) {
                return (root, query, cb) -> null;
            } else {
                return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + param.toLowerCase() + "%");
            }
        });
        filterMap.put("content",(param) -> {
            if (param == null) {
                return (root, query, cb) -> null;
            } else {
                return (root, query, cb) -> cb.like(cb.lower(root.get("content")), "%" + param.toLowerCase() + "%");
            }
        });

    }
    public Function<String, Specification<T>> getFilter(String filter) {
        return filterMap.get(filter);
    }
}
