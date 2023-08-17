package org.acme.movimientos.controllers.getTokenController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Logger;

import org.acme.movimientos.controllers.movimientosController.MovimientosController;
import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/generatetoken")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TokenController {
     private static final Logger LOG = Logger.getLogger(TokenController.class.getName());

     @GET
    @Path("")
    public String getToken() {
    	// Tiempo de expiración: 20 minutos desde ahora (en milisegundos)
    	long expirationTimeInMillis = System.currentTimeMillis() + (20 * 60 * 1000);


        String token =
                Jwt.issuer("https://example.com/issuer")
                        .upn("jdoe@quarkus.io")
                        .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                        .claim(Claims.birthdate.name(), "2001-07-13")
                        .claim("cui", "123456")
                        .claim("folio", "1234")
                        .expiresAt(expirationTimeInMillis) // Establecer la fecha de expiración
                        .sign();
        System.out.println(token);
        return token;
    }
}
