package com.ll.springboot_20240810.base.attr.repository;

import com.ll.springboot_20240810.domain.base.attr.entity.Attr;
import com.ll.springboot_20240810.domain.base.attr.repository.AttrRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class AttrRepositoryTest {

    @Autowired
    private AttrRepository attrRepository;

    @DisplayName("attr 저장")
    @Test
    @Rollback(value = false)
    void t1() {
        Attr attr = Attr.builder()
                .createDate(LocalDateTime.now())
                .name("age")
                .build();
        attrRepository.save(attr);

        assertThat(attr.getId()).isGreaterThan(0L);

    }


    @Test
    @DisplayName("attr 저장, 한번더")

    void t2() {
        Attr attr = Attr.builder()
                .createDate(LocalDateTime.now())
                .name("age")
                .build();
        attrRepository.save(attr);

        assertThat(attr.getId()).isGreaterThan(0L);
    }
}
