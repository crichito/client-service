package com.clientservice.app.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "typeClient")
public class TypeClient {
    @NotEmpty
    @Id
    private String id;
    @NotEmpty
    private String description;
}
