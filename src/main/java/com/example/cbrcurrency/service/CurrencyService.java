package com.example.cbrcurrency.service;

import com.example.cbrcurrency.dto.CurrencyDto;
import com.example.cbrcurrency.entity.CurrencyEntity;
import com.example.cbrcurrency.entity.QCurrencyEntity;
import com.example.cbrcurrency.mapper.CurrencyMapper;
import com.example.cbrcurrency.repository.CurrencyEntityRepository;
import com.example.cbrcurrency.service.exception.CurrencyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyEntityRepository currencyEntityRepository;

    @Transactional(readOnly = true)
    public List<CurrencyDto> getAllCurrencies() {
        List<CurrencyEntity> currencyEntities = currencyEntityRepository.findAll();
        return currencyEntities.stream().map(CurrencyMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CurrencyDto getCurrencyByEngName(String engName) {
        QCurrencyEntity qCurrencyEntity = QCurrencyEntity.currencyEntity;
        CurrencyEntity currencyEntity = currencyEntityRepository.findOne(qCurrencyEntity.engName.eq(engName)).orElseThrow(CurrencyNotFoundException::new);

        return CurrencyMapper.INSTANCE.toDto(currencyEntity);
    }

    @Transactional(readOnly = true)
    public CurrencyDto getCurrencyByName(String name) {
        QCurrencyEntity qCurrencyEntity = QCurrencyEntity.currencyEntity;
        CurrencyEntity currencyEntity = currencyEntityRepository.findOne(qCurrencyEntity.name.eq(name)).orElseThrow(CurrencyNotFoundException::new);

        return CurrencyMapper.INSTANCE.toDto(currencyEntity);
    }

    @Transactional(readOnly = true)
    public CurrencyDto getCurrencyByIsoCharCode(String charCode) {
        QCurrencyEntity qCurrencyEntity = QCurrencyEntity.currencyEntity;
        CurrencyEntity currencyEntity = currencyEntityRepository.findOne(qCurrencyEntity.isoCharCode.eq(charCode)).orElseThrow(CurrencyNotFoundException::new);

        return CurrencyMapper.INSTANCE.toDto(currencyEntity);
    }

    @Transactional(readOnly = true)
    public CurrencyDto getCurrencyByValuteId(String valuteId) {
        CurrencyEntity currencyEntity = currencyEntityRepository.findByValuteId(valuteId).orElseThrow(CurrencyNotFoundException::new);

        return CurrencyMapper.INSTANCE.toDto(currencyEntity);
    }
}