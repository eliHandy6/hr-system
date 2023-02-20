package com.metro.security.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

  @GetMapping
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Hello from secured endpoint");
  }
  @GetMapping("/almost")
  @PreAuthorize("hasRole('ADMIN')")
  //@PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> roleBased(){
    return ResponseEntity.ok("what the hell is going on");
  }


}
