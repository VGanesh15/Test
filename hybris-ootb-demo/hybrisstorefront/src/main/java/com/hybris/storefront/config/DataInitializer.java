package com.hybris.storefront.config;

import com.hybris.core.dao.CatalogVersionDao;
import com.hybris.core.model.*;
import com.hybris.core.service.CategoryService;
import com.hybris.core.service.CustomerService;
import com.hybris.core.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner initializeData(
            final CatalogVersionDao catalogVersionDao,
            final ProductService productService,
            final CategoryService categoryService,
            final CustomerService customerService) {

        return args -> {
            LOG.info("=== SystemSetup: Creating Samsung product catalog ===");

            final CatalogModel catalog = new CatalogModel();
            catalog.setId("electronicsProductCatalog");
            catalog.setName("Samsung Electronics Catalog");
            catalog.setDefaultCatalog(true);

            final CatalogVersionModel onlineVersion = new CatalogVersionModel();
            onlineVersion.setVersionLabel("Online");
            onlineVersion.setActive(true);
            onlineVersion.setCatalog(catalog);
            catalog.setCatalogVersions(List.of(onlineVersion));

            final CatalogVersionModel savedCv = catalogVersionDao.save(onlineVersion);

            // ── CATEGORIES ────────────────────────────────────────────────────
            final CategoryModel phones = cat(categoryService, "smartphones", "Smartphones",
                "Samsung Galaxy smartphones — S series, Z series, A series", savedCv, null,
                "https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=600&q=80");
            final CategoryModel galaxyS = cat(categoryService, "galaxy-s", "Galaxy S Series",
                "Flagship Samsung Galaxy S smartphones", savedCv, phones, null);
            final CategoryModel galaxyZ = cat(categoryService, "galaxy-z", "Galaxy Z Series",
                "Samsung foldable and flip smartphones", savedCv, phones, null);
            final CategoryModel laptops = cat(categoryService, "laptops", "Laptops",
                "Samsung Galaxy Book laptops", savedCv, null,
                "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=600&q=80");
            final CategoryModel tablets = cat(categoryService, "tablets", "Tablets",
                "Samsung Galaxy Tab tablets", savedCv, null,
                "https://images.unsplash.com/photo-1632932197941-d5e40cee9be3?w=600&q=80");
            final CategoryModel audio = cat(categoryService, "audio", "Audio",
                "Samsung Galaxy Buds and soundbars", savedCv, null,
                "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=600&q=80");
            final CategoryModel tvs = cat(categoryService, "tvs", "TVs & Displays",
                "Samsung Neo QLED, OLED, and The Frame TVs", savedCv, null,
                "https://images.unsplash.com/photo-1593359677879-a4bb92f4834b?w=600&q=80");
            final CategoryModel accessories = cat(categoryService, "accessories", "Accessories",
                "Samsung Galaxy Watch, Ring, and storage", savedCv, null,
                "https://images.unsplash.com/photo-1579586337278-3befd40fd17a?w=600&q=80");
            final CategoryModel monitors = cat(categoryService, "monitors", "Monitors & Displays",
                "Samsung Odyssey gaming monitors and ViewFinity displays", savedCv, null,
                "https://images.samsung.com/is/image/samsung/us_LC49G95TSSNXZA_001_Front_Black?$684_547_PNG$");
            LOG.info("Created 9 categories");

            // ── GALAXY S SERIES ───────────────────────────────────────────────
            prod(productService, "SAM_S24U", "Samsung Galaxy S24 Ultra",
                "Titanium frame. 200MP ProVisual Engine camera with 100x Space Zoom. Built-in S Pen. Snapdragon 8 Gen 3. 5000mAh battery with 45W fast charging. 6.8\" Dynamic AMOLED 2X display.",
                "1299.99", 85, savedCv, List.of(phones, galaxyS), "Samsung",
                "https://placehold.co/500x400/1428a0/ffffff?text=Galaxy+S24+Ultra");

            prod(productService, "SAM_S24P", "Samsung Galaxy S24+",
                "6.7\" Dynamic AMOLED 2X. Snapdragon 8 Gen 3. 50MP triple camera. 4900mAh battery. 45W wired and 15W wireless charging. Armor Aluminum frame.",
                "1099.99", 110, savedCv, List.of(phones, galaxyS), "Samsung",
                "https://placehold.co/500x400/1428a0/ffffff?text=Galaxy+S24+Plus");

            prod(productService, "SAM_S24", "Samsung Galaxy S24",
                "6.2\" Dynamic AMOLED 2X. Snapdragon 8 Gen 3. 50MP main camera. 4000mAh battery. Compact flagship form factor. 7 years of OS and security updates.",
                "899.99", 160, savedCv, List.of(phones, galaxyS), "Samsung",
                "https://placehold.co/500x400/1428a0/ffffff?text=Galaxy+S24");

            prod(productService, "SAM_S23FE", "Samsung Galaxy S23 FE",
                "Fan Edition flagship features at a value price. 6.4\" AMOLED. 50MP triple camera. Snapdragon 8 Gen 1. 4500mAh battery.",
                "599.99", 220, savedCv, List.of(phones, galaxyS), "Samsung",
                "https://placehold.co/500x400/1428a0/ffffff?text=Galaxy+S23+FE");

            prod(productService, "SAM_A55", "Samsung Galaxy A55 5G",
                "6.6\" Super AMOLED. 50MP OIS triple camera. Exynos 1480. 5000mAh. IP67 rated. Premium mid-range performance.",
                "449.99", 300, savedCv, List.of(phones), "Samsung",
                "https://placehold.co/500x400/1e40af/ffffff?text=Galaxy+A55+5G");

            // ── GALAXY Z SERIES ───────────────────────────────────────────────
            prod(productService, "SAM_ZFOLD6", "Samsung Galaxy Z Fold6",
                "7.6\" foldable Dynamic AMOLED 2X. Snapdragon 8 Gen 3. 50MP triple camera. S Pen compatible. IPX8 water resistant. 12GB RAM, 256GB storage.",
                "1899.99", 45, savedCv, List.of(phones, galaxyZ), "Samsung",
                "https://placehold.co/500x400/4c1d95/ffffff?text=Galaxy+Z+Fold6");

            prod(productService, "SAM_ZFLIP6", "Samsung Galaxy Z Flip6",
                "Flip phone reimagined. 6.7\" foldable display. 50MP dual camera with FlexCam. Snapdragon 8 Gen 3. Compact design. Cover Screen for quick actions.",
                "1099.99", 70, savedCv, List.of(phones, galaxyZ), "Samsung",
                "https://placehold.co/500x400/6d28d9/ffffff?text=Galaxy+Z+Flip6");

            // ── GALAXY BOOK LAPTOPS ───────────────────────────────────────────
            prod(productService, "BOOK4_ULTRA", "Samsung Galaxy Book4 Ultra 16\"",
                "Intel Core Ultra 9. NVIDIA GeForce RTX 4070. 32GB LPDDR5X RAM. 1TB NVMe SSD. 3K AMOLED touchscreen. Premium laptop for creators and professionals.",
                "2499.99", 22, savedCv, List.of(laptops), "Samsung",
                "https://placehold.co/500x400/0f172a/ffffff?text=Book4+Ultra+16");

            prod(productService, "BOOK4_PRO360", "Samsung Galaxy Book4 Pro 360 16\"",
                "Intel Core Ultra 7. 2-in-1 convertible with S Pen. 3K AMOLED touchscreen. 16GB RAM. 512GB SSD. Thin & light versatile design.",
                "1799.99", 30, savedCv, List.of(laptops), "Samsung",
                "https://placehold.co/500x400/0f172a/ffffff?text=Book4+Pro+360");

            prod(productService, "BOOK4_PRO", "Samsung Galaxy Book4 Pro 14\"",
                "Intel Core Ultra 7. Dynamic AMOLED 2X display. 16GB RAM. 512GB SSD. Ultra-slim 11.6mm profile. AKG-tuned speakers.",
                "1499.99", 40, savedCv, List.of(laptops), "Samsung",
                "https://placehold.co/500x400/0f172a/ffffff?text=Book4+Pro+14");

            prod(productService, "BOOK4_AIR", "Samsung Galaxy Book4 Air 15\"",
                "Intel Core Ultra 5. 15.6\" AMOLED. 16GB RAM. 512GB SSD. Ultra-light 1.17kg. Perfect everyday laptop.",
                "999.99", 55, savedCv, List.of(laptops), "Samsung",
                "https://placehold.co/500x400/1e293b/ffffff?text=Book4+Air+15");

            prod(productService, "BOOK4_360", "Samsung Galaxy Book4 360 13\"",
                "Compact 2-in-1 with S Pen. Intel Core 5. 8GB RAM. 256GB SSD. 13.3\" touchscreen. Tablet and laptop in one.",
                "749.99", 75, savedCv, List.of(laptops), "Samsung",
                "https://placehold.co/500x400/1e293b/ffffff?text=Book4+360+13");

            // ── GALAXY TAB ────────────────────────────────────────────────────
            prod(productService, "TAB_S10U", "Samsung Galaxy Tab S10 Ultra",
                "14.6\" Dynamic AMOLED 2X. Snapdragon 8 Gen 3. S Pen included. 12GB RAM. 256GB. Ideal for creators and professionals on the go.",
                "1199.99", 35, savedCv, List.of(tablets), "Samsung",
                "https://placehold.co/500x400/1e1b4b/ffffff?text=Tab+S10+Ultra");

            prod(productService, "TAB_S10P", "Samsung Galaxy Tab S10+",
                "12.4\" Dynamic AMOLED 2X. Snapdragon 8 Gen 3. S Pen included. 12GB RAM. 256GB. Premium tablet experience.",
                "999.99", 50, savedCv, List.of(tablets), "Samsung",
                "https://placehold.co/500x400/1e1b4b/ffffff?text=Tab+S10+Plus");

            prod(productService, "TAB_S10", "Samsung Galaxy Tab S10",
                "11\" Dynamic AMOLED 2X. Snapdragon 8 Gen 3. S Pen included. 8GB RAM. 128GB. The versatile everyday tablet.",
                "799.99", 80, savedCv, List.of(tablets), "Samsung",
                "https://placehold.co/500x400/312e81/ffffff?text=Galaxy+Tab+S10");

            prod(productService, "TAB_S9FE", "Samsung Galaxy Tab S9 FE",
                "10.9\" LCD display. Exynos 1380. IP68 rated. S Pen included. 6GB RAM. 128GB. Great value tablet.",
                "449.99", 120, savedCv, List.of(tablets), "Samsung",
                "https://placehold.co/500x400/312e81/ffffff?text=Tab+S9+FE");

            // ── AUDIO ─────────────────────────────────────────────────────────
            prod(productService, "BUDS3_PRO", "Samsung Galaxy Buds3 Pro",
                "Intelligent Active Noise Cancellation. 360° Audio. Seamless Galaxy ecosystem integration. Up to 30hrs total battery. Ergonomic blade design.",
                "249.99", 180, savedCv, List.of(audio), "Samsung",
                "https://placehold.co/500x400/065f46/ffffff?text=Buds3+Pro");

            prod(productService, "BUDS3", "Samsung Galaxy Buds3",
                "Open-type design for all-day comfort. AI Live Translate. 360° Audio. 26hrs total battery. Adaptive EQ.",
                "179.99", 220, savedCv, List.of(audio), "Samsung",
                "https://placehold.co/500x400/065f46/ffffff?text=Galaxy+Buds3");

            prod(productService, "BUDS2_PRO", "Samsung Galaxy Buds2 Pro",
                "24-bit Hi-Fi audio. Intelligent Active Noise Cancellation. 360° Audio with head tracking. IPX7. Ergonomic in-ear design.",
                "199.99", 150, savedCv, List.of(audio), "Samsung",
                "https://placehold.co/500x400/065f46/ffffff?text=Buds2+Pro");

            prod(productService, "BUDS_FE", "Samsung Galaxy Buds FE",
                "Active Noise Cancellation. Wing tip for secure fit. 21hrs total battery. One-tap ANC toggle. Affordable premium audio.",
                "99.99", 350, savedCv, List.of(audio), "Samsung",
                "https://placehold.co/500x400/065f46/ffffff?text=Galaxy+Buds+FE");

            prod(productService, "HW_Q990D", "Samsung HW-Q990D Soundbar 11.1.4ch",
                "Dolby Atmos and DTS:X. 656W total output. Wireless surround speakers. SpaceFit Sound Pro. Q-Symphony with compatible Samsung TVs.",
                "1499.99", 18, savedCv, List.of(audio), "Samsung",
                "https://placehold.co/500x400/134e4a/ffffff?text=HW-Q990D+Soundbar");

            // ── TVs ───────────────────────────────────────────────────────────
            prod(productService, "QN900D_85", "Samsung 85\" Neo QLED 8K QN900D",
                "8K AI Upscaling Pro. Neo Quantum HDR 8K+. Dolby Atmos. Object Tracking Sound+. Anti-Reflection. Neural Quantum Processor 8K.",
                "4999.99", 8, savedCv, List.of(tvs), "Samsung",
                "https://placehold.co/500x400/0c0a09/ffffff?text=Neo+QLED+8K+85%22");

            prod(productService, "QN90D_65", "Samsung 65\" Neo QLED 4K QN90D",
                "Neo Quantum HDR 32X. 4K 144Hz. Anti-Reflection. Real Game Enhancer+. Object Tracking Sound+. Ultra-thin design.",
                "2199.99", 15, savedCv, List.of(tvs), "Samsung",
                "https://placehold.co/500x400/0c0a09/ffffff?text=Neo+QLED+4K+65%22");

            prod(productService, "S95D_55", "Samsung 55\" OLED 4K S95D",
                "Glare-Free OLED display. Neural Quantum Processor 4K. LaserSlim design. 4K 144Hz. WOLED panel with Samsung's colour processing.",
                "1999.99", 20, savedCv, List.of(tvs), "Samsung",
                "https://placehold.co/500x400/1c1917/ffffff?text=OLED+4K+S95D+55%22");

            prod(productService, "FRAME_65", "Samsung 65\" The Frame QLED 4K LS03D",
                "Art Mode with 1600+ artworks. Ultra-slim Slim One Connect. No Gap Wall Mount. QLED 4K. Your TV becomes art when not in use.",
                "1799.99", 25, savedCv, List.of(tvs), "Samsung",
                "https://placehold.co/500x400/44403c/ffffff?text=The+Frame+65%22");

            // ── ACCESSORIES ───────────────────────────────────────────────────
            prod(productService, "WATCH_ULTRA", "Samsung Galaxy Watch Ultra",
                "Titanium case. 1.5\" Super AMOLED. Advanced health tracking with Metabolic Rate. 10ATM water resistance. Mountain Guide and Dive. 60hr battery.",
                "649.99", 40, savedCv, List.of(accessories), "Samsung",
                "https://placehold.co/500x400/7c2d12/ffffff?text=Watch+Ultra");

            prod(productService, "WATCH7_44", "Samsung Galaxy Watch7 44mm",
                "Advanced BioActive Sensor. AI-powered Energy Score. 3nm chip for faster performance. 40hr battery. BIA sensor for body composition.",
                "299.99", 90, savedCv, List.of(accessories), "Samsung",
                "https://placehold.co/500x400/92400e/ffffff?text=Galaxy+Watch7");

            prod(productService, "WATCH6C_47", "Samsung Galaxy Watch6 Classic 47mm",
                "Iconic rotating bezel. Advanced sleep coaching. 4nm chip. BIA sensor. 40hr battery. Premium stainless steel design.",
                "399.99", 65, savedCv, List.of(accessories), "Samsung",
                "https://placehold.co/500x400/78350f/ffffff?text=Watch6+Classic");

            prod(productService, "GAL_RING_8", "Samsung Galaxy Ring Size 8",
                "Sleep tracking. Activity tracking. Ovulation cycle analysis. All-day comfort. Titanium build. 7-day battery life. No subscription required.",
                "399.99", 55, savedCv, List.of(accessories), "Samsung",
                "https://placehold.co/500x400/451a03/ffffff?text=Galaxy+Ring+Sz8");

            prod(productService, "T9_2TB", "Samsung Portable SSD T9 2TB",
                "Up to 2,000MB/s read speed. USB 3.2 Gen 2x2. Dynamic Thermal Guard. 3-year warranty. Compatible with PC, Mac, Android, and gaming consoles.",
                "199.99", 100, savedCv, List.of(accessories), "Samsung",
                "https://placehold.co/500x400/1e3a5f/ffffff?text=SSD+T9+2TB");

            // ── MONITORS & DISPLAYS ───────────────────────────────────────────
            prod(productService, "OD_OLED_G9", "Samsung 49\" Odyssey OLED G9",
                "Dual QHD OLED. 240Hz. 0.03ms GtG. AMD FreeSync Premium Pro. G-Sync compatible. Quantum HDR OLED. Immersive ultrawide gaming.",
                "1499.99", 20, savedCv, List.of(monitors), "Samsung",
                "https://placehold.co/500x400/1e1b4b/ffffff?text=Odyssey+OLED+G9");

            prod(productService, "VF_S9_27", "Samsung ViewFinity S9 27\" 5K",
                "5K IPS Nano. 99% DCI-P3. Thunderbolt 4. Built-in 4K Slimfit Camera. USB-C 90W power delivery. Professional colour accuracy for creators.",
                "1299.99", 25, savedCv, List.of(monitors), "Samsung",
                "https://placehold.co/500x400/172554/ffffff?text=ViewFinity+S9+27%22");

            prod(productService, "SM_M8_32", "Samsung Smart Monitor M8 32\"",
                "4K UHD. 60Hz. USB-C 65W. Matte display. Built-in apps (Netflix, Prime Video, web browsing). Slim One Remote. Works without a PC.",
                "699.99", 45, savedCv, List.of(monitors), "Samsung",
                "https://placehold.co/500x400/172554/ffffff?text=Smart+Monitor+M8");

            prod(productService, "OD_ARK_55", "Samsung 55\" Odyssey Ark 4K",
                "55\" curved Quantum Mini-LED. 4K 165Hz. Multi-View. Cockpit Mode. 1000R curvature. The ultimate gaming screen.",
                "2999.99", 10, savedCv, List.of(monitors), "Samsung",
                "https://placehold.co/500x400/1e1b4b/ffffff?text=Odyssey+Ark+55%22");

            prod(productService, "OD_G9_QLED", "Samsung 49\" Odyssey G9 QLED",
                "Dual QHD QLED. 240Hz. 1000R curve. NVIDIA G-Sync. Quantum HDR 2000. 1ms GtG. The curved ultrawide for competitive gaming.",
                "1199.99", 18, savedCv, List.of(monitors), "Samsung",
                "https://placehold.co/500x400/14134a/ffffff?text=Odyssey+G9+QLED");

            prod(productService, "OD_G7_32", "Samsung 32\" Odyssey G7",
                "Curved QHD QLED. 240Hz. 1000R. G-Sync compatible. Quantum HDR 600. USB-C. Compact high-refresh gaming monitor.",
                "499.99", 40, savedCv, List.of(monitors), "Samsung",
                "https://placehold.co/500x400/1e1b4b/ffffff?text=Odyssey+G7+32%22");

            prod(productService, "SM_M7_43", "Samsung Smart Monitor M7 43\"",
                "43\" 4K UHD. Built-in streaming apps. USB-C 65W. Mobile DeX support. Wireless DeX. Big-screen productivity without a PC.",
                "599.99", 30, savedCv, List.of(monitors), "Samsung",
                "https://placehold.co/500x400/172554/ffffff?text=Smart+Monitor+M7+43%22");

            prod(productService, "VF_S8_27", "Samsung ViewFinity S8 27\" 4K",
                "4K UHD IPS. USB-C 90W. Thunderbolt 4. 99% sRGB. Height-adjustable stand. Premium productivity display for professionals.",
                "799.99", 35, savedCv, List.of(monitors), "Samsung",
                "https://placehold.co/500x400/172554/ffffff?text=ViewFinity+S8+27%22");

            LOG.info("=== Created 39 Samsung products across 9 categories ===");

            // ── CUSTOMER ──────────────────────────────────────────────────────
            if (customerService.getCustomerByUid("demo@samsung-store.com").isEmpty()) {
                customerService.registerCustomer(
                        "demo@samsung-store.com", "demo@samsung-store.com",
                        "Demo", "Customer", "Demo@1234");
                LOG.info("Created demo customer: demo@samsung-store.com");
            }
        };
    }

    private CategoryModel cat(final CategoryService svc,
                               final String code, final String name, final String desc,
                               final CatalogVersionModel cv,
                               final CategoryModel parent, final String imgUrl) {
        final CategoryModel c = new CategoryModel();
        c.setCode(code);
        c.setName(name);
        c.setDescription(desc);
        c.setCatalogVersion(cv);
        if (parent != null) {
            c.setSupercategory(parent);
        }
        if (imgUrl != null) {
            c.setThumbnailUrl(imgUrl);
        }
        return svc.saveCategory(c);
    }

    private void prod(final ProductService svc,
                      final String code, final String name, final String desc,
                      final String price, final int stock,
                      final CatalogVersionModel cv,
                      final List<CategoryModel> cats,
                      final String brand, final String img) {
        final ProductModel p = new ProductModel();
        p.setCode(code);
        p.setName(name);
        p.setDescription(desc);
        p.setPrice(new BigDecimal(price));
        p.setCurrencyIso("USD");
        p.setStockLevel(stock);
        p.setCatalogVersion(cv);
        p.setSupercategories(cats);
        p.setManufacturerName(brand);
        p.setThumbnailUrl(img);
        svc.saveProduct(p);
    }
}
