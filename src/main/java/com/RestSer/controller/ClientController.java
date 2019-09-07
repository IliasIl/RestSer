package com.RestSer.controller;

import com.RestSer.domain.Client;
import com.RestSer.domain.dto.Status;
import com.RestSer.domain.dto.Views;
import com.RestSer.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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

    @PostMapping("client")
    public String createClient(@RequestBody Client client) throws JsonProcessingException {
        return clientService.constClient(client);
    }
}
