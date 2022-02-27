package com.example.cbrcurrency.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "calendar_dates")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDateEntity extends BaseEntity<Long> {
    @Temporal(TemporalType.DATE)
    @Column(name = "day", unique = true)
    private Calendar date;

    @OneToMany(mappedBy = "calendarDateEntity")
    private List<RateEntity> rateEntityList;
}
