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
    private String charCode = "";

    @XmlAttribute(name = "ID")
    private String iD = "";

    @XmlElement(name = "Name")
    private String name = "";

    @XmlElement(name = "Nominal")
    private String nominal = "";

    @XmlElement(name = "NumCode")
    private String numCode = "";

    @XmlElement(name = "Value")
    private String value = "";
}