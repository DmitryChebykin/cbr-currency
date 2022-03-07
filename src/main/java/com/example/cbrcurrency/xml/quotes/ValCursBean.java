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
    private String date = "";

    @XmlAttribute(name = "name")
    private String name = "";

    @XmlElement(name = "Valute", type = ValuteBean.class)
    private List<ValuteBean> valuteBeanList;
}