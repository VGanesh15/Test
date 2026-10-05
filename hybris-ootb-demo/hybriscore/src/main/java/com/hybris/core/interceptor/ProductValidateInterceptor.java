package com.hybris.core.interceptor;

import com.hybris.core.model.ProductModel;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Hybris OOTB: Product ValidateInterceptor.
 * Mirrors commerceservices ProductValidateInterceptor.
 *
 * Enforces business rules on product save:
 * - code is mandatory and alphanumeric
 * - price must be non-negative
 * - stock level must be non-negative
 * - online date must be before offline date
 *
 * Registered via Spring interceptor mapping for typeCode=Product.
 */
@Component("productValidateInterceptor")
public class ProductValidateInterceptor implements ValidateInterceptor<ProductModel> {

    @Override
    public void onValidate(final ProductModel product, final InterceptorContext context) throws InterceptorException {
        if (!StringUtils.hasText(product.getCode())) {
            throw new InterceptorException("Product code is mandatory", this);
        }

        if (!product.getCode().matches("[a-zA-Z0-9_\\-]+")) {
            throw new InterceptorException(
                "Product code '" + product.getCode() + "' must be alphanumeric (letters, digits, _, -)", this);
        }

        if (product.getPrice() != null && product.getPrice().signum() < 0) {
            throw new InterceptorException(
                "Product price cannot be negative for product: " + product.getCode(), this);
        }

        if (product.getStockLevel() != null && product.getStockLevel() < 0) {
            throw new InterceptorException(
                "Stock level cannot be negative for product: " + product.getCode(), this);
        }

        if (product.getOnlineDate() != null && product.getOfflineDate() != null) {
            if (product.getOnlineDate().isAfter(product.getOfflineDate())) {
                throw new InterceptorException(
                    "Online date must be before offline date for product: " + product.getCode(), this);
            }
        }

        if (product.getCatalogVersion() == null) {
            throw new InterceptorException(
                "Product must be assigned to a CatalogVersion: " + product.getCode(), this);
        }
    }
}
