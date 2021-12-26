package org.ultims.playleagues.controller.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ultims.playleagues.contract.v1.request.AuthenticationRequest;
import org.ultims.playleagues.contract.v1.response.AuthenticationResponse;
import org.ultims.playleagues.service.TokenService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("")
    public AuthenticationResponse createToken(@RequestBody AuthenticationRequest request) throws Exception {
        return tokenService.createJwtToken(request);
    }

}
