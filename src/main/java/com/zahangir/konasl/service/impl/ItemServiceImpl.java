package com.zahangir.konasl.service.impl;

import com.zahangir.konasl.dto.Response;
import com.zahangir.konasl.model.Item;
import com.zahangir.konasl.dto.Rss;
import com.zahangir.konasl.repository.ItemRepository;
import com.zahangir.konasl.service.ItemService;
import com.zahangir.konasl.utils.QueryConstraint;
import com.zahangir.konasl.utils.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service("itemService")
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager entityManager;
    @Value("${external.api.url}")
    private String apiUrl;
    @Value("${query.limit}")
    private Integer limit;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void saveAll() {
        List<Item> itemList = fetchDataFromApi();
        if(itemList != null){
            itemList.forEach(item -> {
                int existCount = itemRepository.countByTitleAndLinkAndDescriptionAndGuidAndIsActiveTrue(item.getTitle(), item.getLink(), item.getDescription(), item.getGuid());
                if(existCount == 0){
                    item = itemRepository.save(item);
                }
            });
        }
    }

    @Override
    public List<Item> fetchDataFromApi() {
        try {
//            HttpURLConnection connection = HttpUrlConnectionUtils.getHttpURLConnection(apiUrl, "GET", null, null);
//            String content = HttpUrlConnectionUtils.getContent(connection);
            JAXBContext jaxbContext = JAXBContext.newInstance(Rss.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Rss rss = (Rss) jaxbUnmarshaller.unmarshal(new URL(apiUrl));
            if(rss != null && rss.getChannel() != null && rss.getChannel().getItem() != null && rss.getChannel().getItem().size() > 0){
                return rss.getChannel().getItem();
            }
            return null;
        } catch (IOException | JAXBException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Response getAllItems() {
        List<Item> items =  entityManager.createQuery(QueryConstraint.ItemManagement.GET_LAST_30_RECORDS, Item.class).setMaxResults(limit).getResultList();
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, items, "Item Retrieved successfully");
    }
}
