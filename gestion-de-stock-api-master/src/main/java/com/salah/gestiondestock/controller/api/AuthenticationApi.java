package com.salah.gestiondestock.controller.api;

import io.swagger.annotations.Api;

import static com.salah.gestiondestock.utils.Constants.AUTHENTICATION_ENDPOINT;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.salah.gestiondestock.dto.auth.AuthenticationRequest;
import com.salah.gestiondestock.dto.auth.AuthenticationResponse;

@Api("authentication")
public interface AuthenticationApi {

  @PostMapping(AUTHENTICATION_ENDPOINT + "/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);

}
