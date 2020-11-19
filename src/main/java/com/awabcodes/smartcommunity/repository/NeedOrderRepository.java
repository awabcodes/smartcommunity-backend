package com.awabcodes.smartcommunity.repository;

import com.awabcodes.smartcommunity.domain.NeedOrder;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the NeedOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NeedOrderRepository extends JpaRepository<NeedOrder, Long> {

    @Query("select needOrder from NeedOrder needOrder where needOrder.user.login = ?#{principal.username}")
    List<NeedOrder> findByUserIsCurrentUser();
}
