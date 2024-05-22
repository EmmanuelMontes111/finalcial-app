package com.company.financial.app.domain.port;

import com.company.financial.app.domain.model.Client;

import java.util.List;

public interface ClientPersistencePort {

    Client create(Client client);
    Client getById(Long id);
    List<Client> getAll();
    List<Client> getClientByFilter(Client clientModel, String operatorComparator);
    void deleteClient(Client client);
    Client update(Client client);
    List<Client> getByIds(List<Long> tasksIds);
}
