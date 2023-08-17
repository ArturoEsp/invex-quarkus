package org.acme.movimientos.controllers.consultaCuentasController;

import java.util.logging.Logger;

import org.acme.movimientos.utilities.TokenUtility;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/*@Path("/cuentas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)*/
public class CuentasController {

     /*private static final Logger LOG = Logger.getLogger(CuentasController.class.getName());

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeneficiarios(@HeaderParam("Authorization") String authorizationHeader) {
        // Verificar si el encabezado de autorización es nulo o está vacío
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            LOG.severe("Se requiere el encabezado de autorización");
            return Response.status(Response.Status.BAD_REQUEST).entity("Se requiere el encabezado de autorización").build();
        }else{
            LOG.info("El encabezado de autrizacion es: "+authorizationHeader);
        }

        // Verificar y obtener el "cui" del JWT
        String cui = "123456";//TokenUtility.verifyAndGetCui(authorizationHeader);
        LOG.info("CUI: "+cui);
        // Verificar y obtener el "cui" del JWT
        String folio = "1234";//TokenUtility.verifyAndGetFolio(authorizationHeader);
        LOG.info("getMovimientosV2 folio: "+folio);

        return Response.ok("{\n"
        		+ "  \"transactionID\": \"57a318d4-1b23-41fa-baac-c01630ba2849\",\n"
        		+ "  \"httpStatus\": 200,\n"
        		+ "  \"timestamp\": \"2023-07-28T15:26:47.885759\",\n"
        		+ "  \"message\": \"Microservice working fine\",\n"
        		+ "  \"token\": \"" + authorizationHeader + "\"," 
        		+ "  \"data\": {\n"
        		+ "    \"account\": [\n"
        		+ "      {\n"
        		+ "        \"customerNumber\": \"00000571\",\n"
        		+ "        \"idAccount\": 1,\n"
        		+ "        \"customerAccount\": \"00101003326\",\n"
        		+ "        \"name\": \"CARLOS NORIEGA ROMERO\",\n"
        		+ "        \"currentBalance\": \"2942\",\n"
        		+ "        \"availableBalance\": \"2942\",\n"
        		+ "        \"balanceForLiquidate\": \"0\",\n"
        		+ "        \"currency\": \"MXN\",\n"
        		+ "        \"lastTransactionDate\": \"\",\n"
        		+ "        \"clabe\": \"059180001155100026\",\n"
        		+ "        \"type\": \"CTAEJE\",\n"
        		+ "        \"opera\": \"S\",\n"
        		+ "        \"typeS\": \"A\",\n"
        		+ "        \"acclass\": \"CTAEJE\"\n"
        		+ "      },\n"
        		+ "      {\n"
        		+ "        \"customerNumber\": \"00007391\",\n"
        		+ "        \"idAccount\": 2,\n"
        		+ "        \"customerAccount\": \"00101011551\",\n"
        		+ "        \"name\": \"JUAN CARLOS GONZALEZ SERNA\",\n"
        		+ "        \"currentBalance\": \"5942\",\n"
        		+ "        \"availableBalance\": \"-259296.14\",\n"
        		+ "        \"balanceForLiquidate\": \"-259296.14\",\n"
        		+ "        \"currency\": \"MXN\",\n"
        		+ "        \"lastTransactionDate\": \"\",\n"
        		+ "        \"clabe\": \"059180001155100027\",\n"
        		+ "        \"type\": \"CTAEJE\",\n"
        		+ "        \"opera\": \"S\",\n"
        		+ "        \"typeS\": \"A\",\n"
        		+ "        \"retTx\": \"\",\n"
        		+ "        \"dumbbell\": \"00011551\",\n"
        		+ "        \"acclass\": \"CTAEJE\"\n"
        		+ "      },\n"
        		+ "      {\n"
        		+ "        \"customerNumber\": \"00007391\",\n"
        		+ "        \"idAccount\": 3,\n"
        		+ "        \"customerAccount\": \"00101010328\",\n"
        		+ "        \"name\": \"MOISES JIMENEZ MACIAS\",\n"
        		+ "        \"currentBalance\": \"7742\",\n"
        		+ "        \"availableBalance\": \"-259296.14\",\n"
        		+ "        \"balanceForLiquidate\": \"-259296.14\",\n"
        		+ "        \"currency\": \"MXN\",\n"
        		+ "        \"lastTransactionDate\": \"\",\n"
        		+ "        \"clabe\": \"059180001155100028\",\n"
        		+ "        \"type\": \"CTAEJE\",\n"
        		+ "        \"opera\": \"S\",\n"
        		+ "        \"typeS\": \"A\",\n"
        		+ "        \"retTx\": \"\",\n"
        		+ "        \"dumbbell\": \"00011551\",\n"
        		+ "        \"acclass\": \"CTAEJE\"\n"
        		+ "      },\n"
        		+ "      {\n"
        		+ "        \"customerNumber\": \"00007392\",\n"
        		+ "        \"idAccount\": 4,\n"
        		+ "        \"customerAccount\": \"00105010328\",\n"
        		+ "        \"name\": \"LILITH ARISTA\",\n"
        		+ "        \"currentBalance\": \"7742\",\n"
        		+ "        \"availableBalance\": \"-259296.14\",\n"
        		+ "        \"balanceForLiquidate\": \"-259296.14\",\n"
        		+ "        \"currency\": \"USD\",\n"
        		+ "        \"lastTransactionDate\": \"\",\n"
        		+ "        \"clabe\": \"059180001155100068\",\n"
        		+ "        \"type\": \"CTAEJE\",\n"
        		+ "        \"opera\": \"S\",\n"
        		+ "        \"typeS\": \"A\",\n"
        		+ "        \"retTx\": \"\",\n"
        		+ "        \"dumbbell\": \"00011551\",\n"
        		+ "        \"acclass\": \"CTAEJE\"\n"
        		+ "      },\n"
        		+ "      {\n"
        		+ "        \"customerNumber\": \"00007392\",\n"
        		+ "        \"idAccount\": 5,\n"
        		+ "        \"customerAccount\": \"00105010328\",\n"
        		+ "        \"name\": \"Alfredo Garcia\",\n"
        		+ "        \"currentBalance\": \"7742\",\n"
        		+ "        \"availableBalance\": \"-259296.14\",\n"
        		+ "        \"balanceForLiquidate\": \"-259296.14\",\n"
        		+ "        \"currency\": \"JPY\",\n"
        		+ "        \"lastTransactionDate\": \"\",\n"
        		+ "        \"clabe\": \"059180001155100068\",\n"
        		+ "        \"type\": \"CTAEJE\",\n"
        		+ "        \"opera\": \"S\",\n"
        		+ "        \"typeS\": \"A\",\n"
        		+ "        \"retTx\": \"\",\n"
        		+ "        \"dumbbell\": \"00011551\",\n"
        		+ "        \"acclass\": \"CTAEJE\"\n"
        		+ "      }\n"
        		+ "    ]\n"
        		+ "  }\n"
        		+ "}")
                .build();
    }*/
}
