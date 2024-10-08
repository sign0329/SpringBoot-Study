package com.ll.springboot_20240810.domain.base.attr.repository;

import com.ll.springboot_20240810.domain.base.attr.entity.Attr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttrRepository extends JpaRepository<Attr, Long> {
}
