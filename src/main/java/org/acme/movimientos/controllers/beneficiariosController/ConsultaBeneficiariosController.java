package org.acme.movimientos.controllers.beneficiariosController;

import java.util.logging.Logger;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/efectivo/beneficiarios/{idCuenta}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaBeneficiariosController {
    private static final Logger LOG = Logger.getLogger(ConsultaBeneficiariosController.class.getName());


    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeneficiarios(@PathParam("idCuenta") String idCuenta, @HeaderParam("Authorization") String authorizationHeader) {
        // Verificar si el encabezado de autorización es nulo o está vacío
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            LOG.severe("Se requiere el encabezado de autorización");
            return Response.status(Response.Status.BAD_REQUEST).entity("Se requiere el encabezado de autorización").build();
        }else{
            LOG.info("El encabezado de autrizacion es: "+authorizationHeader);
        }

        try {
            LOG.info("ID Cuenta recibido :: " + Integer.parseInt(idCuenta));
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("El parametro idCuenta debe ser un " +
                    "numero").build();
        }



        return Response.ok("{\n"
        		+ "  \"transactionID\": \"57a318d4-1b23-41fa-baac-c01630ba2849\",\n"
        		+ "  \"httpStatus\": 200,\n"
        		+ "  \"timestamp\": \"2023-07-28T15:26:47.885759\",\n"
        		+ "  \"message\": \"Microservice working fine\",\n"
        		+ "  \"token\": \"" + authorizationHeader + "\"," 
        		+ "  \"data\": {\n"
        		+ "    \"beneficiaryAccount\": [\n"
        		+ "      {\n"
        		+ "        \"beneficiaryBank\": \"BBVA BANCOMER\",\n"
        		+ "        \"beneficiaryName\": \"JUAN CARLOS GONZALEZ SERNA\",\n"
        		+ "        \"beneficiaryBankId\": 40012,\n"
        		+ "        \"beneficiaryRFCCURP\": \"GOSJ780425CGA\"\n"
        		+ "      },\n"
        		+ "      {\n"
        		+ "        \"beneficiaryBank\": \"BBVA BANCOMER\",\n"
        		+ "        \"beneficiaryName\": \"JUAN CARLOS GONZALEZ SERNA   \",\n"
        		+ "        \"beneficiaryBankId\": 40012,\n"
        		+ "        \"beneficiaryRFCCURP\": \"ND\"\n"
        		+ "      },\n"
        		+ "      {\n"
        		+ "        \"beneficiaryBank\": \"BBVA BANCOMER\",\n"
        		+ "        \"beneficiaryAccount\": \"012060001115453029\",\n"
        		+ "        \"beneficiaryName\": \"Julen Lopetegui \",\n"
        		+ "        \"beneficiaryBankId\": 40012,\n"
        		+ "        \"beneficiaryRFCCURP\": \"SASL7412285X0\"\n"
        		+ "      },\n"
        		+ "      {\n"
        		+ "        \"beneficiaryBank\": \"BBVA BANCOMER\",\n"
        		+ "        \"beneficiaryAccount\": \"012180001939324191\",\n"
        		+ "        \"beneficiaryName\": \"TAPON CORONA, S.A. DE C.V.  \",\n"
        		+ "        \"beneficiaryBankId\": 40012,\n"
        		+ "        \"beneficiaryRFCCURP\": \"TCO800325PJ4\"\n"
        		+ "      },\n"
        		+ "      {\n"
        		+ "        \"beneficiaryBank\": \"BBVA BANCOMER\",\n"
        		+ "        \"beneficiaryAccount\": \"012180012583992984\",\n"
        		+ "        \"beneficiaryName\": \"JOAQUIN LICONA HERNANDEZ\",\n"
        		+ "        \"beneficiaryBankId\": 40012,\n"
        		+ "        \"beneficiaryRFCCURP\": \"LIHJ7608259F0\"\n"
        		+ "      },\n"
        		+ "      {\n"
        		+ "        \"beneficiaryBank\": \"BBVA BANCOMER\",\n"
        		+ "        \"beneficiaryAccount\": \"012180029800950605\",\n"
        		+ "        \"beneficiaryName\": \"BENITO DE ANDA PABLO\",\n"
        		+ "        \"beneficiaryBankId\": 40012,\n"
        		+ "        \"beneficiaryRFCCURP\": \"AAPB921226RB0\",\n"
        		+ "        \"beneficiaryMail\": \"jdeanda@invex.com\"\n"
        		+ "      },\n"
        		+ "      {\n"
        		+ "        \"beneficiaryBank\": \"SCOTIABANK\",\n"
        		+ "        \"beneficiaryAccount\": \"044180001011352444\",\n"
        		+ "        \"beneficiaryName\": \"JUAN CARLOS GONZALEZ SERNA\",\n"
        		+ "        \"beneficiaryBankId\": 40044,\n"
        		+ "        \"beneficiaryRFCCURP\": \"GOSJ780425CGA\",\n"
        		+ "        \"beneficiaryMail\": \"jgonzalez@invex.com\"\n"
        		+ "      },\n"
        		+ "      {\n"
        		+ "        \"beneficiaryBank\": \"Banco Invex S. A.\",\n"
        		+ "        \"beneficiaryAccount\": \"059180001456900051\",\n"
        		+ "        \"beneficiaryName\": \"JOSE LEONARDO HERNANDEZ TIBURCIO\",\n"
        		+ "        \"beneficiaryBankId\": 40059,\n"
        		+ "        \"beneficiaryRFCCURP\": \"ND\",\n"
        		+ "        \"relationType\": 99\n"
        		+ "      },\n"
        		+ "      {\n"
        		+ "        \"beneficiaryBank\": \"GEMELO INVEX MEXICO\",\n"
        		+ "        \"beneficiaryAccount\": 559180123456789100,\n"
        		+ "        \"beneficiaryName\": \"GEM INVX\",\n"
        		+ "        \"beneficiaryBankId\": 559,\n"
        		+ "        \"beneficiaryRFCCURP\": \"ND\"\n"
        		+ "      }\n"
        		+ "    ]\n"
        		+ "  }\n"
        		+ "}")
                .build();
    }

}
