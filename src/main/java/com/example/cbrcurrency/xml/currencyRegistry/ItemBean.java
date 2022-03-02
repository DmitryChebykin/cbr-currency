package com.example.cbrcurrency.xml.currencyRegistry;

import lombok.*;

import javax.xml.bind.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Getter
@Setter
public class ItemBean {
    @XmlAttribute(name = "ID")
    String itemId = "";

    @XmlElement(name = "EngName")
    String engName = "";

    @XmlElement(name = "Name")
    String name = "";

    @XmlElement(name = "Nominal")
    String nominal = "";

    @XmlElement(name = "ParentCode")
    String parentCode = "";

    @XmlElement(name = "ISO_Num_Code")
    String isoNumCode = "";

    @XmlElement(name = "ISO_Char_Code")
    String isoCharCode = "";
}