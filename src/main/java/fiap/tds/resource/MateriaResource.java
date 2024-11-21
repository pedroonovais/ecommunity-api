package fiap.tds.resource;

import fiap.tds.model.bo.MateriaBO;
import fiap.tds.model.dao.MateriaDAO;
import fiap.tds.model.vo.Materia;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Path("materia")
public class MateriaResource {

    private MateriaDAO dao = new MateriaDAO();
    private MateriaBO bo = new MateriaBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMaterias() {
        List<Materia> materias = dao.selectAll();

        if (materias.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("msg", "Nenhuma matéria encontrada");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        } else {
            List<Map<String, String>> responseList = new ArrayList<>();

            for (Materia materia : materias) {
                Map<String, String> response = new HashMap<>();
                response.put("status", "success");
                response.put("idMateria", String.valueOf(materia.getIdMateria()));
                response.put("idUsuario", String.valueOf(materia.getIdUsuario()));
                response.put("tituloMateria", materia.getTituloMateria());
                response.put("textoMateria", materia.getTextoMateria());
                response.put("ativo", String.valueOf(materia.isAtivo()));
                response.put("dtCriacao", materia.getDtCriacao().toString());
                response.put("dtUpdate", materia.getDtUpdate().toString());
                response.put("imagem", materia.getImagem());
                responseList.add(response);
            }

            return Response.ok(responseList).build();
        }
    }

    @Path("/inserir")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertMateria(Materia materia) {
        Map<String, String> response = new HashMap<>();

        if (!bo.materiaValida(materia)){
            response.put("status", "error");
            response.put("msg", "Dados inválidos para a matéria. Verifique os campos obrigatórios, o status e as datas.");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        int idMateria = dao.insert(materia);

        if (idMateria > 0) {
            response.put("status", "success");
            response.put("id", String.valueOf(idMateria));
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
    public Response getMateria(@PathParam("id") int id) {
        Materia materia = dao.select(id);

        Map<String, String> response = new HashMap<>();
        if (materia != null) {
            response.put("status", "success");
            response.put("idMateria", String.valueOf(materia.getIdMateria()));
            response.put("idUsuario", String.valueOf(materia.getIdUsuario()));
            response.put("tituloMateria", materia.getTituloMateria());
            response.put("textoMateria", materia.getTextoMateria());
            response.put("ativo", String.valueOf(materia.isAtivo()));
            response.put("dtCriacao", materia.getDtCriacao().toString());
            response.put("dtUpdate", materia.getDtUpdate().toString());
            response.put("imagem", materia.getImagem());
            return Response.ok(response).build();
        } else {
            response.put("status", "error");
            response.put("msg", "Matéria não encontrada");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }

    @Path("/deletar/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMateria(@PathParam("id") int id) {
        boolean sucesso = dao.deletar(id);

        Map<String, String> response = new HashMap<>();
        if (sucesso) {
            response.put("status", "success");
            return Response.ok(response).build();
        } else {
            response.put("status", "error");
            response.put("msg", "Matéria não encontrada");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }

    @Path("/atualizar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarMateria(Materia materia) {
        boolean sucesso = dao.update(materia);

        Map<String, String> response = new HashMap<>();
        if (sucesso) {
            response.put("status", "success");
            return Response.ok(response).build();
        } else {
            response.put("status", "error");
            response.put("msg", "Matéria não encontrada para atualização");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }
}
