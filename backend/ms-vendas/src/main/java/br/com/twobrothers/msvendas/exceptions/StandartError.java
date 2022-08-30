package br.com.twobrothers.msvendas.exceptions;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class StandartError {

    private LocalDateTime localDateTime;
    private Integer status;
    private String error;
    private String path;

}
