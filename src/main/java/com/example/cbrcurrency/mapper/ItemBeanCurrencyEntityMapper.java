package com.example.cbrcurrency.mapper;

import com.example.cbrcurrency.entity.CurrencyEntity;
import com.example.cbrcurrency.xml.currencyRegistry.ItemBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ItemBeanCurrencyEntityMapper {
    @Mappings({
            @Mapping(target = "valuteId", expression = "java(item.getItemId())"),
            @Mapping(target = "rateEntity", ignore = true)
    })
    CurrencyEntity itemBeanToCurrencyEntity(ItemBean item);
}