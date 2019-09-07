package com.RestSer.repo;

import com.RestSer.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Long> {
    Client findByLogin(String login);

    Client findByLoginAndPassword(String login, String password);
}
