package org.acme.movimientos.controllers.banksController;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.acme.movimientos.dtos.catalogBank.DataBank;
import org.acme.movimientos.dtos.catalogBank.GetBankListingRes;
import org.acme.movimientos.dtos.catalogBank.GetRequestDataUser;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;


import java.util.ArrayList;
import java.util.List;

@Path("/bankList")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class ApiBankController {

    @POST
    @Path("")
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
}
