package com.smousseur.audit.sample.service;

import com.smousseur.audit.sample.dao.ClientRepository;
import com.smousseur.audit.sample.model.request.ClientRequest;
import com.smousseur.audit.sample.model.response.ClientResponse;

public class ClientService {
  private static final ClientRepository clientRepository = new ClientRepository();

  public ClientResponse save(ClientRequest request) {
    return clientRepository
        .findByNameAndEmail(request.getName(), request.getEmail())
        .map(
            c -> ClientResponse.builder().name(request.getName()).email(request.getEmail()).build())
        .orElseThrow();
  }

  public ClientResponse find(String name, String email) {
    return clientRepository
        .findByNameAndEmail(name, email)
        .map(
            c ->
                ClientResponse.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .email(c.getEmail())
                    .build())
        .orElseThrow();
  }
}
