// a classe Main está com bastante responsabilidades. Isso também será alterado nos próximos commits,
// para separar estas responsabilidades.

package com.renato;

import io.javalin.Javalin;
import com.renato.model.*;
import com.renato.repository.*;
import com.renato.service.*;
import java.util.*;

public class Main {
    // Repositórios para acesso ao banco de dados
    private static final NoticiaRepository noticiaRepository = new NoticiaRepository();
    private static final RespostaRepository respostaRepository = new RespostaRepository();
    private static final ProgressoCategoriaRepository progressoRepository = new ProgressoCategoriaRepository();
    private static final ConquistaRepository conquistaRepository = new ConquistaRepository();
    private static final ConquistaUsuarioRepository conquistaUsuarioRepository = new ConquistaUsuarioRepository();

    // Serviços
    private static final JogoService jogoService = new JogoService();
    private static final PontuacaoService pontuacaoService = new PontuacaoService();

    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "3000"));
        var app = Javalin.create(); // cria a aplicação Javalin
        var dadosService = new DadosService(); // Novo construtor sem parâmetros - usa repositórios

        ////////// rotas para usuários //////////
        app.get("/usuarios", ctx -> ctx.json(dadosService.listarUsuarios())); // lista todos os usuários

        app.get("/usuarios/{id}", ctx -> { // lista usuário por ID
            Long id = Long.parseLong(ctx.pathParam("id"));
            Usuario usuario = dadosService.encontrarUsuario(id);
            if (usuario != null) {
                ctx.json(usuario);
            }
            else {
                ctx.status(404).result("Usuário não encontrado");
            }
        });
        
        app.post("/usuarios", ctx -> { // cria novo usuário
            Usuario novoUsuario = ctx.bodyAsClass(Usuario.class); // converte JSON em objeto Java
            novoUsuario.setNivel(1);
            Usuario usuarioSalvo = dadosService.salvarUsuario(novoUsuario);
            ctx.status(201).json(usuarioSalvo);
        });

        app.put("/usuarios/{id}", ctx -> { // atualiza usuário
            Long id = Long.parseLong(ctx.pathParam("id"));
            Usuario usuarioExistente = dadosService.encontrarUsuario(id);

            if (usuarioExistente == null) {
                ctx.status(404).result("Usuário não encontrado");
                return;
            }

            Usuario usuarioAtualizado = ctx.bodyAsClass(Usuario.class);
            usuarioAtualizado.setId(id);
            dadosService.atualizarUsuario(usuarioAtualizado);
            ctx.json(usuarioAtualizado);
        });

        app.delete("/usuarios/{id}", ctx -> { // deleta usuário
            Long id = Long.parseLong(ctx.pathParam("id"));
            Usuario usuario = dadosService.encontrarUsuario(id);

            if (usuario == null) {
                ctx.status(404).result("Usuário não encontrado");
                return;
            }

            dadosService.deletarUsuario(usuario);
            ctx.status(204);
        });

        app.get("/usuarios/{id}/perfil", ctx -> { //  busca perfil do usuário
            try {
                Long id = Long.parseLong(ctx.pathParam("id"));
                Usuario usuario = dadosService.encontrarUsuario(id);

                if (usuario == null) {
                    ctx.status(404).result("Usuário não encontrado");
                    return;
                }

                List<ProgressoCategoria> progressosUsuario = dadosService.obtemProgressosUsuario(id);
                List<ConquistaUsuario> conquistasUsuarioLista = dadosService.obtemConquistasUsuario(id);
                int totalRespostas = dadosService.obtemTotalRespostasUsuario(id);
                int totalAcertos = dadosService.obtemTotalAcertosUsuario(id);
                double taxaAcerto = totalRespostas > 0 ? (totalAcertos * 100.0 / totalRespostas) : 0;

                Map<String, Object> perfil = new HashMap<>(); // para montar a resposta, eu usei HashMap
                perfil.put("usuario", usuario);
                perfil.put("progressos", progressosUsuario != null ? progressosUsuario : new ArrayList<>());
                perfil.put("conquistas", conquistasUsuarioLista != null ? conquistasUsuarioLista : new ArrayList<>());
                perfil.put("totalRespostas", totalRespostas);
                perfil.put("totalAcertos", totalAcertos);
                perfil.put("taxaAcerto", taxaAcerto);

                ctx.json(perfil);
            } catch (Exception e) {
                System.err.println("Erro ao buscar perfil: " + e.getMessage());
                e.printStackTrace();
                ctx.status(500).result("Erro interno: " + e.getMessage());
            }
        });
        /////////////////////////////////////////
        
        ///////// rotas para categorias ////////
        app.get("/categorias", ctx -> ctx.json(dadosService.listarCategorias())); // lista todas as categorias

        app.get("/categorias/{id}", ctx -> { // categoria por ID
            Long id = Long.parseLong(ctx.pathParam("id"));
            Categoria categoria = dadosService.encontrarCategoria(id);
            if (categoria != null) {
                ctx.json(categoria);
            }
            else {
                ctx.status(404).result("Categoria não encontrada");
            }
        });
        
        app.get("/categorias/{id}/progresso/{usuarioId}", ctx -> { // progresso do usuário na categoria
            Long categoriaId = Long.parseLong(ctx.pathParam("id"));
            Long usuarioId = Long.parseLong(ctx.pathParam("usuarioId"));            
            ProgressoCategoria progresso = dadosService.obtemProgressoCategoria(usuarioId, categoriaId);

            if (progresso != null) {
                ctx.json(progresso);
            }
            else {
                ctx.status(404).result("Progresso não encontrado");
            }
        });

        app.post("/categorias", ctx -> { // cria nova categoria
            Categoria novaCategoria = ctx.bodyAsClass(Categoria.class);
            Categoria categoriaSalva = dadosService.salvarCategoria(novaCategoria);
            ctx.status(201).json(categoriaSalva);
        });

        app.put("/categorias/{id}", ctx -> { // atualiza categoria
            Long id = Long.parseLong(ctx.pathParam("id"));
            Categoria categoria = ctx.bodyAsClass(Categoria.class);
            categoria.setId(id);
            dadosService.atualizarCategoria(categoria);
            ctx.json(categoria);
        });

        app.delete("/categorias/{id}", ctx -> { // deleta categoria
            Long id = Long.parseLong(ctx.pathParam("id"));
            dadosService.deletarCategoria(id);
            ctx.status(204);
        });
        /////////////////////////////////////////

        ////////// rotas para notícias //////////
        app.get("/noticias", ctx -> ctx.json(noticiaRepository.findAll())); // lista todas as notícias

        app.get("/noticias/random/{usuarioId}/categoria/{categoriaId}", ctx -> { // notícia aleatória de categoria específica não respondida
            Long usuarioId = Long.parseLong(ctx.pathParam("usuarioId"));
            Long categoriaId = Long.parseLong(ctx.pathParam("categoriaId"));

            Noticia noticia = jogoService.obterNoticiaAleatoriaDaCategoria(usuarioId, categoriaId);

            if (noticia == null) {
                ctx.status(404).result("Todas as notícias desta categoria já foram respondidas");
                return;
            }

            ctx.json(noticia);
        });
        
        app.get("/noticias/categoria/{categoriaId}", ctx -> { // lista todas as notícias de uma categoria
            Long categoriaId = Long.parseLong(ctx.pathParam("categoriaId"));
            List<Noticia> noticiasDaCategoria = dadosService.obtemNoticiasDaCategoria(categoriaId);
            ctx.json(noticiasDaCategoria);
        });

        app.post("/noticias", ctx -> { // cria nova notícia
            Noticia novaNoticia = ctx.bodyAsClass(Noticia.class);
            Noticia noticiaSalva = noticiaRepository.save(novaNoticia);
            ctx.status(201).json(noticiaSalva);
        });

        app.get("/noticias/{id}", ctx -> { // busca notícia por ID
            Long id = Long.parseLong(ctx.pathParam("id"));
            Noticia noticia = dadosService.encontrarNoticia(id);

            if (noticia != null) {
                ctx.json(noticia);
            } else {
                ctx.status(404).result("Notícia não encontrada");
            }
        });

        app.put("/noticias/{id}", ctx -> { // atualiza notícia
            Long id = Long.parseLong(ctx.pathParam("id"));
            Noticia noticiaExistente = dadosService.encontrarNoticia(id);

            if (noticiaExistente == null) {
                ctx.status(404).result("Notícia não encontrada");
                return;
            }

            Noticia noticiaAtualizada = ctx.bodyAsClass(Noticia.class);
            noticiaAtualizada.setId(id);
            noticiaRepository.update(noticiaAtualizada);
            ctx.json(noticiaAtualizada);
        });

        app.delete("/noticias/{id}", ctx -> { // deleta notícia
            Long id = Long.parseLong(ctx.pathParam("id"));
            Noticia noticia = dadosService.encontrarNoticia(id);

            if (noticia == null) {
                ctx.status(404).result("Notícia não encontrada");
                return;
            }

            noticiaRepository.delete(noticia);
            ctx.status(204);
        });
        /////////////////////////////////////////
        
        ///////// rotas para respostas //////////
        app.post("/noticias/{id}/responder", ctx -> { // responde notícia
            try {
                Long noticiaId = Long.parseLong(ctx.pathParam("id"));
                Map<String, Object> body = ctx.bodyAsClass(Map.class);
                Long usuarioId = ((Number) body.get("usuarioId")).longValue();
                boolean respostaUsuario = (boolean) body.get("resposta");

                // Usa o JogoService para processar a resposta
                Map<String, Object> resultado = jogoService.processarResposta(usuarioId, noticiaId, respostaUsuario);

                ctx.status(201).json(resultado);
            } catch (IllegalArgumentException e) {
                ctx.status(404).result(e.getMessage());
            } catch (IllegalStateException e) {
                ctx.status(400).result(e.getMessage());
            } catch (Exception e) {
                ctx.status(500).result("Erro ao processar resposta: " + e.getMessage());
            }
        });
        
        app.get("/usuarios/{id}/respostas", ctx -> { // respostas do usuário
            Long id = Long.parseLong(ctx.pathParam("id"));
            List<Resposta> respostasUsuario = dadosService.obtemRespostasUsuario(id);
            ctx.json(respostasUsuario);
        });
        /////////////////////////////////////////
        
        ///////// rotas para conquistas ////////
        app.get("/conquistas", ctx -> ctx.json(conquistaRepository.findAll())); // lista todas as conquistas

        app.get("/usuarios/{id}/conquistas", ctx -> { // lista conquistas de um usuário
            Long id = Long.parseLong(ctx.pathParam("id"));
            List<ConquistaUsuario> conquistasDoUsuario = dadosService.obtemConquistasUsuario(id);
            ctx.json(conquistasDoUsuario);
        });
        /////////////////////////////////////////

        ///////// rota para teste ////////
        app.get("/", ctx -> { // rota só para fins de teste
            ctx.result("backend está funcionando");
        });
        ////////////////////////////////////////

        app.start(port);
        System.out.println("servidor rodando na porta " + port);
    }
}