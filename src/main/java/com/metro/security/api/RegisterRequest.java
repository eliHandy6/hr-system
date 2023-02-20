package com.metro.security.api;

import com.metro.security.specification.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private  String name;
  private String email;
  private String password;
  private RoleName role;

}
