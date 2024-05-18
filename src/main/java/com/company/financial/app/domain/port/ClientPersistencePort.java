package com.company.financial.app.domain.port;

import com.company.financial.app.domain.model.Client;

import java.util.List;

public interface ClientPersistencePort {

    Client create(Client request);
    Client getById(Long id);
    List<Client> getAll();
    void deleteClient(Client client);
    Client update(Client request);
    List<Client> getByIds(List<Long> tasksIds);
}
