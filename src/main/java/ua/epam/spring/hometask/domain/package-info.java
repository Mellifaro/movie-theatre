@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(value=LocaleDateXmlAdapter.class, type=LocalDate.class)
})
@XmlSchema(namespace = "http://epam.com/movie/", elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED)
package ua.epam.spring.hometask.domain;

import ua.epam.spring.hometask.util.LocaleDateXmlAdapter;

import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;