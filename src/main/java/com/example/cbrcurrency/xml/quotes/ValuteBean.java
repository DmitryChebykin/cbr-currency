package com.example.cbrcurrency.xml.quotes;

import lombok.*;

import javax.xml.bind.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@XmlRootElement(name = "Valute")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class ValuteBean {
    @XmlElement(name = "CharCode")
    String charCode = "";

    @XmlAttribute(name = "ID")
    String iD = "";

    @XmlElement(name = "Name")
    String name = "";

    @XmlElement(name = "Nominal")
    String nominal = "";

    @XmlElement(name = "NumCode")
    String numCode = "";

    @XmlElement(name = "Value")
    String value = "";
}