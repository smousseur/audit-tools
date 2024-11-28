package com.smousseur.audit.sample.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
  private Integer id;
  private String name;
  private String email;
  private String login;
  private String password;
}
