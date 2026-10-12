package com.shortlinkx.repository;

import com.shortlinkx.entity.Url;
import com.shortlinkx.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    List<Url> findAllByUserOrderByCreatedAtDesc(User user);
}