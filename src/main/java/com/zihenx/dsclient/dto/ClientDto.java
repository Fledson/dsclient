package com.zihenx.dsclient.dto;

import com.zihenx.dsclient.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String cpf;
    private Double income;
    private LocalDate birthDate;
    private Integer children;

    public ClientDto(Client entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = entity.getCpf();
        this.income = entity.getIncome();
        this.birthDate = entity.getBirthDate();
        this.children = entity.getChildren();
    }
}
