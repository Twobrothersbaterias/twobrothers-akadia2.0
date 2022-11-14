package br.com.twobrothers.frontend.repositories.services.exceptions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ErrorLog {

    String code;
    String message;
    String trace;
    String cause;
    String localizedMessage;

}
