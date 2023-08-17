package org.acme.movimientos.controllers.movimientosController;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;

import org.acme.movimientos.controllers.banksController.ApiBankController;
import org.acme.movimientos.dtos.AddThirdAccountsCLABEReq;
import org.acme.movimientos.dtos.AddThirdAccountsCreditCardReq;
import org.acme.movimientos.dtos.AddThirdAccountsDebitCardReq;
import org.acme.movimientos.dtos.AddThirdAccountsInvexReq;
import org.acme.movimientos.dtos.AddThirdAccountsMobileReq;
import org.acme.movimientos.dtos.CEPVoucherInquiryReqDto;
import org.acme.movimientos.dtos.InfoDto;
import org.acme.movimientos.dtos.MovimientosDto;
import org.acme.movimientos.dtos.RemoveThirdAccountsReq;
import org.acme.movimientos.dtos.catalogBank.DataBank;
import org.acme.movimientos.dtos.catalogBank.GetBankListingRes;
import org.acme.movimientos.dtos.catalogBank.GetRequestDataUser;
import org.acme.movimientos.dtos.catalogBank.RequestGetBankClabe;
import org.acme.movimientos.dtos.orderspei.CreateSPEIOrderReqDto;
import org.acme.movimientos.dtos.traspasoEntreCuentasInvex.TraspasosInvReq;
import org.acme.movimientos.services.CuentaService;
import org.acme.movimientos.utilities.TokenUtility;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import io.smallrye.jwt.build.Jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


import java.util.logging.Logger;


