package com.zahangir.konasl.repository;

import com.zahangir.konasl.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    int countByTitleAndLinkAndDescriptionAndGuidAndIsActiveTrue(String title, String link, String description, String guid);
}
