package com.smousseur.audit.sample.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientRequest {
  private String name;
  private String email;
}
