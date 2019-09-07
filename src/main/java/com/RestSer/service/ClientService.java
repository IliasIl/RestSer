package com.RestSer.service;

import com.RestSer.domain.Client;
import com.RestSer.domain.dto.Balance;
import com.RestSer.domain.dto.Status;
import com.RestSer.domain.dto.Views;
import com.RestSer.repo.ClientRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepo clientRepo;
    private final ObjectWriter objectWriter;
    private final ObjectWriter objectWriter2;


    @Autowired
    public ClientService(ClientRepo clientRepo, ObjectMapper objectMapper) {
        this.clientRepo = clientRepo;
        ObjectMapper mapper = objectMapper
                .setConfig(objectMapper.getSerializationConfig());
        this.objectWriter = mapper.writerWithView(Views.Balance.class);
        this.objectWriter2 = mapper.writerWithView(Views.Full.class);
    }

    public String constClient(Client client) throws JsonProcessingException {
        String type = client.getType();
        if (type != null && type.equalsIgnoreCase("create")) {
            if (client.getLogin() == null) {
                return objectWriter.writeValueAsString(new Status(2));
            }
            return objectWriter.writeValueAsString(createClient(client));
        } else if (type != null && type.equalsIgnoreCase("get-balance")) {
            if (client.getLogin() == null) {
                return objectWriter.writeValueAsString(new Status(2));
            }
            int a = balanceClient(client).getResult();
            Status status = balanceClient(client);
            String resp=objectWriter.writeValueAsString(status);
            if (a == 0) {
                return objectWriter2.writeValueAsString(status);
            } else if (a == 3) {
                return resp;
            } else {
                return resp;
            }

        } else {
            return objectWriter.writeValueAsString(new Status(2));
        }
    }

    public Status createClient(Client client) {
        Client user = clientRepo.findByLogin(client.getLogin());
        if (user != null) {
            return new Status(1);
        } else {
            client.setBalance(0);
            clientRepo.save(client);
            return new Status(0);
        }
    }

    public Status balanceClient(Client client) {
        Client user = clientRepo.findByLogin(client.getLogin());
        if (user == null) {
            return new Status(3);

        } else {
            user = clientRepo.findByLoginAndPassword(client.getLogin(), client.getPassword());
            if (user != null) {
                return new Status(0, new Balance(client.getBalance()));
            } else {
                return new Status(4);
            }
        }
    }
}
