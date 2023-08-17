package org.acme.movimientos.controllers.banksController;

import java.util.logging.Logger;

import org.acme.movimientos.dtos.AddThirdAccountsCLABEReq;
import org.acme.movimientos.dtos.InfoDto;
import org.acme.movimientos.dtos.catalogBank.RequestGetBankClabe;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/efectivo/getBankClabe")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GetBankClabe {
    private static final Logger LOG = Logger.getLogger(GetBankClabe.class.getName());

    @POST
    @Path("")
    public Response getBankporClabe(@RequestBody RequestGetBankClabe bankdto , @HeaderParam("Authorization") String authorizationHeader) {
        String response=null;
        InfoDto infodto = new InfoDto();
        try {
            // Validar que los parámetros no sean nulos o vacíos
        	 //Validaciones de datos entrada del request
            if (bankdto.getClabe() == null || bankdto.getClabe().isEmpty()) {
            	LOG.severe("El parámetro de clabe es requerido, transaccion "+infodto.getTransactionID());
                infodto.setHttpStatus(400);
                infodto.setMessage("El parámetro de clabe es requerido");
                infodto.setData(null);
                return Response.status(Response.Status.BAD_REQUEST).entity(infodto).build();
            }else{
            	LOG.info("La clabe es: "+bankdto.getClabe());
            }

            // Verificar si el encabezado de autorización es nulo o está vacío
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                LOG.severe("Se requiere el encabezado de autorización");
                infodto.setHttpStatus(400);
                infodto.setMessage("e requiere el encabezado de autorizacion");
                infodto.setData(null);
                return Response.status(Response.Status.BAD_REQUEST).entity(infodto).build();
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
            		+ "  \"idBank\": \"006\",\n"
            		+ "  \"bank\": \"BANCOMEXT\"\n"

            		+ "  }\n"
            		+ "}").build();

        } catch (Exception e) {
            LOG.severe("Error al consultar la informacion del cliente: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al consultar la información del cliente: "+e.getMessage()).build();
        }
    }
    
}
