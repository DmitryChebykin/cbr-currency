package com.example.cbrcurrency.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "calendar_dates")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDateEntity extends BaseEntity<Long> {
    @Temporal(TemporalType.DATE)
    @Column(name = "date", unique = true)
    private Calendar date;

    @OneToMany(mappedBy = "calendarDateEntity")
    private List<RateEntity> rateEntityList;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        CalendarDateEntity that = (CalendarDateEntity) o;

        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return 1012231433;
    }
}
