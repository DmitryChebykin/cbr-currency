package com.example.cbrcurrency.xml.currencyRegistry;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@XmlRootElement(name = "Valuta", namespace = "")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class ValutaBean {
    @XmlElement(name = "Item", type = ItemBean.class)
    List<ItemBean> itemBean;
}