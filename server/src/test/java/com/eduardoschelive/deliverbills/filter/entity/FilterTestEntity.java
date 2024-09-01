package com.eduardoschelive.deliverbills.filter.entity;

import com.eduardoschelive.deliverbills.filter.annotation.Filterable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "filter_test_table")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
public class FilterTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Filterable
    @Column(name = "stringField", nullable = false)
    private String stringField;

    @Filterable
    @Column(name = "booleanField", nullable = false)
    private Boolean booleanField;

}
