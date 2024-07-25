package com.example.sportradardemo.domain.mapper;

public interface Mapper<K, V> {
    K mapToDomain(V dto);

    V mapToDTO(K domain);
}
