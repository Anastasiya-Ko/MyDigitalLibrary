package ru.kopylova.springcourse.DigitalLibrary.util.page.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum BookSort {

        ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
        ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
        AUTHOR_ASC(Sort.by(Sort.Direction.ASC, "author_id")),
        AUTHOR_DESC(Sort.by(Sort.Direction.DESC, "author_id")),
        TITLE_ASC(Sort.by(Sort.Direction.ASC, "title")),
        TITLE_DESC(Sort.by(Sort.Direction.DESC, "title"));

        private final Sort sortValue;

}
