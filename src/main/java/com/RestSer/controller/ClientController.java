package com.RestSer.controller;

import com.RestSer.domain.dto.ClientDto;
import com.RestSer.domain.dto.Status;
import com.RestSer.domain.dto.Views;
import com.RestSer.service.ClientService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/restser")
@RestController
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @JsonView(Views.Full.class)
    @PostMapping("client")
    public Status createClient(@RequestBody ClientDto client) {
        return clientService.constClient(client);
    }
}
