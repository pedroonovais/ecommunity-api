package fiap.tds.resource;

import fiap.tds.model.dao.UsuarioDAO;
import fiap.tds.model.vo.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("usuario")
public class UsuarioResource {

    private UsuarioDAO dao = new UsuarioDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsuarios() {
        List<Usuario> usuarios = dao.selectAll();
        if (usuarios.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("msg", "Nenhum usuário encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        } else {
            List<Map<String, String>> responseList = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                Map<String, String> response = new HashMap<>();
                response.put("id", String.valueOf(usuario.getId()));
                response.put("nome", usuario.getNome());
                response.put("email", usuario.getEmail());
                response.put("qtdPontos", String.valueOf(usuario.getPontos()));
                response.put("admin", usuario.isAdmin());
                responseList.add(response);
            }
            return Response.ok(responseList).build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario(@PathParam("id") int id) {
        Usuario usuario = dao.select(id);
        if (usuario != null) {
            Map<String, String> response = new HashMap<>();
            response.put("id", String.valueOf(usuario.getId()));
            response.put("nome", usuario.getNome());
            response.put("email", usuario.getEmail());
            response.put("qtdPontos", String.valueOf(usuario.getPontos()));
            response.put("admin", usuario.isAdmin());
            return Response.ok(response).build();
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("msg", "Usuário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }

    @Path("/inserir")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertUsuario(Usuario usuario) {
        int idUsuario = dao.insert(usuario);
        Map<String, String> response = new HashMap<>();
        if (idUsuario > 0) {
            response.put("status", "success");
            response.put("id", String.valueOf(idUsuario));
            return Response.ok(response).build();
        } else {
            response.put("status", "error");
            response.put("msg", "Não foi possível inserir o usuário");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

    @Path("/atualizar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUsuario(Usuario usuario) {
        boolean sucesso = dao.update(usuario);
        Map<String, String> response = new HashMap<>();

        if (sucesso) {
            response.put("status", "success");
            return Response.ok(response).build();
        } else {
            response.put("status", "error");
            response.put("msg", "Usuário não encontrado para atualização");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }


    @Path("/deletar/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUsuario(@PathParam("id") int id) {
        boolean sucesso = dao.delete(id);
        Map<String, String> response = new HashMap<>();
        if (sucesso) {
            response.put("status", "success");
            return Response.ok(response).build();
        } else {
            response.put("status", "error");
            response.put("msg", "Usuário não encontrado");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }
}
