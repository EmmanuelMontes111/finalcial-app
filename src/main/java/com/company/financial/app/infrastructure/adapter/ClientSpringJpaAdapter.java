package com.company.financial.app.infrastructure.adapter;

import com.company.financial.app.application.service.ClientEntitySpecification;
import com.company.financial.app.domain.model.Client;
import com.company.financial.app.domain.port.ClientPersistencePort;
import com.company.financial.app.infrastructure.adapter.entity.ClientEntity;
import com.company.financial.app.infrastructure.adapter.mapper.ClientEntityModelMapper;
import com.company.financial.app.infrastructure.adapter.repository.ClientRepository;
import com.company.financial.app.infrastructure.rest.controller.ResExceptiont.DataClientException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientSpringJpaAdapter implements ClientPersistencePort {

    @Autowired
    ClientEntityModelMapper clientEntityModelMapper;
    @Autowired
    ClientRepository clientRepository;

    @Override
    public Client create(Client request) {
        ClientEntity clientEntityToSave = clientEntityModelMapper.modelToEntity(request);
        ClientEntity clientEntitySaved = clientRepository.save(clientEntityToSave);
        return clientEntityModelMapper.entityToModel(clientEntitySaved);
    }

    @Override
    public Client getById(Long id) {
        Optional<ClientEntity> clientEntitySaved = clientRepository.findById(id);
        if (clientEntitySaved.isPresent()) {
            return clientEntityModelMapper.entityToModel(clientEntitySaved.get());
        } else {
            throw new DataClientException("El id del cliente no existe en la base de datos");
        }

    }

    @Override
    public List<Client> getAll() {
        List<ClientEntity> clientEntities;
        List<Client> clientModels = new ArrayList<>();

        clientEntities = clientRepository.findAll();
        for (ClientEntity clientEntity : clientEntities) {
            clientModels.add(clientEntityModelMapper.entityToModel(clientEntity));
        }

        return clientModels;
    }


    @Override
    public List<Client> getClientByFilter(Client clientModel, String operatorComparator) {
        List<ClientEntity> clientEntities;
        List<Client> clientModels = new ArrayList<>();
        ClientEntity clientEntity =  clientEntityModelMapper.modelToEntity(clientModel);
        Specification<ClientEntity> specification = ClientEntitySpecification.getClientsByFilter(clientEntity, operatorComparator);
        clientEntities  = clientRepository.findAll(specification);
        for (ClientEntity client : clientEntities) {
            clientModels.add(clientEntityModelMapper.entityToModel(client));
        }
        return clientModels;
    }

    @Override
    public void deleteClient(Client client) {
        ClientEntity clientEntityToSave = clientEntityModelMapper.modelToEntity(client);
        clientRepository.delete(clientEntityToSave);
    }

    @Override
    public Client update(Client request) {
        ClientEntity clientEntityToUpdate = clientEntityModelMapper.modelToEntity(request);
        ClientEntity clientEntitySaved = clientRepository.save(clientEntityToUpdate);
        return clientEntityModelMapper.entityToModel(clientEntitySaved);
    }

    @Override
    public List<Client> getByIds(List<Long> tasksIds) {
        return null;
    }


}
