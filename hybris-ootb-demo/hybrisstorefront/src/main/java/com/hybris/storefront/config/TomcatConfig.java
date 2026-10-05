package com.hybris.storefront.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Forces Tomcat to use NIO2 (AsynchronousChannelGroup) instead of NIO.
 *
 * Oracle JDK 17.0.x on Windows 11 changed PipeImpl to use Unix Domain
 * Sockets for its internal loopback pipe. On some Windows builds the UDS
 * loopback path fails with "Invalid argument: connect", crashing the NIO
 * Selector. NIO2 avoids Selector/Pipe entirely by using thread-pool AIO.
 */
@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> nio2ProtocolCustomizer() {
        return factory ->
            factory.setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
    }
}
