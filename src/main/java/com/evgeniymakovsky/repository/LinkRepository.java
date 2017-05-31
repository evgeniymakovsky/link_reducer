package com.evgeniymakovsky.repository;

import com.evgeniymakovsky.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LinkRepository extends JpaRepository<Link, String> {

    @Modifying
    @Transactional(readOnly=false)
    @Query("update Link u set u.invocations = :invocations where u.link_id = :link_id")
    void alterStatistics(@Param("link_id") Integer link_id, @Param("invocations") Integer invocations);
}