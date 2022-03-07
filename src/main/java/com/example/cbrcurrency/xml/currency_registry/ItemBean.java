package com.example.cbrcurrency.xml.currency_registry;

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
    private String itemId = "";

    @XmlElement(name = "EngName")
    private String engName = "";

    @XmlElement(name = "Name")
    private String name = "";

    @XmlElement(name = "Nominal")
    private String nominal = "";

    @XmlElement(name = "ParentCode")
    private String parentCode = "";

    @XmlElement(name = "ISO_Num_Code")
    private String isoNumCode = "";

    @XmlElement(name = "ISO_Char_Code")
    private String isoCharCode = "";
}