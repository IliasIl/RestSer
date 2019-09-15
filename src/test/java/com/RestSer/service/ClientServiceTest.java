package com.RestSer.service;

import com.RestSer.domain.Client;
import com.RestSer.domain.dto.ClientDto;
import com.RestSer.domain.dto.Status;
import com.RestSer.repo.ClientRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepo clientRepo;

    @Test
    public void constClient() throws JsonProcessingException {
        /*Проверим, что у пустого клиента не будет осуществлен поиск в БД,
          а также добавление. Возврат будет с кодом 2.*/
        ClientDto client = new ClientDto();
        String str = clientService.constClient(client);
        Mockito.verify(clientRepo, Mockito.times(0)).findByLogin(ArgumentMatchers.anyString());
        Mockito.verify(clientRepo, Mockito.times(0)).save(ArgumentMatchers.any(Client.class));
        Assert.assertThat(str, Matchers.containsString("2"));
    }

    @Test
    public void constClient2() throws JsonProcessingException {
        /*Проверим, что в случае заполненного логина и типа будет вызван поиск в БД.*/
        ClientDto client = new ClientDto();
        client.setType("create");
        client.setLogin("Il");
        Mockito.doReturn(new Client()).when(clientRepo).findByLogin(client.getLogin());
        String str = clientService.constClient(client);
        Mockito.verify(clientRepo, Mockito.times(1)).findByLogin(ArgumentMatchers.anyString());
        /*Проверим, что в случае повторного сохранения
          клиента с одним и тем же логином не будет происходить сохраниния в БД.*/
        Assert.assertThat(str, Matchers.containsString("1"));
        Mockito.verify(clientRepo, Mockito.times(0)).save(ArgumentMatchers.any(Client.class));
    }

    @Test
    public void balanceClient() {
        /*Проверим, что будет код 3 в случае если такого пользователя нет в БД.*/
        ClientDto client = new ClientDto();
        Status status = clientService.balanceClient(client);
        Mockito.verify(clientRepo, Mockito.times(1))
                .findByLogin(null);
        Mockito.verify(clientRepo, Mockito.times(0))
                .findByLoginAndPassword(null, null);
        Assert.assertThat(String.valueOf(status.getResult()), Matchers.containsString("3"));
    }
}