package com.example.cbrcurrency.service;

import com.example.cbrcurrency.dto.CurrencyDto;
import com.example.cbrcurrency.entity.CurrencyEntity;
import com.example.cbrcurrency.mapper.CurrencyMapper;
import com.example.cbrcurrency.repository.CurrencyEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyEntityRepository currencyEntityRepository;

    public List<CurrencyDto> getAllCurrencies() {
        List<CurrencyEntity> currencyEntities = currencyEntityRepository.findAll();
        List<CurrencyDto> currencyDtos = currencyEntities.stream().map(e -> {
            CurrencyDto target = CurrencyMapper.INSTANCE.toDto(e);
            return target;
        }).collect(Collectors.toList());

        return currencyDtos;
    }
}