package pl.mgorski.nativeserverless;

import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Singleton
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    void save(PersonDTO personDTO) {
        Person person = PersonMapper.INSTANCE.fromDto(personDTO);
        personRepository.save(person);
    }

    List<PersonDTO> findAll() {
        return personRepository.findAll()
                .stream()
                .map(PersonMapper.INSTANCE::toDto)
                .collect(toList());
    }

}
