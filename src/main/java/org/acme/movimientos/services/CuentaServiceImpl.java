package org.acme.movimientos.services;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

//import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.acme.movimientos.utilities.Constants;
import org.eclipse.microprofile.jwt.Claims;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;

import java.util.HashSet;
import java.util.logging.Logger;
@ApplicationScoped
public class CuentaServiceImpl implements CuentaService{

    private static final Logger LOG = Logger.getLogger(CuentaServiceImpl.class.getName());
    //@Inject
    //EntityManager em;


    //Metodo que consume el SP ConsultaCuentasPorCUI
    @Transactional
    public String obtenerNumeroCuenta(String cuenta, String cui,String folio) {
        try {
            // Validar que los parámetros no sean nulos o vacíos
            if (cuenta == null || cuenta.isEmpty() || cui == null || cui.isEmpty()) {
                LOG.severe("Los parametros cuenta y cui no pueden ser nulos o vacios");
                throw new IllegalArgumentException("Los parametros cuenta y cui no pueden ser nulos o vacios");
            }


            //Se comento porque se realizo un API Rest para consumir a parte el SP
            LOG.info("obtenerNumeroCuenta cui: " + cui);//Quitar en produccion
            /*StoredProcedureQuery query = em.createStoredProcedureQuery("ConsultaCuentasPorCUI0");
            query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

            query.setParameter(1, cui);
            query.execute();
            String resultado = (String) query.getOutputParameterValue(2);
            */
            String resultado = (String) obtenerConsultacuentasporcuiEndpoint(cui,folio);
            LOG.info("obtenerNumeroCuenta Resultado EndPoint SP: " + resultado);//Quitar en produccion
            // Validar que se obtuvo una respuesta válida del SP
            if (resultado == null || resultado.isEmpty()) {
                LOG.severe("No se obtuvo una respuesta valida del SP ConsultaCuentasPorCUI");
                throw new IllegalStateException("No se obtuvo una respuesta valida del SP ConsultaCuentasPorCUI");
            }

            //Metodo para parsear el resultado y obtener el numerocuenta
            String numerocuenta = parsearNumeroCuenta(resultado, cuenta);
            LOG.info("obtenerNumeroCuenta numerocuenta: " + numerocuenta);//Quitar en produccion
            return numerocuenta;
        } catch (IllegalArgumentException e) {
            LOG.severe("Error: " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            LOG.severe("Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            LOG.severe("Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //Metodo para obtener el numero de cuenta del resultado del SP
    public String parsearNumeroCuenta(String respuesta, String cuenta) {
        ObjectMapper mapper = new ObjectMapper();
        String numerocuenta = null;
        LOG.info("parsearNumeroCuenta respuesta: "+respuesta);
        LOG.info("parsearNumeroCuenta cuenta: "+cuenta);
        try {
            JsonNode root = mapper.readTree(respuesta);
            JsonNode respuestaConsultaNode = root.path("result").path("respuestaConsulta");
            String respuestaConsulta = respuestaConsultaNode.asText();

            JsonNode datosNode = mapper.readTree(respuestaConsulta).path("datos");
            JsonNode accountNode = mapper.readTree(datosNode.asText()).path("account");

            LOG.info("parsearNumeroCuenta TRY");
            for (JsonNode cuentaNode : accountNode) {
                int idAccount = cuentaNode.path("idAccount").asInt();
                LOG.info("parsearNumeroCuenta idAccount: " + idAccount);
                String customerAccount = cuentaNode.path("customerAccount").asText();
                LOG.info("parsearNumeroCuenta customerAccount: " + customerAccount);
                if (idAccount == Integer.parseInt(cuenta)) {
                    numerocuenta = customerAccount;
                    break;
                }
            }
            LOG.info("parsearNumeroCuenta TRY");
        } catch (JsonProcessingException e) {
            LOG.severe("Error*************************************: " + e.getMessage());
            e.printStackTrace();
        }

        return numerocuenta;
    }


    //Consumo de enpoint externo flex::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public String obtenerMovimientosDesdeEndpoint(String numerocuenta, String fecha) throws IOException {
        String response = null;

        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();

        // Convertir la fecha actual a java.sql.Date
        java.sql.Date fromDateSql = new java.sql.Date(currentDate.getTime());
        // Convertir la fecha en una representación de cadena
        String fromDateStr = fromDateSql.toString();
        LOG.info("fromDateStr: " + fromDateStr);
        //Variable tipo string request:
        String numerOfTransaccion = "123123"; // no se sabe qué tendrá pero el el endopoint del flex se usa

        // URL del endpoint externo
        String url = Constants.API_FLEX_MOVIMIENTOS_URL;

        // Crear la conexión HTTP
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String requestBody = "{ " +
                "\"canonicalInvexMessage\": {" +
                "\"header\": {" +
                "\"requestSystem\": \"PORTAL\"" +
                "}," +
                "\"body\": {" +
                "\"accountTransactionsReq\": {" +
                "\"customerAccount\": \"" + numerocuenta + "\"," +
                "\"fromDate\": \"" + fecha + "\"," +
                "\"toDate\": \"" + fromDateStr + "\"," +
                "\"numberOfTransactions\": \"" + numerOfTransaccion + "\"" +
                "}" +
                "}" +
                "}" +
                "}";


        // Enviar el cuerpo de la solicitud
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(requestBody.getBytes());
        outputStream.flush();

        // Leer la respuesta del endpoint externo
        int varReposCode = connection.getResponseCode();

        if (varReposCode == HttpURLConnection.HTTP_OK) {
            LOG.info("**************************************EndPoint externo FLEX Tiene status OK");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = reader.readLine();

            // Cerrar las conexiones
            reader.close();
            connection.disconnect();
        } else {
            LOG.severe("Error Endpoint Flex status : " +varReposCode+" ,Response: "+ response);
            throw new IOException("Status: " + varReposCode);
        }

        return response;
    }


    public String obtenerConsultacuentasporcuiEndpoint(String cui, String folio) throws IOException {
        String response = null;


        // URL del endpoint externo
        String url = Constants.API_WSO2_SP_CONSULTACUENTASPORCUI_URL;

        // Crear la conexión HTTP
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);


        String requestBody = "{ " +
                "\"Request\": {" +
                "\"cui\": \"" + cui + "\"," +
                "\"folio\": \"" + folio + "\"" +
                "}" +
                "}";


        // Enviar el cuerpo de la solicitud
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(requestBody.getBytes());
        outputStream.flush();

        // Leer la respuesta del endpoint externo
        int varReposCode = connection.getResponseCode();

        if (varReposCode == HttpURLConnection.HTTP_OK) {
            LOG.info("**************************************EndPoint Externo SP Tiene status OK");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = reader.readLine();

            // Cerrar las conexiones
            reader.close();
            connection.disconnect();
        } else {
            LOG.severe("Error Endpoint Flex status : " +varReposCode+" ,Response: "+ response);
            throw new IOException("Status: " + varReposCode);
        }

        return response;
    }

    public String obtenerConsultacuentas() throws IOException {
        String response = null;



        try{
            Instant expirationTime = Instant.now().plus(3, ChronoUnit.HOURS);

            String token =
                    Jwt.issuer("https://example.com/issuer")
                            .upn("jdoe@quarkus.io")
                            .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                            .claim(Claims.birthdate.name(), "2001-07-13")
                            .claim("cui", "123456")
                            .claim("folio", "0")
                            .sign();
            URL url = new URL("https://app-movimientos-mock-047d864954b1.herokuapp.com/efectivo/cuentas");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);


            conn.connect();




            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Error al consumir el endpoint: " + responseCode);
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                response = reader.readLine();

                // Cerrar las conexiones
                reader.close();
                conn.disconnect();
            }
            return response;
        }catch(IOException e){
            throw new IOException("Error service: " + e.getMessage());
        }


    }

}
