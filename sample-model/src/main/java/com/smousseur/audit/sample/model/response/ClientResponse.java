package com.smousseur.audit.sample.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientResponse {
  private Integer id;
  private String name;
  private String email;
}
