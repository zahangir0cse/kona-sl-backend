package com.zahangir.konasl.dto;

import com.zahangir.konasl.model.Item;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

@Data
@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Channel {
    private String title;
    private int ttl;
    private String link;
    private String description;
    private String copyright;
    private String language;
    private String pubDate;
    private List<Item> item;
}
