package com.hybris.facades.converters;

/**
 * Hybris OOTB: Converter interface.
 * Mirrors de.hybris.platform.servicelayer.dto.converter.Converter.
 *
 * The Converter pattern is central to Hybris facade architecture:
 * - Converter<Source, Target> creates a new Target instance and populates it
 * - Registered in Spring as a bean, typically named [Type]Converter
 * - Uses a list of Populators to fill attributes (Open-Closed principle)
 *
 * Spring XML registration pattern:
 * <bean id="productConverter" parent="abstractPopulatingConverter">
 *   <property name="targetClass" value="ProductData"/>
 *   <property name="populators">
 *     <list>
 *       <ref bean="productBasicPopulator"/>
 *       <ref bean="productPricePopulator"/>
 *       <ref bean="productStockPopulator"/>
 *     </list>
 *   </property>
 * </bean>
 */
public interface Converter<SOURCE, TARGET> {

    TARGET convert(SOURCE source);

    TARGET convert(SOURCE source, TARGET target);
}
