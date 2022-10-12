package com.zihenx.dsclient.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoError {
    private String fieldError;
    private String messageError;
}
