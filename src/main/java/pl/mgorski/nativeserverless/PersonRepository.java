package pl.mgorski.nativeserverless;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Singleton
@RequiredArgsConstructor
public class PersonRepository {

    private final DynamoDbClient client;

    void save(Person person) {
        client.putItem(putRequest(person));
    }

    private PutItemRequest putRequest(Person person) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put(Person.COLUMN_PESEL, AttributeValue.builder().s(person.getPesel()).build());
        item.put(Person.COLUMN_NAME, AttributeValue.builder().s(person.getName()).build());
        item.put(Person.COLUMN_LASTNAME, AttributeValue.builder().s(person.getLastName()).build());
        return PutItemRequest.builder()
                .tableName(Person.TABLE_NAME)
                .item(item)
                .build();
    }

    public List<Person> findAll() {
        return client.scanPaginator(buildScanRequest())
                .items()
                .stream()
                .map(Person::from)
                .collect(toList());
    }

    private ScanRequest buildScanRequest() {
        return ScanRequest
                .builder()
                .tableName(Person.TABLE_NAME)
                .attributesToGet(Person.COLUMN_PESEL, Person.COLUMN_NAME, Person.COLUMN_LASTNAME)
                .build();
    }

}
