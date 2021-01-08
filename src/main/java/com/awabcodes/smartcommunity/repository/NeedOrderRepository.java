package com.awabcodes.smartcommunity.repository;

import com.awabcodes.smartcommunity.domain.NeedOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the NeedOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NeedOrderRepository extends JpaRepository<NeedOrder, Long> {

    @Query("select needOrder from NeedOrder needOrder where needOrder.user.login = ?#{principal.username}")
    List<NeedOrder> findByUserIsCurrentUser();

    Page<NeedOrder> findAllByUserLogin(Pageable pageable, String login);

    Optional<NeedOrder> findByIdAndUserLogin(Long id, String login);
}
