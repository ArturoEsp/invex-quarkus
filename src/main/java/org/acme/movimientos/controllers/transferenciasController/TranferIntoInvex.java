package org.acme.movimientos.controllers.transferenciasController;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import org.acme.movimientos.dtos.InfoDto;
import org.acme.movimientos.dtos.traspasoEntreCuentasInvex.TraspasosInvReq;
import org.acme.movimientos.utilities.TokenUtility;

import java.util.logging.Logger;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;


@Path("/efectivo/transferIntoInvex")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TranferIntoInvex {

      private static final Logger LOG = Logger.getLogger(TranferIntoInvex.class.getName());

    @POST
    @Path("")
    public Response transferenciaEntreCuentasInvex(@RequestBody TraspasosInvReq traspasosInvReq, @HeaderParam("Authorization") String authorizationHeader) {
        InfoDto infodto = new InfoDto();
        LOG.info("Proceso endpoint mockeado Transferencia entre cuentas");
        try {
           if(!validaParametros(traspasosInvReq)){
                LOG.severe("BadRequest, parametro");
                infodto.setHttpStatus(400);
                infodto.setTransactionID("550e8400-e29b-41d4-a716");
                infodto.setMessage("Verificar los campos del request");
                infodto.setData(null);
                
                return Response.status(Response.Status.BAD_REQUEST).entity(infodto).build();
            }

            // Verificar si el encabezado de autorización es nulo o está vacío
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                LOG.severe("Se requiere el encabezado de autorizacion");
                LOG.severe("Error al ejecutar el ws ThirdAccountCLABE,Se requiere el encabezado de autorizacion");
                infodto.setHttpStatus(400);
                infodto.setTransactionID("550e8400-e29b-41d4-a716");
                infodto.setMessage("Se requiere el encabezado de autorizacion");
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
            infodto.setHttpStatus(200);
            infodto.setTransactionID("550e8400-e29b-41d4-a716");
            infodto.setMessage("Se realizo correctamente la transferencia entre Cuentas");
            infodto.setToken(authorizationHeader);
            String respuesta = "{\n"
            + "    \"canonicalInvexMessage\": {\n"
            + "        \"header\": {\n"
            + "            \"transactionId\": \"550e8400-e29b-41d4-a716\",\n"
            + "            \"requestSystem\": \"PORTAL\",\n"
            + "            \"requestDateTime\": \"2023-06-22T14:41:14.012-05:00\",\n"
            + "            \"responseDateTime\": \"2023-06-22T14:41:15.012-05:00\",\n"
            + "            \"statusResponse\": \"OK\"\n"
            + "        },\n"
            + "        \"body\": {\n"
            + "            \"transferAccountRes\": {\n"
            + "                \"transactionID\": \"001BUSS232000248\",\n"
            + "                \"creditReference\": \"1234567\",\n"
            + "                \"debitReference\": \"123456\"\n"
            + "            }\n"
            + "        }\n"
            + "    }\n"
            + "}";
            infodto.setData(respuesta);
            System.out.println(infodto.getData().toString());
    
            return Response.ok(infodto).build();

        } catch (Exception e) {
            LOG.severe("Error al consultar la informacion del cliente: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al consultar la información del cliente: "+e.getMessage()).build();
        }
    }

    private Boolean validaParametros(TraspasosInvReq traspasosInvReq){
        if(traspasosInvReq.getCanonicalInvexMessage() == null) return false;
        if(traspasosInvReq.getCanonicalInvexMessage().getHeader().getRequestSystem().isEmpty() ||
            traspasosInvReq.getCanonicalInvexMessage().getHeader().getRequestSystem() == null
        )return false;
        if(traspasosInvReq.getCanonicalInvexMessage().getHeader().getTransactionId().isEmpty() ||
            traspasosInvReq.getCanonicalInvexMessage().getHeader().getTransactionId() == null
        )return false;
        if(traspasosInvReq.getCanonicalInvexMessage().getBody().getTransferAccountReq().getAmmount().isEmpty() ||
            traspasosInvReq.getCanonicalInvexMessage().getBody().getTransferAccountReq().getAmmount() == null
        )return false;
        if(traspasosInvReq.getCanonicalInvexMessage().getBody().getTransferAccountReq().getOriginAccount().isEmpty() ||
            traspasosInvReq.getCanonicalInvexMessage().getBody().getTransferAccountReq().getOriginAccount() == null
        )return false;
        if(traspasosInvReq.getCanonicalInvexMessage().getBody().getTransferAccountReq().getDestinationAccount().isEmpty() ||
            traspasosInvReq.getCanonicalInvexMessage().getBody().getTransferAccountReq().getDestinationAccount() == null
        )return false;
        if(traspasosInvReq.getCanonicalInvexMessage().getBody().getTransferAccountReq().getCurrency().isEmpty() ||
            traspasosInvReq.getCanonicalInvexMessage().getBody().getTransferAccountReq().getCurrency() == null
        )return false;
        return true;
    }
        

}
