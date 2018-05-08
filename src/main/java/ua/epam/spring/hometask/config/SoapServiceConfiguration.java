package ua.epam.spring.hometask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
@ComponentScan("ua.epam.spring.hometask.soap")
public class SoapServiceConfiguration extends WsConfigurerAdapter {

    @Bean(name = "entities")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema entitiesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("EntitiesPort");
        wsdl11Definition.setLocationUri("/soapws");
        wsdl11Definition.setTargetNamespace("http://epam.com/soap");
        wsdl11Definition.setSchema(entitiesSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema entitiesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("entities.xsd"));
    }
}
