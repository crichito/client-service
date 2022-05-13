package com.clientservice.app.document;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "Client")
@Getter
@Setter
@AllArgsConstructor
@ToString
@Data
public class Client {
    @Id
    private String id; //Identificador del cliente
    @NotEmpty
    private String firtName;
    @NotEmpty
    private String lastName;
    @Valid
    @NotNull
    private TypeClient typeClient;
    @NotEmpty
    private String document;/*DNI o RUC*/
    @NotEmpty
    private String direction;
    private Date createAt;/*Fecha de creacion*/
    private Date updateDate;/*Fecha de modificacion*/


    
}
