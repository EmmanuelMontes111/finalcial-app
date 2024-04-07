package com.company.financial.app.infrastructure.adapter.repository;

import com.company.financial.app.infrastructure.adapter.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
