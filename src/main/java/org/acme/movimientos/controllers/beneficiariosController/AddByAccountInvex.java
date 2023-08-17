package org.acme.movimientos.controllers.beneficiariosController;

import java.util.logging.Logger;

import org.acme.movimientos.dtos.AddThirdAccountsInvexReq;
import org.acme.movimientos.utilities.TokenUtility;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/efectivo/addThirdAccountsInvex")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddByAccountInvex {
    private static final Logger LOG = Logger.getLogger(AddByAccountInvex.class.getName());
    @POST
    @Path("")
    public Response addThirdAccountsInvex(@RequestBody AddThirdAccountsInvexReq addThirdAccountsInvexReq , @HeaderParam("Authorization") String authorizationHeader) {
        String response=null;
        try {
            // Validar que los parámetros no sean nulos o vacíos
            if (
            		(addThirdAccountsInvexReq.getIdAccount() == null || addThirdAccountsInvexReq.getIdAccount().isEmpty() || addThirdAccountsInvexReq.getIdAccount().isBlank()) 
            		|| (addThirdAccountsInvexReq.getBeneficiaryAccount() == null || addThirdAccountsInvexReq.getBeneficiaryAccount().isEmpty() || addThirdAccountsInvexReq.getBeneficiaryAccount().isBlank())
            		|| (addThirdAccountsInvexReq.getCurpRfc() == null || addThirdAccountsInvexReq.getCurpRfc().isEmpty() || addThirdAccountsInvexReq.getCurpRfc().isBlank())
            		) {
                LOG.severe("Los parametros idAccount, beneficiaryAccount o curpRfc, son requeridos");
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
            		+ "    \"addThirdAccountsInvexRes\": {\n"
            		+ "      \"isAdded\": \"true\"\n"
            		+ "    }\n"
            		+ "  }\n"
            		+ "}").build();

        } catch (Exception e) {
            LOG.severe("Error al consultar la informacion del cliente: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al consultar la información del cliente: "+e.getMessage()).build();
        }
    }
}
