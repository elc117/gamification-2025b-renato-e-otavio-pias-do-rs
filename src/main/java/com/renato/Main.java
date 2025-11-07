// commit com foco na implementação do backend Javalin e criação das rotas básicas da API

package com.renato;

import io.javalin.Javalin;
import com.renato.model.*;
import java.util.*;

public class Main {   
    private static final List<Usuario> usuarios = new ArrayList<>(); // ainda não utilizando banco de dados
    private static final List<Noticia> noticias = new ArrayList<>();
    private static final List<Resposta> respostas = new ArrayList<>();
    
    private static Long nextUsuarioId = 1L;
    private static Long nextNoticiaId = 1L;
    private static Long nextRespostaId = 1L;
    
    public static void main(String[] args) {
        inicializarDados(); // só para exemplo
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "3000"));
        var app = Javalin.create(); // cria a aplicação Javalin
        var dadosService = new DadosService(usuarios, noticias, respostas); // cria a instância do service
        
        app.get("/usuarios", ctx -> ctx.json(usuarios));
        
        app.get("/usuarios/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Usuario usuario = dadosService.encontrarUsuario(id);
            if (usuario != null) {
                ctx.json(usuario);
            }
            else {
                ctx.status(404).result("Usuário não encontrado");
            }
        });
        
        app.post("/usuarios", ctx -> {
            Usuario novoUsuario = ctx.bodyAsClass(Usuario.class); // usei pra converter JSON em objeto Java
            novoUsuario.setId(nextUsuarioId++);
            novoUsuario.setNivel(1);
            usuarios.add(novoUsuario);
            ctx.status(201).json(novoUsuario);
        });
        
        app.get("/usuarios/{id}/perfil", ctx -> { //  busca perfil do usuário
            Long id = Long.parseLong(ctx.pathParam("id"));
            Usuario usuario = dadosService.encontrarUsuario(id);
            
            if (usuario == null) {
                ctx.status(404).result("Usuário não encontrado");
                return;
            }
            
            int totalRespostas = dadosService.obtemTotalRespostasUsuario(id);
            
            Map<String, Object> perfil = new HashMap<>(); // para montar a resposta, eu usei HashMap
            perfil.put("usuario", usuario);
            perfil.put("totalRespostas", totalRespostas);
            
            ctx.json(perfil);
        });
        
        app.get("/noticias", ctx -> ctx.json(noticias));
        
        app.get("/noticias/random", ctx -> { // notícia aleatória
            Random random = new Random();
            Noticia noticia = noticias.get(random.nextInt(noticias.size()));
            ctx.json(noticia);
        });
        
        app.get("/noticias/random/{usuarioId}", ctx -> {
            Long usuarioId = Long.parseLong(ctx.pathParam("usuarioId"));
            List<Noticia> noticiasDisponiveis = dadosService.obtemNoticiasNaoRespondidas(usuarioId);
            
            if (noticiasDisponiveis.size() == 0) {
                ctx.status(404).result("Todas as notícias já foram respondidas");
                return;
            }
            
            Random random = new Random();
            Noticia noticia = noticiasDisponiveis.get(random.nextInt(noticiasDisponiveis.size()));
            ctx.json(noticia);
        });
        
        app.post("/noticias", ctx -> { // cria nova notícia
            Noticia novaNoticia = ctx.bodyAsClass(Noticia.class);
            novaNoticia.setId(nextNoticiaId++);
            noticias.add(novaNoticia);
            ctx.status(201).json(novaNoticia);
        });
        
        app.post("/noticias/{id}/responder", ctx -> { // responde notícia
            Long noticiaId = Long.parseLong(ctx.pathParam("id"));
            Map<String, Object> body = ctx.bodyAsClass(Map.class);
            Long usuarioId = ((Number) body.get("usuarioId")).longValue();
            boolean respostaUsuario = (boolean) body.get("resposta");
            
            Noticia noticia = dadosService.encontrarNoticia(noticiaId);
            if (noticia == null) {
                ctx.status(404).result("Notícia não encontrada");
                return;
            }

            boolean jaRespondeu = dadosService.jaRespondeuNoticia(usuarioId, noticiaId);
            if (jaRespondeu) {
                ctx.status(400).result("O usuário já respondeu esta notícia");
                return;
            }
            
            boolean acertou = (respostaUsuario == noticia.isEhVerdadeira());
            int pontosGanhos = acertou ? 10 : -5;
            
            Resposta resposta = new Resposta(nextRespostaId++, usuarioId, noticiaId, 
                                             respostaUsuario, acertou, pontosGanhos);
            respostas.add(resposta); // registra a resposta
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("acertou", acertou);
            resultado.put("pontosGanhos", pontosGanhos);
            resultado.put("explicacao", noticia.getExplicacao());
            ctx.status(201).json(resultado);
        });
        
        app.get("/usuarios/{id}/respostas", ctx -> { // respostas do usuário
            Long id = Long.parseLong(ctx.pathParam("id"));
            List<Resposta> respostasUsuario = dadosService.obtemRespostasUsuario(id);
            ctx.json(respostasUsuario);
        });
        
        app.get("/", ctx -> { // rota só para fins de teste
            ctx.result("backend está funcionando");
        });
        
        app.start(port);
        System.out.println("servidor rodando na porta " + port);
    }
    
    private static void inicializarDados() {
        // abaixo, eu fiz algumas notícias de exemplo
        noticias.add(new Noticia(1L, 1L, "Vacina COVID-19", 
            "As vacinas contra COVID-19 foram aprovadas após rigorosos testes clínicos com milhares de participantes.", 
            true, "As vacinas passaram por todas as fases de testes e foram aprovadas por agências reguladoras.", 1));
        
        noticias.add(new Noticia(2L, 1L, "Chá milagroso", 
            "Chá de alho com limão cura COVID-19 em 24 horas.", 
            false, "Não existe comprovação científica de cura para COVID-19 por chás caseiros.", 1));
        
        noticias.add(new Noticia(3L, 3L, "Aquecimento Global", 
            "O planeta Terra está aquecendo devido às atividades humanas, segundo 97% dos cientistas climáticos.", 
            true, "Existe consenso científico sobre o aquecimento global antropogênico.", 2));
        
        noticias.add(new Noticia(4L, 2L, "Política", 
            "Político X prometeu acabar com todos os impostos se eleito.", 
            false, "Essa declaração nunca foi feita pelo político. Verifique fontes oficiais.", 1));
    }
}