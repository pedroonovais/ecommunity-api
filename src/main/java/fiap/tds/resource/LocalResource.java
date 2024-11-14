package fiap.tds.resource;

import fiap.tds.model.dao.LocalDAO;
import fiap.tds.model.vo.Local;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Path("local")
public class LocalResource {

    private LocalDAO dao = new LocalDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLocais() {
        List<Local> locais = dao.selectAll();

        if (locais.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("msg", "Nenhum local encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        } else {
            List<Map<String, String>> responseList = new ArrayList<>();

            for (Local local : locais) {
                Map<String, String> response = new HashMap<>();
                response.put("status", "success");
                response.put("id", String.valueOf(local.getId()));
                response.put("nome", local.getNome());
                response.put("categoria", local.getCategoria());
                response.put("logradouro", local.getLogradouro());
                response.put("cep", local.getCep());
                response.put("cidade", local.getCidade());
                response.put("estado", local.getEstado());
                response.put("latitude", String.valueOf(local.getLatitude()));
                response.put("longitude", String.valueOf(local.getLongitude()));
                responseList.add(response);
            }

            return Response.ok(responseList).build();
        }
    }

    @Path("/inserir")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertLocal(Local local){
        int idLocal = dao.insert(local);

        Map<String, String> response = new HashMap<>();
        if (idLocal > 0){
            response.put("status", "success");
            response.put("id", String.valueOf(idLocal));
            return Response.ok(response).build();
        } else {
            response.put("status", "error");
            response.put("msg", "Não foi possível inserir o registro");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocal(@PathParam("id") int id){
        Local local = dao.select(id);

        Map<String, String> response = new HashMap<>();
        if (local != null){
            response.put("status", "success");
            response.put("id", String.valueOf(local.getId()));
            response.put("nome", local.getNome());
            response.put("categoria", local.getCategoria());
            response.put("logradouro", local.getLogradouro());
            response.put("cep", local.getCep());
            response.put("cidade", local.getCidade());
            response.put("estado", local.getEstado());
            response.put("latitude", String.valueOf(local.getLatitude()));
            response.put("longitude", String.valueOf(local.getLongitude()));
            return Response.ok(response).build();
        } else{
            response.put("status", "error");
            response.put("msg", "Local não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }

    @Path("/deletar/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLocal(@PathParam("id") int id) {
        boolean sucesso = dao.deletar(id);

        Map<String, String> response = new HashMap<>();
        if (sucesso) {
            response.put("status", "success");
            return Response.ok(response).build();
        } else {
            response.put("status", "error");
            response.put("msg", "Local não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }

    @Path("/atualizar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarLocal(Local local) {
        boolean sucesso = dao.update(local);

        Map<String, String> response = new HashMap<>();
        if (sucesso) {
            response.put("status", "success");
            return Response.ok(response).build();
        } else {
            response.put("status", "error");
            response.put("msg", "Local não encontrado para atualização");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }
}