@Path("/efectivo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class MovimientosController {

    private static final Logger LOG = Logger.getLogger(MovimientosController.class.getName());
    
    @Inject
    CuentaService cuentaService;


    @GET
    @Path("/generatetoken")
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
    
    @GET
    @Path("/movimientos/{cuenta}/{fecha}/{cep}/{operationType}")
    public Response getMovimientosV2(@PathParam("cuenta") @NotNull String cuenta, @PathParam("fecha") @NotNull String fecha,@PathParam("cep") @NotNull String cep,@PathParam("operationType") @NotNull String operationType, @HeaderParam("Authorization") String authorizationHeader) {
        String response=null;
        try {
            // Validar que los parámetros no sean nulos o vacíos
            if (cuenta == null || cuenta.isEmpty()  || cuenta.isBlank() || fecha == null || fecha.isEmpty() || fecha.isBlank()
            		|| cep == null || cep.isEmpty() || cep.isBlank()
            		|| operationType == null || operationType.isEmpty() || operationType.isBlank()
            		) {
                LOG.severe("Los parámetros cuenta, fecha, cep o operationType son requeridos");
                return Response.status(Response.Status.BAD_REQUEST).entity("Los parámetros cuenta, fecha, cep o operationType son requeridos").build();
            }

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


            //Consumo del EndPoint del sp

            String numerocuenta = "1510100344327";//cuentaService.obtenerNumeroCuenta(cuenta,cui,folio);//SP ConsultaCuentasPorCUI
            LOG.info("getMovimientosV2 numerocuenta: "+numerocuenta);//quitar en produccion
            if (numerocuenta == null) {
                LOG.severe("No se pudo obtener el numero de cuenta... cuentaService.obtenerNumeroCuenta");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo obtener el numero de cuenta").build();
            }

            //EndPoint que trae los datos
            /*response = cuentaService.obtenerMovimientosDesdeEndpoint(numerocuenta, fecha);

            // Convertir la respuesta JSON a una lista de objetos MovimientosDto
            ObjectMapper objectMapper = new ObjectMapper();

            //Descomentar para dejar de mockear y comentar el mockeo de respuesta
            Responses resp = objectMapper.readValue(response, new TypeReference<Responses>() {});
            List<AccountTransactions> AccountTransactionsList = resp.getCanonicalInvexMessage().getBody().getAccountTransactionsRes().getAccountTransactions();
            List<MovimientosDto> resultList =  new ArrayList<>();
            MovimientosList resFinal = new MovimientosList();
            for (AccountTransactions accountTransactions:AccountTransactionsList ){
                MovimientosDto result = new MovimientosDto();
                result.setMovimiento(accountTransactions.getOperationAmount());
                result.setConcepto(accountTransactions.getConcept());
                result.setFecha(accountTransactions.getOperationDate());
                result.setSaldo(accountTransactions.getBalance());
                resultList.add(result);
            }
            resFinal.setMovimientos(resultList);
            return Response.ok(resFinal).build();
            */
            
            //Mockeo respuesta
            List<MovimientosDto> movimientosList = new ArrayList<>();
            if(cuenta.equals("1")) {
            	
            	return Response.ok("{\n"
            			+ "  \"transactionID\": \"57a318d4-1b23-41fa-baac-c01630ba2849\",\n"
            			+ "  \"httpStatus\": 200,\n"
            			+ "  \"timestamp\": \"2023-07-28T15:26:47.885759\",\n"
            			+ "  \"message\": \"Microservice working fine\",\n"
                		+ "  \"token\": \"" + authorizationHeader + "\"," 
            			+ "  \"data\": {\n"
            			+ "    \"movimientos\": [\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"PAGO 4 JUAN JOSE AGUILAR\",\n"
            			+ "        \"fecha\": \"2023-06-01T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"2396.94\",\n"
            			+ "        \"saldo\": \"177370.85\",\n"
            			+ "        \"referencia\": \"7528300\",\n"
            			+ "        \"fechaAceptacion\": \"2023-06-01T00:31:03-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-06-01T00:31:04-05:00\",\n"
            			+ "        \"emisor\": \"SANTANDER\",\n"
            			+ "        \"cuentaOrdenante\": \"014180605605294205\",\n"
            			+ "        \"nombreOrdenante\": \"JUANJOSEAGUILARRIVERA\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023060140014TRAP0000431254700\",\n"
            			+ "        \"tipoOperacion\": \"Deposito\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"pago prestamo \",\n"
            			+ "        \"fecha\": \"2023-05-31T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"1300\",\n"
            			+ "        \"saldo\": \"163404.31\",\n"
            			+ "        \"referencia\": \"4092351\",\n"
            			+ "        \"fechaAceptacion\": \"2023-05-31T15:46:45-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-05-31T15:46:45-05:00\",\n"
            			+ "        \"emisor\": \"STP\",\n"
            			+ "        \"cuentaOrdenante\": \"646013206858315561\",\n"
            			+ "        \"nombreOrdenante\": \"JORGEALEJANDROGOMORACARMONA\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"CPO58796137714\",\n"
            			+ "        \"tipoOperacion\": \"Deposito\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Compra de equipo de oficina\",\n"
            			+ "        \"fecha\": \"2023-07-20T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"5000\",\n"
            			+ "        \"saldo\": \"172404.31\",\n"
            			+ "        \"referencia\": \"2023072001\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-20T10:15:30-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-20T10:15:30-05:00\",\n"
            			+ "        \"emisor\": \"BANCO INVEX, S.A.\",\n"
            			+ "        \"cuentaOrdenante\": \"348612987530912456\",\n"
            			+ "        \"nombreOrdenante\": \"EMPRESA ABC\",\n"
            			+ "        \"receptor\": \"JUAN JOSE AGUILAR\",\n"
            			+ "        \"cuentaBeneficiario\": \"014180605605294205\",\n"
            			+ "        \"nombreBeneficiario\": \"JUAN JOSE AGUILAR\",\n"
            			+ "        \"claveRastreo\": \"2023072001INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Pago de nómina\",\n"
            			+ "        \"fecha\": \"2023-07-15T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"7500.50\",\n"
            			+ "        \"saldo\": \"164903.81\",\n"
            			+ "        \"referencia\": \"2023071502\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-15T08:30:00-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-15T08:30:00-05:00\",\n"
            			+ "        \"emisor\": \"EMPRESA XYZ\",\n"
            			+ "        \"cuentaOrdenante\": \"998877665544332211\",\n"
            			+ "        \"nombreOrdenante\": \"EMPLEADO 1\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023071502INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Depósito de ventas\",\n"
            			+ "        \"fecha\": \"2023-07-28T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"8500.75\",\n"
            			+ "        \"saldo\": \"173404.56\",\n"
            			+ "        \"referencia\": \"2023072801\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-28T14:10:20-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-28T14:10:21-05:00\",\n"
            			+ "        \"emisor\": \"CLIENTE ABC\",\n"
            			+ "        \"cuentaOrdenante\": \"112233445566778899\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE ABC\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023072801INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Deposito\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Pago de servicios\",\n"
            			+ "        \"fecha\": \"2023-07-05T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"1200.25\",\n"
            			+ "        \"saldo\": \"166204.31\",\n"
            			+ "        \"referencia\": \"2023070501\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-05T12:45:30-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-05T12:45:30-05:00\",\n"
            			+ "        \"emisor\": \"SERVICIOS ABC\",\n"
            			+ "        \"cuentaOrdenante\": \"777788889999000011\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE XYZ\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023070501INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Pago de impuestos\",\n"
            			+ "        \"fecha\": \"2023-07-10T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"3500\",\n"
            			+ "        \"saldo\": \"162704.31\",\n"
            			+ "        \"referencia\": \"2023071001\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-10T09:20:00-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-10T09:20:00-05:00\",\n"
            			+ "        \"emisor\": \"GOBIERNO\",\n"
            			+ "        \"cuentaOrdenante\": \"333322221111444455\",\n"
            			+ "        \"nombreOrdenante\": \"EMPRESA XYZ\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023071001INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Retiro de efectivo\",\n"
            			+ "        \"fecha\": \"2023-07-12T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"-2000\",\n"
            			+ "        \"saldo\": \"160704.31\",\n"
            			+ "        \"referencia\": \"2023071201\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-12T13:55:10-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-12T13:55:11-05:00\",\n"
            			+ "        \"emisor\": \"BANCO INVEX, S.A.\",\n"
            			+ "        \"cuentaOrdenante\": \"448877112233556677\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE XYZ\",\n"
            			+ "        \"receptor\": \"CLIENTE XYZ\",\n"
            			+ "        \"cuentaBeneficiario\": \"014180605605294205\",\n"
            			+ "        \"nombreBeneficiario\": \"JUAN JOSE AGUILAR\",\n"
            			+ "        \"claveRastreo\": \"2023071201INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Retiro\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Transferencia de fondos\",\n"
            			+ "        \"fecha\": \"2023-07-22T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"-5000.50\",\n"
            			+ "        \"saldo\": \"155703.81\",\n"
            			+ "        \"referencia\": \"2023072201\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-22T11:30:00-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-22T11:30:00-05:00\",\n"
            			+ "        \"emisor\": \"BANCO INVEX, S.A.\",\n"
            			+ "        \"cuentaOrdenante\": \"448877112233556677\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE XYZ\",\n"
            			+ "        \"receptor\": \"EMPRESA ABC\",\n"
            			+ "        \"cuentaBeneficiario\": \"112233445566778899\",\n"
            			+ "        \"nombreBeneficiario\": \"EMPRESA ABC\",\n"
            			+ "        \"claveRastreo\": \"2023072201INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Pago de factura\",\n"
            			+ "        \"fecha\": \"2023-07-18T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"350.75\",\n"
            			+ "        \"saldo\": \"155353.06\",\n"
            			+ "        \"referencia\": \"2023071801\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-18T09:15:20-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-18T09:15:20-05:00\",\n"
            			+ "        \"emisor\": \"EMPRESA XYZ\",\n"
            			+ "        \"cuentaOrdenante\": \"998877665544332211\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE 2\",\n"
            			+ "        \"receptor\": \"PROVEEDOR ABC\",\n"
            			+ "        \"cuentaBeneficiario\": \"556677889900112233\",\n"
            			+ "        \"nombreBeneficiario\": \"PROVEEDOR ABC\",\n"
            			+ "        \"claveRastreo\": \"2023071801INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Deposito\"\n"
            			+ "      }\n"
            			+ "    ]\n"
            			+ "  }\n"
            			+ "}")
                        .build();
            }else if(cuenta.equals("2")){
            	return Response.ok("{\n"
            			+ "  \"transactionID\": \"57a318d4-1b23-41fa-baac-c01630ba2849\",\n"
            			+ "  \"httpStatus\": 200,\n"
            			+ "  \"timestamp\": \"2023-07-28T15:26:47.885759\",\n"
            			+ "  \"message\": \"Microservice working fine\",\n"
                		+ "  \"token\": \"" + authorizationHeader + "\"," 
            			+ "  \"data\": {\n"
            			+ "    \"movimientos\": [\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"PAGO 4 JUAN JOSE AGUILAR\",\n"
            			+ "        \"fecha\": \"2023-06-01T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"2396.94\",\n"
            			+ "        \"saldo\": \"177370.85\",\n"
            			+ "        \"referencia\": \"7528300\",\n"
            			+ "        \"fechaAceptacion\": \"2023-06-01T00:31:03-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-06-01T00:31:04-05:00\",\n"
            			+ "        \"emisor\": \"SANTANDER\",\n"
            			+ "        \"cuentaOrdenante\": \"014180605605294205\",\n"
            			+ "        \"nombreOrdenante\": \"JUANJOSEAGUILARRIVERA\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023060140014TRAP0000431254700\",\n"
            			+ "        \"tipoOperacion\": \"Deposito\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"pago prestamo \",\n"
            			+ "        \"fecha\": \"2023-05-31T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"1300\",\n"
            			+ "        \"saldo\": \"163404.31\",\n"
            			+ "        \"referencia\": \"4092351\",\n"
            			+ "        \"fechaAceptacion\": \"2023-05-31T15:46:45-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-05-31T15:46:45-05:00\",\n"
            			+ "        \"emisor\": \"STP\",\n"
            			+ "        \"cuentaOrdenante\": \"646013206858315561\",\n"
            			+ "        \"nombreOrdenante\": \"JORGEALEJANDROGOMORACARMONA\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"CPO58796137714\",\n"
            			+ "        \"tipoOperacion\": \"Deposito\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Compra de equipo de oficina\",\n"
            			+ "        \"fecha\": \"2023-07-20T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"5000\",\n"
            			+ "        \"saldo\": \"172404.31\",\n"
            			+ "        \"referencia\": \"2023072001\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-20T10:15:30-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-20T10:15:30-05:00\",\n"
            			+ "        \"emisor\": \"BANCO INVEX, S.A.\",\n"
            			+ "        \"cuentaOrdenante\": \"348612987530912456\",\n"
            			+ "        \"nombreOrdenante\": \"EMPRESA ABC\",\n"
            			+ "        \"receptor\": \"JUAN JOSE AGUILAR\",\n"
            			+ "        \"cuentaBeneficiario\": \"014180605605294205\",\n"
            			+ "        \"nombreBeneficiario\": \"JUAN JOSE AGUILAR\",\n"
            			+ "        \"claveRastreo\": \"2023072001INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Pago de nómina\",\n"
            			+ "        \"fecha\": \"2023-07-15T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"7500.50\",\n"
            			+ "        \"saldo\": \"164903.81\",\n"
            			+ "        \"referencia\": \"2023071502\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-15T08:30:00-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-15T08:30:00-05:00\",\n"
            			+ "        \"emisor\": \"EMPRESA XYZ\",\n"
            			+ "        \"cuentaOrdenante\": \"998877665544332211\",\n"
            			+ "        \"nombreOrdenante\": \"EMPLEADO 1\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023071502INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Depósito de ventas\",\n"
            			+ "        \"fecha\": \"2023-07-28T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"8500.75\",\n"
            			+ "        \"saldo\": \"173404.56\",\n"
            			+ "        \"referencia\": \"2023072801\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-28T14:10:20-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-28T14:10:21-05:00\",\n"
            			+ "        \"emisor\": \"CLIENTE ABC\",\n"
            			+ "        \"cuentaOrdenante\": \"112233445566778899\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE ABC\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023072801INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Deposito\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Pago de servicios\",\n"
            			+ "        \"fecha\": \"2023-07-05T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"1200.25\",\n"
            			+ "        \"saldo\": \"166204.31\",\n"
            			+ "        \"referencia\": \"2023070501\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-05T12:45:30-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-05T12:45:30-05:00\",\n"
            			+ "        \"emisor\": \"SERVICIOS ABC\",\n"
            			+ "        \"cuentaOrdenante\": \"777788889999000011\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE XYZ\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023070501INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Pago de impuestos\",\n"
            			+ "        \"fecha\": \"2023-07-10T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"3500\",\n"
            			+ "        \"saldo\": \"162704.31\",\n"
            			+ "        \"referencia\": \"2023071001\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-10T09:20:00-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-10T09:20:00-05:00\",\n"
            			+ "        \"emisor\": \"GOBIERNO\",\n"
            			+ "        \"cuentaOrdenante\": \"333322221111444455\",\n"
            			+ "        \"nombreOrdenante\": \"EMPRESA XYZ\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023071001INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Retiro de efectivo\",\n"
            			+ "        \"fecha\": \"2023-07-12T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"-2000\",\n"
            			+ "        \"saldo\": \"160704.31\",\n"
            			+ "        \"referencia\": \"2023071201\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-12T13:55:10-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-12T13:55:11-05:00\",\n"
            			+ "        \"emisor\": \"BANCO INVEX, S.A.\",\n"
            			+ "        \"cuentaOrdenante\": \"448877112233556677\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE XYZ\",\n"
            			+ "        \"receptor\": \"CLIENTE XYZ\",\n"
            			+ "        \"cuentaBeneficiario\": \"014180605605294205\",\n"
            			+ "        \"nombreBeneficiario\": \"JUAN JOSE AGUILAR\",\n"
            			+ "        \"claveRastreo\": \"2023071201INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Retiro\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Transferencia de fondos\",\n"
            			+ "        \"fecha\": \"2023-07-22T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"-5000.50\",\n"
            			+ "        \"saldo\": \"155703.81\",\n"
            			+ "        \"referencia\": \"2023072201\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-22T11:30:00-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-22T11:30:00-05:00\",\n"
            			+ "        \"emisor\": \"BANCO INVEX, S.A.\",\n"
            			+ "        \"cuentaOrdenante\": \"448877112233556677\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE XYZ\",\n"
            			+ "        \"receptor\": \"EMPRESA ABC\",\n"
            			+ "        \"cuentaBeneficiario\": \"112233445566778899\",\n"
            			+ "        \"nombreBeneficiario\": \"EMPRESA ABC\",\n"
            			+ "        \"claveRastreo\": \"2023072201INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Pago de factura\",\n"
            			+ "        \"fecha\": \"2023-07-18T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"350.75\",\n"
            			+ "        \"saldo\": \"155353.06\",\n"
            			+ "        \"referencia\": \"2023071801\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-18T09:15:20-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-18T09:15:20-05:00\",\n"
            			+ "        \"emisor\": \"EMPRESA XYZ\",\n"
            			+ "        \"cuentaOrdenante\": \"998877665544332211\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE 2\",\n"
            			+ "        \"receptor\": \"PROVEEDOR ABC\",\n"
            			+ "        \"cuentaBeneficiario\": \"556677889900112233\",\n"
            			+ "        \"nombreBeneficiario\": \"PROVEEDOR ABC\",\n"
            			+ "        \"claveRastreo\": \"2023071801INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Deposito\"\n"
            			+ "      }\n"
            			+ "    ]\n"
            			+ "  }\n"
            			+ "}")
                        .build();
                 
            }else if(cuenta.equals("3")){
            	return Response.ok("{\n"
            			+ "  \"transactionID\": \"32a4499a-f0a4-4e00-9435-fbce7f933003\",\n"
            			+ "  \"httpStatus\": 500,\n"
            			+ "  \"timestamp\": \"2023-07-28T15:46:50.802209200\",\n"
            			+ "  \"message\": \"Se requiere el encabezado de autorización\",\n"
                		+ "  \"token\": \"" + authorizationHeader + "\"," 
            			+ "  \"data\": null\n"
            			+ "}").build(); 	
            }else {
            	return Response.ok("{\n"
            			+ "  \"transactionID\": \"57a318d4-1b23-41fa-baac-c01630ba2849\",\n"
            			+ "  \"httpStatus\": 200,\n"
            			+ "  \"timestamp\": \"2023-07-28T15:26:47.885759\",\n"
            			+ "  \"message\": \"Microservice working fine\",\n"
                		+ "  \"token\": \"" + authorizationHeader + "\","  
            			+ "  \"data\": {\n"
            			+ "    \"movimientos\": [\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"PAGO 4 JUAN JOSE AGUILAR\",\n"
            			+ "        \"fecha\": \"2023-06-01T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"2396.94\",\n"
            			+ "        \"saldo\": \"177370.85\",\n"
            			+ "        \"referencia\": \"7528300\",\n"
            			+ "        \"fechaAceptacion\": \"2023-06-01T00:31:03-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-06-01T00:31:04-05:00\",\n"
            			+ "        \"emisor\": \"SANTANDER\",\n"
            			+ "        \"cuentaOrdenante\": \"014180605605294205\",\n"
            			+ "        \"nombreOrdenante\": \"JUANJOSEAGUILARRIVERA\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023060140014TRAP0000431254700\",\n"
            			+ "        \"tipoOperacion\": \"Deposito\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"pago prestamo \",\n"
            			+ "        \"fecha\": \"2023-05-31T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"1300\",\n"
            			+ "        \"saldo\": \"163404.31\",\n"
            			+ "        \"referencia\": \"4092351\",\n"
            			+ "        \"fechaAceptacion\": \"2023-05-31T15:46:45-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-05-31T15:46:45-05:00\",\n"
            			+ "        \"emisor\": \"STP\",\n"
            			+ "        \"cuentaOrdenante\": \"646013206858315561\",\n"
            			+ "        \"nombreOrdenante\": \"JORGEALEJANDROGOMORACARMONA\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"CPO58796137714\",\n"
            			+ "        \"tipoOperacion\": \"Deposito\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Compra de equipo de oficina\",\n"
            			+ "        \"fecha\": \"2023-07-20T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"5000\",\n"
            			+ "        \"saldo\": \"172404.31\",\n"
            			+ "        \"referencia\": \"2023072001\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-20T10:15:30-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-20T10:15:30-05:00\",\n"
            			+ "        \"emisor\": \"BANCO INVEX, S.A.\",\n"
            			+ "        \"cuentaOrdenante\": \"348612987530912456\",\n"
            			+ "        \"nombreOrdenante\": \"EMPRESA ABC\",\n"
            			+ "        \"receptor\": \"JUAN JOSE AGUILAR\",\n"
            			+ "        \"cuentaBeneficiario\": \"014180605605294205\",\n"
            			+ "        \"nombreBeneficiario\": \"JUAN JOSE AGUILAR\",\n"
            			+ "        \"claveRastreo\": \"2023072001INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Pago de nómina\",\n"
            			+ "        \"fecha\": \"2023-07-15T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"7500.50\",\n"
            			+ "        \"saldo\": \"164903.81\",\n"
            			+ "        \"referencia\": \"2023071502\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-15T08:30:00-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-15T08:30:00-05:00\",\n"
            			+ "        \"emisor\": \"EMPRESA XYZ\",\n"
            			+ "        \"cuentaOrdenante\": \"998877665544332211\",\n"
            			+ "        \"nombreOrdenante\": \"EMPLEADO 1\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023071502INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Depósito de ventas\",\n"
            			+ "        \"fecha\": \"2023-07-28T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"8500.75\",\n"
            			+ "        \"saldo\": \"173404.56\",\n"
            			+ "        \"referencia\": \"2023072801\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-28T14:10:20-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-28T14:10:21-05:00\",\n"
            			+ "        \"emisor\": \"CLIENTE ABC\",\n"
            			+ "        \"cuentaOrdenante\": \"112233445566778899\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE ABC\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023072801INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Deposito\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Pago de servicios\",\n"
            			+ "        \"fecha\": \"2023-07-05T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"1200.25\",\n"
            			+ "        \"saldo\": \"166204.31\",\n"
            			+ "        \"referencia\": \"2023070501\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-05T12:45:30-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-05T12:45:30-05:00\",\n"
            			+ "        \"emisor\": \"SERVICIOS ABC\",\n"
            			+ "        \"cuentaOrdenante\": \"777788889999000011\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE XYZ\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023070501INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Pago de impuestos\",\n"
            			+ "        \"fecha\": \"2023-07-10T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"3500\",\n"
            			+ "        \"saldo\": \"162704.31\",\n"
            			+ "        \"referencia\": \"2023071001\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-10T09:20:00-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-10T09:20:00-05:00\",\n"
            			+ "        \"emisor\": \"GOBIERNO\",\n"
            			+ "        \"cuentaOrdenante\": \"333322221111444455\",\n"
            			+ "        \"nombreOrdenante\": \"EMPRESA XYZ\",\n"
            			+ "        \"receptor\": \"BANCO INVEX, S.A., Institucion de Banca Multiple, Invex Grupo Financiero\",\n"
            			+ "        \"cuentaBeneficiario\": \"00101003699\",\n"
            			+ "        \"nombreBeneficiario\": \"PYMES CAPITAL, INC\",\n"
            			+ "        \"claveRastreo\": \"2023071001INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Retiro de efectivo\",\n"
            			+ "        \"fecha\": \"2023-07-12T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"-2000\",\n"
            			+ "        \"saldo\": \"160704.31\",\n"
            			+ "        \"referencia\": \"2023071201\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-12T13:55:10-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-12T13:55:11-05:00\",\n"
            			+ "        \"emisor\": \"BANCO INVEX, S.A.\",\n"
            			+ "        \"cuentaOrdenante\": \"448877112233556677\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE XYZ\",\n"
            			+ "        \"receptor\": \"CLIENTE XYZ\",\n"
            			+ "        \"cuentaBeneficiario\": \"014180605605294205\",\n"
            			+ "        \"nombreBeneficiario\": \"JUAN JOSE AGUILAR\",\n"
            			+ "        \"claveRastreo\": \"2023071201INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Retiro\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Transferencia de fondos\",\n"
            			+ "        \"fecha\": \"2023-07-22T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"-5000.50\",\n"
            			+ "        \"saldo\": \"155703.81\",\n"
            			+ "        \"referencia\": \"2023072201\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-22T11:30:00-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-22T11:30:00-05:00\",\n"
            			+ "        \"emisor\": \"BANCO INVEX, S.A.\",\n"
            			+ "        \"cuentaOrdenante\": \"448877112233556677\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE XYZ\",\n"
            			+ "        \"receptor\": \"EMPRESA ABC\",\n"
            			+ "        \"cuentaBeneficiario\": \"112233445566778899\",\n"
            			+ "        \"nombreBeneficiario\": \"EMPRESA ABC\",\n"
            			+ "        \"claveRastreo\": \"2023072201INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Transferencia\"\n"
            			+ "      },\n"
            			+ "      {\n"
            			+ "        \"concepto\": \"Pago de factura\",\n"
            			+ "        \"fecha\": \"2023-07-18T00:00:00-05:00\",\n"
            			+ "        \"movimiento\": \"350.75\",\n"
            			+ "        \"saldo\": \"155353.06\",\n"
            			+ "        \"referencia\": \"2023071801\",\n"
            			+ "        \"fechaAceptacion\": \"2023-07-18T09:15:20-05:00\",\n"
            			+ "        \"fechaLiquidacion\": \"2023-07-18T09:15:20-05:00\",\n"
            			+ "        \"emisor\": \"EMPRESA XYZ\",\n"
            			+ "        \"cuentaOrdenante\": \"998877665544332211\",\n"
            			+ "        \"nombreOrdenante\": \"CLIENTE 2\",\n"
            			+ "        \"receptor\": \"PROVEEDOR ABC\",\n"
            			+ "        \"cuentaBeneficiario\": \"556677889900112233\",\n"
            			+ "        \"nombreBeneficiario\": \"PROVEEDOR ABC\",\n"
            			+ "        \"claveRastreo\": \"2023071801INVEX\",\n"
            			+ "        \"tipoOperacion\": \"Deposito\"\n"
            			+ "      }\n"
            			+ "    ]\n"
            			+ "  }\n"
            			+ "}")
                        .build();
            }
           

        } catch (Exception e) {
            LOG.severe("Error al consultar la informacion del cliente: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al consultar la información del cliente: "+e.getMessage()).build();
        }
    }

    
    @POST
    @Path("/bankList")
    public Response getBankList(@RequestBody GetRequestDataUser getRequestDataUser){
        if(StringUtils.isBlank(getRequestDataUser.getNetwork())){
            log.error("el campo esta vacio");
            throw new RuntimeException("el campo esta vacio");
        }
        DataBank b1 = new DataBank("BANXICO", "2001");
        DataBank b2 = new DataBank("BANCOMEXT", "37006");
        DataBank b3 = new DataBank("BANOBRAS", "37009");
        DataBank b4 = new DataBank("BANJERCITO", "37019");
        DataBank b5 = new DataBank("NAFIN", "37135");
        DataBank b6 = new DataBank("BABIEN", "37166");
        DataBank b7 = new DataBank("HIPOTECARIA FED", "37168");
        DataBank b8 = new DataBank("BANAMEX", "40002");
        DataBank b9 = new DataBank("BBVA MEXICO", "40012");
        DataBank b10 = new DataBank("SANTANDER", "40014");

        List<DataBank> databank = new ArrayList<>();
        databank.add(b1);
        databank.add(b2);
        databank.add(b3);
        databank.add(b4);
        databank.add(b5);
        databank.add(b6);
        databank.add(b7);
        databank.add(b8);
        databank.add(b9);
        databank.add(b10);
        GetBankListingRes getBankListingRes = new GetBankListingRes();
        getBankListingRes.setDataBank(databank);
        return Response.ok(getBankListingRes).build();
    }
    
    @POST
    @Path("/getBankClabe")
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
    
    @POST
    @Path("/addThirdAccountsInvex")
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
    
    @POST
    @Path("/addThirdAccountsCLABE")
    public Response addThirdAccountsCLABE(@RequestBody AddThirdAccountsCLABEReq addThirdAccountsCLABEReq , @HeaderParam("Authorization") String authorizationHeader) {
        String response=null;
        try {
            // Validar que los parámetros no sean nulos o vacíos
            if (
            		(addThirdAccountsCLABEReq.getIdAccount() == null || addThirdAccountsCLABEReq.getIdAccount().isEmpty() || addThirdAccountsCLABEReq.getIdAccount().isBlank()) 
            		|| (addThirdAccountsCLABEReq.getClabe() == null || addThirdAccountsCLABEReq.getClabe().isEmpty() || addThirdAccountsCLABEReq.getClabe().isBlank())
            		|| (addThirdAccountsCLABEReq.getCurpRfc() == null || addThirdAccountsCLABEReq.getCurpRfc().isEmpty() || addThirdAccountsCLABEReq.getCurpRfc().isBlank())
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
            		+ "    \"addThirdAccountsCLABERes\": {\n"
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
    
    
    @POST
    @Path("/addThirdAccountsCreditCard")
    public Response addThirdAccountsCreditCard(@RequestBody AddThirdAccountsCreditCardReq addThirdAccountsCreditCardReq , @HeaderParam("Authorization") String authorizationHeader) {
        String response=null;
        try {
            // Validar que los parámetros no sean nulos o vacíos
            if (
            		(addThirdAccountsCreditCardReq.getIdAccount() == null || addThirdAccountsCreditCardReq.getIdAccount().isEmpty() || addThirdAccountsCreditCardReq.getIdAccount().isBlank()) 
            		|| (addThirdAccountsCreditCardReq.getCreditCard() == null || addThirdAccountsCreditCardReq.getCreditCard().isEmpty() || addThirdAccountsCreditCardReq.getCreditCard().isBlank())
            		|| (addThirdAccountsCreditCardReq.getCurpRfc() == null || addThirdAccountsCreditCardReq.getCurpRfc().isEmpty() || addThirdAccountsCreditCardReq.getCurpRfc().isBlank())
            		) {
                LOG.severe("Los parametros idAccount, accountNumber o curpRfc, son requeridos");
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
            		+ "    \"addThirdAccountsCreditCardRes\": {\n"
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
    
    
    
    @POST
    @Path("/addThirdAccountsDebitCard")
    public Response addThirdAccountsCreditCard(@RequestBody AddThirdAccountsDebitCardReq addThirdAccountsDebitCardReq , @HeaderParam("Authorization") String authorizationHeader) {
        String response=null;
        try {
            // Validar que los parámetros no sean nulos o vacíos
            if (
            		(addThirdAccountsDebitCardReq.getIdAccount() == null || addThirdAccountsDebitCardReq.getIdAccount().isEmpty() || addThirdAccountsDebitCardReq.getIdAccount().isBlank()) 
            		|| (addThirdAccountsDebitCardReq.getDebitCard() == null || addThirdAccountsDebitCardReq.getDebitCard().isEmpty() || addThirdAccountsDebitCardReq.getDebitCard().isBlank())
            		|| (addThirdAccountsDebitCardReq.getCurpRfc() == null || addThirdAccountsDebitCardReq.getCurpRfc().isEmpty() || addThirdAccountsDebitCardReq.getCurpRfc().isBlank())
            		) {
                LOG.severe("Los parametros idAccount, debitCard o curpRfc, son requeridos");
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
            		+ "    \"addThirdAccountsDebitCardRes\": {\n"
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
    
    
    @POST
    @Path("/addThirdAccountsMobile")
    public Response addThirdAccountsMobile(@RequestBody AddThirdAccountsMobileReq addThirdAccountsMobileReq , @HeaderParam("Authorization") String authorizationHeader) {
        String response=null;
        try {
            // Validar que los parámetros no sean nulos o vacíos
            if (
            		(addThirdAccountsMobileReq.getIdAccount() == null || addThirdAccountsMobileReq.getIdAccount().isEmpty() || addThirdAccountsMobileReq.getIdAccount().isBlank()) 
            		|| (addThirdAccountsMobileReq.getMobileNumber() == null || addThirdAccountsMobileReq.getMobileNumber().isEmpty() || addThirdAccountsMobileReq.getMobileNumber().isBlank())
            		|| (addThirdAccountsMobileReq.getCurpRfc() == null || addThirdAccountsMobileReq.getCurpRfc().isEmpty() || addThirdAccountsMobileReq.getCurpRfc().isBlank())
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
            		+ "    \"addThirdAccountsMobileRes\": {\n"
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
    
    @GET
    @Path("/beneficiarios/{idCuenta}")
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
    
    
    @DELETE
    @Path("/removeThirdAccounts")
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
    }
    
    
    
    @GET
    @Path("/cuentas")
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
    }
    
    
    @POST
    @Path("/cepvoucherinquiry")
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
            String cui = "123456";//TokenUtility.verifyAndGetCui(authorizationHeader);
            LOG.info("CUI: "+cui);
            // Verificar y obtener el "cui" del JWT
            String folio = "1234";//TokenUtility.verifyAndGetFolio(authorizationHeader);
            LOG.info("Folio: "+folio);
      
            return Response.ok("{\n"
            		+ "    \"transactionID\": \"2a857448-8d41-49b1-a12d-f450570371c1\",\n"
            		+ "    \"httpStatus\": 200,\n"
            		+ "    \"timestamp\": \"2023-08-04T17:37:06.486428700\",\n"
            		+ "    \"message\": \"Microservice working fine\",\n"
            		+ "  \"token\": \"" + authorizationHeader + "\"," 
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
    
    
    @POST
    @Path("/transferIntoInvex")
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
