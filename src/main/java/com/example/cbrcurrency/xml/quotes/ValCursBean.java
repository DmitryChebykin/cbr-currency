package com.example.cbrcurrency.xml.quotes;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@XmlRootElement(name = "ValCurs", namespace = "")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValCursBean {
    @XmlAttribute(name = "Date")
    String date = "";

    @XmlAttribute(name = "name")
    String name = "";

    @XmlElement(name = "Valute", type = ValuteBean.class)
    List<ValuteBean> valuteBeanList;
}