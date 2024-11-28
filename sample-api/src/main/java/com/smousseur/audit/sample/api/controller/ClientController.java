package com.smousseur.audit.sample.api.controller;

import com.smousseur.audit.sample.model.request.ClientRequest;
import com.smousseur.audit.sample.model.response.ClientResponse;
import com.smousseur.audit.sample.service.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
  private static final ClientService clientService = new ClientService();

  @PostMapping
  public ClientResponse save(@RequestBody ClientRequest request) {
    return clientService.save(request);
  }

  @GetMapping
  public ClientResponse find(@RequestParam String name, @RequestParam String email) {
    return clientService.find(name, email);
  }
}
