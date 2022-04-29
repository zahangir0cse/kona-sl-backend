package com.zahangir.konasl.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
public class Rss {
    private Channel channel;
    private double version;
    private String text;
}
