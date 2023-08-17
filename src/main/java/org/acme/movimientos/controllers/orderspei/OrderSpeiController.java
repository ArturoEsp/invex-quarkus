package org.acme.movimientos.controllers.orderspei;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import org.acme.movimientos.dtos.AddThirdAccountsCLABEReq;
import org.acme.movimientos.dtos.AddThirdAccountsCreditCardReq;
import org.acme.movimientos.dtos.AddThirdAccountsDebitCardReq;
import org.acme.movimientos.dtos.AddThirdAccountsInvexReq;
import org.acme.movimientos.dtos.AddThirdAccountsMobileReq;
import org.acme.movimientos.dtos.CEPVoucherInquiryReqDto;
import org.acme.movimientos.dtos.InfoDto;
import org.acme.movimientos.dtos.MovimientosDto;
import org.acme.movimientos.dtos.RemoveThirdAccountsReq;
import org.acme.movimientos.dtos.orderspei.CreateSPEIOrderReqDto;
import org.acme.movimientos.services.CuentaService;
import org.acme.movimientos.services.CuentaServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import java.util.logging.Logger;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import io.smallrye.jwt.build.Jwt;

@Path("/efectivo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderSpeiController {

    private static final Logger LOG = Logger.getLogger(OrderSpeiController.class.getName());
    
    @Inject
    CuentaService cuentaService;




    // Verificar el Bearer Token y obtener el "cui"
    private String verifyAndGetCui(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            try {
                DecodedJWT decodedToken = JWT.decode(token);
                String cui = decodedToken.getClaim("cui").asString();

                return cui;
            } catch (Exception e) {
                // Manejar el error según tus necesidades
                throw new RuntimeException("Error al decodificar el JWT", e);
            }
        }

        throw new WebApplicationException("Bearer token invalido", Response.Status.UNAUTHORIZED);
    }

    // Verificar el Bearer Token y obtener el "cui"
    private String verifyAndGetFolio(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            try {
                DecodedJWT decodedToken = JWT.decode(token);
                String folio = decodedToken.getClaim("folio").asString();

                return folio;
            } catch (Exception e) {
                // Manejar el error según tus necesidades
                throw new RuntimeException("Error al decodificar el JWT", e);
            }
        }

        throw new WebApplicationException("Bearer token invalido", Response.Status.UNAUTHORIZED);
    }



    @POST
    @Path("/createSPEIOrder")
    public Response addThirdAccountsCLABE(@RequestBody CreateSPEIOrderReqDto createSPEIOrderReqDto, @HeaderParam("Authorization") String authorizationHeader) {
        String response=null;
        InfoDto infodto = new InfoDto();
        LOG.info("Proceso endpoint mockeado CreateSPEIOrder");
        try {
        	// Validar que los parámetros no sean nulos o vacíos
            if (
            		(createSPEIOrderReqDto.getCounterpartCode() == null || createSPEIOrderReqDto.getCounterpartCode().isEmpty() || createSPEIOrderReqDto.getCounterpartCode().isBlank()) 
            		|| (createSPEIOrderReqDto.getPaymentAmount() == null || createSPEIOrderReqDto.getPaymentAmount().isEmpty() || createSPEIOrderReqDto.getPaymentAmount().isBlank())
            		|| (createSPEIOrderReqDto.getIdAccount() == null || createSPEIOrderReqDto.getIdAccount().isEmpty() || createSPEIOrderReqDto.getIdAccount().isBlank())
            		|| (createSPEIOrderReqDto.getPayerName()== null || createSPEIOrderReqDto.getPayerName().isEmpty() || createSPEIOrderReqDto.getPayerName().isBlank())
            		//|| (createSPEIOrderReqDto.getBeneficiaryAccuount() == null || createSPEIOrderReqDto.getBeneficiaryAccuount().isEmpty() || createSPEIOrderReqDto.getBeneficiaryAccuount().isBlank())
            		|| (createSPEIOrderReqDto.getPaymentSubject() == null || createSPEIOrderReqDto.getPaymentSubject().isEmpty() || createSPEIOrderReqDto.getPaymentSubject().isBlank())
            		|| (createSPEIOrderReqDto.getPaymentReferenceNumber() == null || createSPEIOrderReqDto.getPaymentReferenceNumber().isEmpty() || createSPEIOrderReqDto.getPaymentReferenceNumber().isBlank())
            		) {
                LOG.severe("Los parametros counterpartCode, paymentAmount, payerAccount, payerName, beneficiaryAccuount, paymentSubject o paymentReferenceNumber, son requeridos");
                LOG.severe("Error al ejecutar el ws ThirdAccountCLABE");
                infodto.setHttpStatus(400);
                infodto.setMessage("Los parametros counterpartCode, paymentAmount, payerAccount, payerName, beneficiaryAccuount, paymentSubject o paymentReferenceNumber, son requeridos");
                infodto.setData(null);
                
                return Response.status(Response.Status.BAD_REQUEST).entity(infodto).build();
            }

            // Verificar si el encabezado de autorización es nulo o está vacío
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                LOG.severe("Se requiere el encabezado de autorizacion");
                LOG.severe("Error al ejecutar el ws ThirdAccountCLABE,Se requiere el encabezado de autorizacion");
                infodto.setHttpStatus(400);
                infodto.setMessage("Se requiere el encabezado de autorizacion");
                infodto.setData(null);
                
                return Response.status(Response.Status.BAD_REQUEST).entity(infodto).build();
            }else{
                LOG.info("El encabezado de autorizacion es: "+authorizationHeader);
            }

            // Verificar y obtener el "cui" del JWT
            String cui = verifyAndGetCui(authorizationHeader);
            LOG.info("CUI: "+cui);
            // Verificar y obtener el "cui" del JWT
            String folio = verifyAndGetFolio(authorizationHeader);
            LOG.info("Folio: "+folio);
      
            return Response.ok("{\n"
            		+ "    \"transactionID\": \"2a857448-8d41-49b1-a12d-f450570371c1\",\n"
            		+ "    \"httpStatus\": 200,\n"
            		+ "    \"timestamp\": \"2023-08-04T17:37:06.486428700\",\n"
            		+ "    \"message\": \"Microservice working fine\",\n"
            		+ "    \"data\": {\n"
            		+ "        \"createSPEIOrderRes\": {\n"
            		+ "            \"contractReferenceNumber\": \"001BUSS232000248\",\n"
            		+ "            \"traceNumber\": \"1234567\",\n"
            		+ "            \"id\": \"123456\"\n"
            		+ "        }\n"
            		+ "    }\n"
            		+ "}").build();

        } catch (Exception e) {
            LOG.severe("Error al consultar la informacion del cliente: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al consultar la información del cliente: "+e.getMessage()).build();
        }
    }
    
    
}
