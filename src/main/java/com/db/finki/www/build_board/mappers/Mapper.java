package com.db.finki.www.build_board.mappers;

import java.util.List;


public interface Mapper<T,S> {
    S toDTO(T entity);
    List<S> toDTO(List<T> entities);

    T fromDTO(S dto);
    List<T> fromDTO(List<S> dtos);
}
