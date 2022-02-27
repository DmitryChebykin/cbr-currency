package com.example.cbrcurrency.mapper;

import com.example.cbrcurrency.entity.CurrencyEntity;
import com.example.cbrcurrency.xml.currencyThesaurus.ItemBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ItemBeanCurrencyEntityMapper {
    @Mappings({
            @Mapping(target = "valuteId", expression = "java(item.getItemId())"),
            @Mapping(target = "rateEntity", ignore = true)
    })
    CurrencyEntity itemBeanToCurrencyEntity(ItemBean item);
}