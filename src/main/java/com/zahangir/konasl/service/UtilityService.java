package com.zahangir.konasl.service;

import java.util.List;

public interface UtilityService {
    <S, T> List<T> mapList(List<S> source, Class<T> targetClass);
}
