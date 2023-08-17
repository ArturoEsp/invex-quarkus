package org.acme.movimientos.security;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;

//import org.eclipse.microprofile.jwt.Claims;

//import io.smallrye.jwt.build.Jwt;

public class GenerateToken {
    /**
     * Generate JWT token
     */
    public static void main(String[] args) {
        Instant expirationTime = Instant.now().plus(3, ChronoUnit.HOURS);

        /*String token =
                Jwt.issuer("https://example.com/issuer")
                        .upn("jdoe@quarkus.io")
                        .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                        .claim(Claims.birthdate.name(), "2001-07-13")
                        .claim("cui", "123456")
                        .claim("folio", "0")
                        .sign();
        System.out.println(token);*/
    }
}