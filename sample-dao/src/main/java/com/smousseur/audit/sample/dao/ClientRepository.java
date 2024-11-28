package com.smousseur.audit.sample.dao;

import com.smousseur.audit.sample.model.entity.Client;
import java.util.Optional;

public class ClientRepository {
  public Optional<Client> findByNameAndEmail(String name, String email) {
    return Optional.of(Client.builder().name(name).email(email).build());
  }
}
