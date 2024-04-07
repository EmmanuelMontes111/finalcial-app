package com.company.financial.app.infrastructure.adapter;

import com.company.financial.app.domain.model.Client;
import com.company.financial.app.domain.port.ClientPersistencePort;
import com.company.financial.app.infrastructure.adapter.entity.ClientEntity;
import com.company.financial.app.infrastructure.adapter.mapper.ClientEntityModelMapper;
import com.company.financial.app.infrastructure.adapter.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public List<Client> getAll() {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Client update(Client request) {
        return null;
    }

    @Override
    public List<Client> getByIds(List<Long> tasksIds) {
        return null;
    }
}
