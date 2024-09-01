package com.eduardoschelive.deliverbills.common.dto;

import com.eduardoschelive.deliverbills.common.utils.PageUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@RequiredArgsConstructor
public class PagedModel<T> {

    private final Page<T> page;

    @JsonProperty
    public  List<T> content() {
        return page.getContent();
    }

    @JsonProperty("page")
    public PageMetadata metadata() {
        return new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
    }

    public record PageMetadata(int size, int number, long totalElements, int totalPages) {
        public int number() {
            return PageUtils.oneIndexPage(this.number);
        }
    }

}
