package pl.mgorski.nativeserverless;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@Data
@RegisterForReflection
public class Person {

    public final static String TABLE_NAME = "Persons";
    public final static String COLUMN_PESEL = "pesel"; // unique identifier in Poland
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_LASTNAME = "lastName";

    private String pesel;
    private String name;
    private String lastName;

    public static Person from(Map<String, AttributeValue> attributeMap) {
        Person person = new Person();
        if (MapUtils.isNotEmpty(attributeMap)) {
            person.setPesel(attributeMap.get(COLUMN_PESEL).s());
            person.setName(attributeMap.get(COLUMN_NAME).s());
            person.setLastName(attributeMap.get(COLUMN_LASTNAME).s());
        }
        return person;
    }

}
