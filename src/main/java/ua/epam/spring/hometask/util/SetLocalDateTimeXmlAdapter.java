package ua.epam.spring.hometask.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SetLocalDateTimeXmlAdapter extends XmlAdapter<HashSet<String>, NavigableSet<LocalDateTime>> {
    @Override
    public TreeSet<LocalDateTime> unmarshal(HashSet<String> v) throws Exception {
        return v.stream()
                .map(LocalDateTime::parse)
                .collect(Collectors.toCollection(() -> new TreeSet<>((o1, o2) -> {
                    if (o2.isAfter(o1)) {
                        return 1;
                    } else if (o1.isAfter(o2)) {
                        return -1;
                    } else {
                        return 0;
                    }
                })));
    }

    @Override
    public HashSet<String> marshal(NavigableSet<LocalDateTime> v) throws Exception {
        return v.stream()
                .map(LocalDateTime::toString)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
