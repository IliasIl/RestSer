package com.RestSer.service;

import com.RestSer.domain.Client;
import com.RestSer.domain.dto.Balance;
import com.RestSer.domain.dto.ClientDto;
import com.RestSer.domain.dto.Status;
import com.RestSer.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private static final String ST1 = "create";
    private static final String ST2 = "get-balance";

    private final ClientRepo clientRepo;


    @Autowired
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    public Status constClient(ClientDto client) {
        String type = client.getType();
        if (type != null && type.equalsIgnoreCase(ST1)) {
            if (client.getLogin() == null) {
                return new Status(2);
            }
            return createClient(client);
        } else if (type != null && type.equalsIgnoreCase(ST2)) {
            if (client.getLogin() == null) {
                return new Status(2);
            }
            Status status = balanceClient(client);
            if (status.getResult() == 0) {
                return status;
            } else {
                return status;
            }
        } else {
            return new Status(2);
        }
    }

    public Status createClient(ClientDto client) {
        Client user = clientRepo.findByLogin(client.getLogin());
        if (user != null) {
            return new Status(1);
        } else {
            Client client1 = new Client();
            client1.setBalance(0.0);
            client1.setLogin(client.getLogin());
            client1.setPassword(client.getPassword());
            clientRepo.save(client1);
            return new Status(0, new Balance(client1.getBalance()));
        }
    }

    public Status balanceClient(ClientDto client) {
        Client user = clientRepo.findByLogin(client.getLogin());
        if (user == null) {
            return new Status(3);

        } else {
            user = clientRepo.findByLoginAndPassword(client.getLogin(), client.getPassword());
            if (user != null) {
                return new Status(0, new Balance(user.getBalance()));
            } else {
                return new Status(4);
            }
        }
    }
}
