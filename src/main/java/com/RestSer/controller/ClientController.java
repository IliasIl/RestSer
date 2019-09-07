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
    private final ObjectWriter objectWriter;
    private final ObjectWriter objectWriter2;

    @Autowired
    public ClientController(ClientService clientService, ObjectMapper objectMapper) {
        this.clientService = clientService;
        ObjectMapper mapper = objectMapper
                .setConfig(objectMapper.getSerializationConfig());
        this.objectWriter = mapper.writerWithView(Views.Balance.class);
        this.objectWriter2 = mapper.writerWithView(Views.Full.class);
    }

    @PostMapping("client")
    public String createClient(@RequestBody Client client) throws JsonProcessingException {
        String type = client.getType();
        if (type != null && type.equalsIgnoreCase("create")) {
            if (client.getLogin() == null) {
                return objectWriter.writeValueAsString(new Status(2));
            }
            return objectWriter.writeValueAsString(clientService.createClient(client));
        } else if (type != null && type.equalsIgnoreCase("get-balance")) {
            if (client.getLogin() == null) {
                return objectWriter.writeValueAsString(new Status(2));
            }
            int a = clientService.balanceClient(client).getResult();
            Status status = clientService.balanceClient(client);
            if (a == 0) {
                return objectWriter2.writeValueAsString(status);
            } else if (a == 3) {
                return objectWriter.writeValueAsString(status);
            } else {
                return objectWriter.writeValueAsString(status);
            }

        } else {
            return objectWriter.writeValueAsString(new Status(2));
        }
    }
}
