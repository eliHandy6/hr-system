package com.metro.auth.dtos;

import com.metro.auth.specification.RoleName;
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
