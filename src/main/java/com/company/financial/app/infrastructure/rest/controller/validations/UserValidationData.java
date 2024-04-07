package com.company.financial.app.infrastructure.rest.controller.validations;

import com.company.financial.app.domain.model.dto.request.ClientRequest;
import com.company.financial.app.infrastructure.rest.controller.ResExceptiont.DataClientException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidationData {
    public void validateData(ClientRequest clientRequest) {
        if (isAMinor(clientRequest.getBirthdate())) {
            throw new DataClientException("El cliente no puede ser menor de edad");
        }

        if (isValidEmail(clientRequest.getEmail())) {
            throw new DataClientException("El correo electrónico proporcionado no es válido");
        }

        if (isShortNameAndLastName(clientRequest.getName(), clientRequest.getLastName())) {
            throw new DataClientException("Nombre y apellido deben contener al menos 2 caracteres");
        }
    }

    private boolean isAMinor(Long birthdate) {
        LocalDate birthdateDate = LocalDate.ofEpochDay(birthdate / (24 * 60 * 60 * 1000));
        Period age = Period.between(birthdateDate, LocalDate.now());
        return age.getYears() < 18;
    }

    private boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isShortNameAndLastName(String name, String lastName) {
        return name.length() < 2 || lastName.length() < 2;
    }

}
