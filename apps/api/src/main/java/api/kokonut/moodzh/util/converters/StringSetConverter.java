package api.kokonut.moodzh.util.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;

@Converter() //autoApply = true si quiero evitar poner @Converter en las propiedades de las entidades
public class StringSetConverter implements AttributeConverter<Set<String>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        if (attribute == null || attribute.isEmpty()) return null;
        return String.join(SPLIT_CHAR, attribute);
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return emptySet();
        }
        return Arrays.stream(dbData.split(SPLIT_CHAR)).map(String::trim).collect(Collectors.toSet());
    }
}
