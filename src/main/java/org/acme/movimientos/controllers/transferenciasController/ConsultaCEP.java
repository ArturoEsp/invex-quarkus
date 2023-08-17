package org.acme.movimientos.controllers.transferenciasController;

import java.util.logging.Logger;


import org.acme.movimientos.dtos.CEPVoucherInquiryReqDto;
import org.acme.movimientos.dtos.InfoDto;
import org.acme.movimientos.utilities.TokenUtility;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/*@Path("/cepvoucherinquiry")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)*/
public class ConsultaCEP {
    /*private static final Logger LOG = Logger.getLogger(ConsultaCEP.class.getName());
     @POST
    @Path("")
    public Response createSPEIOrder(@RequestBody CEPVoucherInquiryReqDto cEPVoucherInquiryReqDto , @HeaderParam("Authorization") String authorizationHeader) {
        String response=null;
        InfoDto infodto = new InfoDto();
        cEPVoucherInquiryReqDto.setAssambleCEPLink("true");
        //cEPVoucherInquiryReqDto.setOrigin(origin);
        cEPVoucherInquiryReqDto.setSearchCriterion("T");
        try {
            // Validar que los parámetros no sean nulos o vacíos
        	if (
            		(cEPVoucherInquiryReqDto.getAssambleCEPLink() == null || cEPVoucherInquiryReqDto.getAssambleCEPLink().isEmpty() || cEPVoucherInquiryReqDto.getAssambleCEPLink().isBlank()) 
            		|| (cEPVoucherInquiryReqDto.getTrackingKey() == null || cEPVoucherInquiryReqDto.getTrackingKey().isEmpty() || cEPVoucherInquiryReqDto.getTrackingKey().isBlank())
            		|| (cEPVoucherInquiryReqDto.getSearchCriterion() == null || cEPVoucherInquiryReqDto.getSearchCriterion().isEmpty() || cEPVoucherInquiryReqDto.getSearchCriterion().isBlank())
            		|| (cEPVoucherInquiryReqDto.getBeneficiaryAccoun()== null || cEPVoucherInquiryReqDto.getBeneficiaryAccoun().isEmpty() || cEPVoucherInquiryReqDto.getBeneficiaryAccoun().isBlank())
            		|| (cEPVoucherInquiryReqDto.getIssuing() == null || cEPVoucherInquiryReqDto.getIssuing().isEmpty() || cEPVoucherInquiryReqDto.getIssuing().isBlank())
            		|| (cEPVoucherInquiryReqDto.getOperationDate() == null || cEPVoucherInquiryReqDto.getOperationDate().isEmpty() || cEPVoucherInquiryReqDto.getOperationDate().isBlank())
            		|| (cEPVoucherInquiryReqDto.getOrigin() == null || cEPVoucherInquiryReqDto.getOrigin().isEmpty() || cEPVoucherInquiryReqDto.getOrigin().isBlank())
            		|| (cEPVoucherInquiryReqDto.getOperationAmmount() == null || cEPVoucherInquiryReqDto.getOperationAmmount().isEmpty() || cEPVoucherInquiryReqDto.getOperationAmmount().isBlank())
            		|| (cEPVoucherInquiryReqDto.getReceiver() == null || cEPVoucherInquiryReqDto.getReceiver().isEmpty() || cEPVoucherInquiryReqDto.getReceiver().isBlank())
            		) {
                LOG.severe("Los parametros trackingKey, issuing, operationDate, operationAmmount o receiver, son requeridos");
                LOG.severe("Error al ejecutar el ws cepvoucherinquiry");
                infodto.setHttpStatus(400);
                infodto.setMessage("Los parametros trackingKey, issuing, operationDate, operationAmmount o receiver, son requeridos");
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
            String cui = "123456";//TokenUtility.verifyAndGetCui(authorizationHeader);
            LOG.info("CUI: "+cui);
            // Verificar y obtener el "cui" del JWT
            String folio = "1234";//TokenUtility.verifyAndGetFolio(authorizationHeader);
            LOG.info("Folio: "+folio);
      
            return Response.ok("{"
            		+ "\"transactionID\": \"bbdcd06b-949c-45a7-8b01-ee892f9a9dd1\","
            		+ "\"httpStatus\": 200,"
            		+ "\"timestamp\": \"2023-08-03T18:34:41.836266300\","
            		+ "\"message\": \"Microservice working fine\","
            		+ "\"token\": \"" + authorizationHeader + "\"," 
            		+ "\"data\": {"
            		+ "\"CEPVoucherInquiryRes\": {"
            		+ "\"receiverBanxico\": \"40059\","
            		+ "\"operationDateBanxico\": \"20191119\","
            		+ "\"URLbanxico\": \"https://www.banxico.org.mx/cep/go?\","
            		+ "\"CEP\": \"FCx3px9EAlHRkKYdSfqyh7hLrX9w2VWjmnazc%2FJpBSXd1c4Dg4xvUfoOKNpsRc34IrhTMAk5YwPbYE4j2T968Q%3D%3D\","
            		+ "\"URLbanxicoCEP\": \"https://www.banxico.org.mx/cep/go?i=40059&s=20191119&d=FCx3px9EAlHRkKYdSfqyh7hLrX9w2VWjmnazc%2FJpBSXd1c4Dg4xvUfoOKNpsRc34IrhTMAk5YwPbYE4j2T968Q%3D%3D\""
            		+ "}"
            		+ "}"
            		+ "}").build();

        } catch (Exception e) {
            LOG.severe("Error al consultar la informacion del cliente: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al consultar la información del cliente: "+e.getMessage()).build();
        }
    }*/
}
