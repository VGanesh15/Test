package com.hybris.core.dao;

import com.hybris.core.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Hybris OOTB pattern: OrderDao.
 * Mirrors de.hybris.platform.order.daos.OrderDao.
 *
 * Order management, fraud checking, and reporting all depend on this DAO.
 * Orders transition through OrderStatus states managed by order business processes.
 */
@Repository
public interface OrderDao extends JpaRepository<OrderModel, String> {

    Optional<OrderModel> findByCode(String code);

    Optional<OrderModel> findByGuid(String guid);

    List<OrderModel> findByUserUid(String userUid);

    List<OrderModel> findByStatus(OrderModel.OrderStatus status);

    /**
     * Find orders for a user ordered by placement date descending.
     * Used by the My Account order history page (OrderHistoryPageController).
     */
    @Query("SELECT o FROM OrderModel o WHERE o.user.uid = :uid ORDER BY o.placed DESC")
    List<OrderModel> findOrderHistoryByUser(@Param("uid") String userUid);

    /**
     * Fraud check queue - orders awaiting fraud screening.
     */
    @Query("SELECT o FROM OrderModel o WHERE o.status = 'ON_VALIDATION' ORDER BY o.placed ASC")
    List<OrderModel> findOrdersPendingFraudCheck();

    /**
     * Orders requiring payment capture - used by payment capture job.
     */
    @Query("SELECT o FROM OrderModel o WHERE o.status = 'PAYMENT_AUTHORIZED'")
    List<OrderModel> findOrdersPendingPaymentCapture();

    /**
     * Revenue report query - total revenue grouped by site.
     */
    @Query("SELECT o.site, SUM(o.totalPrice), COUNT(o) FROM OrderModel o " +
           "WHERE o.placed BETWEEN :from AND :to AND o.status = 'COMPLETED' " +
           "GROUP BY o.site")
    List<Object[]> getRevenueReportBySite(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    /**
     * Find recently placed orders for the order management dashboard.
     */
    @Query("SELECT o FROM OrderModel o WHERE o.placed > :since ORDER BY o.placed DESC")
    List<OrderModel> findRecentOrders(@Param("since") LocalDateTime since,
                                        org.springframework.data.domain.Pageable pageable);

    /**
     * Count orders by status - used for dashboard KPIs.
     */
    @Query("SELECT o.status, COUNT(o) FROM OrderModel o GROUP BY o.status")
    List<Object[]> countOrdersByStatus();
}
