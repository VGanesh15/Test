package com.hybris.storefront.controllers;

import com.hybris.facades.data.ProductData;
import com.hybris.facades.facades.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final ProductFacade productFacade;

    @Autowired
    public DashboardController(final ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping("/dashboard")
    public String dashboard(final Model model) {
        final List<ProductData> products = productFacade.getFeaturedProducts();
        model.addAttribute("products", products);
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("cartItemCount", 0);
        return "dashboard";
    }

    @GetMapping("/analytics")
    public String analytics(final Model model) {
        return "redirect:/dashboard";
    }
}
