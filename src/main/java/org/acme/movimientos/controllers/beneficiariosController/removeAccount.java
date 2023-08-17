package org.acme.movimientos.controllers.beneficiariosController;

import java.util.logging.Logger;

import org.acme.movimientos.dtos.RemoveThirdAccountsReq;
import org.acme.movimientos.utilities.TokenUtility;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/*@Path("/removeThirdAccounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)*/
public class removeAccount {
    /*private static final Logger LOG = Logger.getLogger(removeAccount.class.getName());
    @DELETE
    @Path("")
    public Response removeThirdAccounts(@RequestBody RemoveThirdAccountsReq removeThirdAccountsReq , @HeaderParam("Authorization") String authorizationHeader) {
        String response=null;
        try {
            // Validar que los parámetros no sean nulos o vacíos
            if (
            		(removeThirdAccountsReq.getThirdAccount() == null || removeThirdAccountsReq.getThirdAccount().isEmpty() || removeThirdAccountsReq.getThirdAccount().isBlank()) 
            		|| (removeThirdAccountsReq.getCheckbookNumber() == null || removeThirdAccountsReq.getCheckbookNumber().isEmpty() || removeThirdAccountsReq.getCheckbookNumber().isBlank())
            		|| (removeThirdAccountsReq.getIsBeneficiaryCreditCard() == null || removeThirdAccountsReq.getIsBeneficiaryCreditCard().isEmpty() || removeThirdAccountsReq.getIsBeneficiaryCreditCard().isBlank())
            		) {
                LOG.severe("Los parametros idAccount, clabe o curpRfc, son requeridos");
                return Response.status(Response.Status.BAD_REQUEST).entity("Los parametros idAccount, clabe o curpRfc, son requeridos").build();
            }

            // Verificar si el encabezado de autorización es nulo o está vacío
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                LOG.severe("Se requiere el encabezado de autorización");
                return Response.status(Response.Status.BAD_REQUEST).entity("Se requiere el encabezado de autorización").build();
            }else{
                LOG.info("El encabezado de autorizacion es: "+authorizationHeader);
            }

            // Verificar y obtener el "cui" del JWT
            String cui = "123456";//TokenUtility.verifyAndGetCui(authorizationHeader);
            LOG.info("CUI: "+cui);
            // Verificar y obtener el "cui" del JWT
            String folio = "1234";//TokenUtility.verifyAndGetFolio(authorizationHeader);
            LOG.info("Folio: "+folio);
      
            
            return Response.ok("{\n"
            		+ "  \"transactionID\": \"57a318d4-1b23-41fa-baac-c01630ba2849\",\n"
            		+ "  \"httpStatus\": 200,\n"
            		+ "  \"timestamp\": \"2023-07-28T15:26:47.885759\",\n"
            		+ "  \"message\": \"Microservice working fine\",\n"
            		+ "  \"token\": \"" + authorizationHeader + "\"," 
            		+ "  \"data\": {\n"
            		+ "    \"removeThirdAccountsRes\": {\n"
            		+ "      \"isRemoved\": \"true\"\n"
            		+ "    }\n"
            		+ "  }\n"
            		+ "}").build();

        } catch (Exception e) {
            LOG.severe("Error al consultar la informacion del cliente: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al consultar la información del cliente: "+e.getMessage()).build();
        }
    }*/
}
