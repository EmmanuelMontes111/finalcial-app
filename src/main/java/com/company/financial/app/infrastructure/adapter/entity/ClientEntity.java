package com.company.financial.app.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "client")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String idType;
    private Long identificationNumber;
    private String name;
    private String lastName;
    private String email;
    private Long birthdate;
    private Long creationDate;
    private Long modificationDate;
    private int moneyInvested;
}
