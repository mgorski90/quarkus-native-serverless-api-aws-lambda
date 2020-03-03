package pl.mgorski.nativeserverless;

import lombok.Data;

@Data
public class PersonDTO {

    private String pesel;
    private String name;
    private String lastName;

}
