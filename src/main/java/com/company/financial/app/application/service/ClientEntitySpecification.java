package com.company.financial.app.application.service;

import com.company.financial.app.infrastructure.adapter.entity.ClientEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class ClientEntitySpecification {

    public static Specification<ClientEntity> getClientsByFilter(ClientEntity client) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (client.getIdType() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("idType"), client.getIdType()));
            }
            if (client.getIdentificationNumber() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("identificationNumber"), client.getIdentificationNumber()));
            }
            if (client.getName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("name"), client.getName()));
            }
            if (client.getLastName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("lastName"), client.getLastName()));
            }
            if (client.getEmail() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("email"), client.getEmail()));
            }
            if (client.getBirthdate() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("birthdate"), client.getBirthdate()));
            }
            if (client.getCreationDate() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("creationDate"), client.getCreationDate()));
            }
            if (client.getModificationDate() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("modificationDate"), client.getModificationDate()));
            }

            return predicate;
        };
    }
}