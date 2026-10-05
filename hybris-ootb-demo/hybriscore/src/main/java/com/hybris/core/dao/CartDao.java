package com.hybris.core.dao;

import com.hybris.core.model.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Hybris OOTB pattern: CartDao.
 * Mirrors de.hybris.platform.order.daos.CartDao.
 *
 * The CartService maintains session carts. Old carts are cleaned up by
 * the CartCleanUpJob which uses this DAO to find expired carts.
 */
@Repository
public interface CartDao extends JpaRepository<CartModel, String> {

    Optional<CartModel> findByGuid(String guid);

    List<CartModel> findByUserUid(String userUid);

    /**
     * Find the active (most recently modified) cart for a user.
     * The SessionCartService uses this to restore the user's cart on login.
     */
    @Query("SELECT c FROM CartModel c WHERE c.user.uid = :uid ORDER BY c.modifiedTs DESC")
    List<CartModel> findActiveCartsByUser(@Param("uid") String userUid);

    /**
     * Find expired carts for cleanup. The CartCleanUpCronJob removes
     * carts older than configurable threshold (default 30 days).
     */
    @Query("SELECT c FROM CartModel c WHERE c.modifiedTs < :threshold")
    List<CartModel> findExpiredCarts(@Param("threshold") LocalDateTime threshold);

    /**
     * Bulk delete expired carts. Used by CartCleanUpJob to keep DB lean.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM CartModel c WHERE c.modifiedTs < :threshold")
    int deleteExpiredCarts(@Param("threshold") LocalDateTime threshold);

    /**
     * Find carts by site - multi-site deployments need site-scoped cart management.
     */
    List<CartModel> findBySite(String site);
}
