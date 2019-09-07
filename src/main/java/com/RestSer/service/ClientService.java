package com.RestSer.service;

import com.RestSer.domain.Client;
import com.RestSer.domain.dto.Balance;
import com.RestSer.domain.dto.Status;
import com.RestSer.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepo clientRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
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
