package com.hybris.core.dao;

import com.hybris.core.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * Hybris OOTB pattern: CustomerDao.
 * Mirrors de.hybris.platform.commerceservices.customer.dao.CustomerDao.
 *
 * The Hybris UserService and CustomerAccountService depend on this to
 * find and manage customer accounts and guest sessions.
 */
@Repository
public interface CustomerDao extends JpaRepository<CustomerModel, String> {

    Optional<CustomerModel> findByUid(String uid);

    Optional<CustomerModel> findByEmail(String email);

    boolean existsByUid(String uid);

    boolean existsByEmail(String email);

    /**
     * Find customers by customer type - used for B2B vs B2C segmentation.
     */
    List<CustomerModel> findByCustomerType(CustomerModel.CustomerType customerType);

    /**
     * FlexibleSearch equivalent:
     * SELECT {c:PK} FROM {Customer AS c} WHERE {c:customerId} = ?customerId
     * Used to restore guest accounts and B2B impersonation.
     */
    Optional<CustomerModel> findByCustomerId(String customerId);

    /**
     * Find customers with outstanding loyalty points for marketing campaigns.
     */
    @Query("SELECT c FROM CustomerModel c WHERE c.loyaltyPoints > :minPoints ORDER BY c.loyaltyPoints DESC")
    List<CustomerModel> findTopLoyaltyCustomers(@Param("minPoints") int minPoints,
                                                  org.springframework.data.domain.Pageable pageable);

    /**
     * Count customers by active base site - used for analytics and multi-site deployments.
     */
    @Query("SELECT c.activeBaseSite, COUNT(c) FROM CustomerModel c GROUP BY c.activeBaseSite")
    List<Object[]> countCustomersByBaseSite();
}
