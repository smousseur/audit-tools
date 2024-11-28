package com.smousseur.audit.sample.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client {
  private Integer id;
  private String name;
  private String email;
  private String login;
  private String password;
}
