package com.hms.restaurantservice.repository;

import com.hms.restaurantservice.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Long> {
    Optional<Menu> findByNameIgnoreCase(String name);
}
