package com.example.cbrcurrency.mapper;

import com.example.cbrcurrency.dto.CurrencyDto;
import com.example.cbrcurrency.entity.CurrencyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrencyMapper extends EntityMapper<CurrencyDto, CurrencyEntity> {
    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);
}