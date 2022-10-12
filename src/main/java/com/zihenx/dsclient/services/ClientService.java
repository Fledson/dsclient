package com.zihenx.dsclient.services;

import com.zihenx.dsclient.dto.ClientDto;
import com.zihenx.dsclient.entities.Client;
import com.zihenx.dsclient.repositories.ClientRepository;
import com.zihenx.dsclient.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ClientService {

    @Autowired
    ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDto> findAll(PageRequest pageRequest) {

        Page<Client> list = repository.findAll(pageRequest);
        Page<ClientDto> clientsDto = list.map(client -> new ClientDto(client));

        return clientsDto;
    }

    @Transactional(readOnly = true)
    public ClientDto findById(Long id) {
        var client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ClientDto(client);
    }

    @Transactional
    public ClientDto insert(ClientDto dto) {
        Client entity = new Client();

        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);

        return new ClientDto(entity);
    }

    @Transactional
    public ClientDto update(Long id, ClientDto dto) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);

        return new ClientDto(entity);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    private void copyDtoToEntity(ClientDto dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
