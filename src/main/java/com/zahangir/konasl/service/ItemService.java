package com.zahangir.konasl.service;

import com.zahangir.konasl.dto.Response;
import com.zahangir.konasl.model.Item;

import java.util.List;

public interface ItemService {
    void saveAll();
    List<Item> fetchDataFromApi();

    Response getAllItems();
}
