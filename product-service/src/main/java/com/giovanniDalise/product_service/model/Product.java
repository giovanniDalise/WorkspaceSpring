package com.giovanniDalise.product_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "product")

//Librerie Lombok
//@AllArgsConstructor: Genera un costruttore con un parametro per ciascun campo della classe.
//@NoArgsConstructor: Genera un costruttore vuoto senza parametri.
//@Builder: Fornisce un pattern builder per creare istanze della classe.
//@Data:
/*  Combina le seguenti annotazioni di Lombok:
    @Getter: Genera getter per tutti i campi.
    @Setter: Genera setter per tutti i campi.
    @ToString: Genera un metodo toString().
    @EqualsAndHashCode: Genera metodi equals() e hashCode().
    @RequiredArgsConstructor: Genera un costruttore per i campi final o annotati con @NonNull.
    */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
