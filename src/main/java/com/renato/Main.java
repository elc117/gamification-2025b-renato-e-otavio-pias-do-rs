// commit com novas rotas para categorias, progressos e conquistas. Por ora, utiliza arrays para a base,
// mas a estrutura será alterada nos próximos commits.

// a classe Main está com bastante responsabilidades. Isso também será alterado nos próximos commits,
// para separar estas responsabilidades.

package com.renato;

import io.javalin.Javalin;
import com.renato.model.*;
import java.util.*;

public class Main {   
    private static final List<Usuario> usuarios = new ArrayList<>(); // ainda não utilizando banco de dados
    private static final List<Categoria> categorias = new ArrayList<>();
    private static final List<Noticia> noticias = new ArrayList<>();
    private static final List<Resposta> respostas = new ArrayList<>();
    private static final List<ProgressoCategoria> progressos = new ArrayList<>();
    private static final List<Conquista> conquistas = new ArrayList<>();
    private static final List<ConquistaUsurario> conquistasUsuario = new ArrayList<>();
    
    private static Long nextUsuarioId = 1L;
    private static Long nextNoticiaId = 1L;
    private static Long nextRespostaId = 1L;
    private static Long nextProgressoId = 1L;
    private static Long nextConquistaUsuarioId = 1L;
    
    public static void main(String[] args) {
        inicializarDados(); // só para exemplo
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "3000"));
        var app = Javalin.create(); // cria a aplicação Javalin
        var dadosService = new DadosService(usuarios, categorias, noticias, respostas, progressos, conquistasUsuario); // cria a instância do service com métodos de lógica
        
        ////////// rotas para usuários //////////
        app.get("/usuarios", ctx -> ctx.json(usuarios)); // lista todos os usuários
        
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

            List<ProgressoCategoria> progressosUsuario = dadosService.obtemProgressosUsuario(id);
            List<ConquistaUsurario> conquistasUsuarioLista = dadosService.obtemConquistasUsuario(id);
            int totalRespostas = dadosService.obtemTotalRespostasUsuario(id);
            int totalAcertos = dadosService.obtemTotalAcertosUsuario(id);
            double taxaAcerto = totalRespostas > 0 ? (totalAcertos * 100.0 / totalRespostas) : 0;
            
            Map<String, Object> perfil = new HashMap<>(); // para montar a resposta, eu usei HashMap
            perfil.put("usuario", usuario);
            perfil.put("progressos", progressosUsuario);
            perfil.put("conquistas", conquistasUsuarioLista);
            perfil.put("totalRespostas", totalRespostas);
            perfil.put("totalAcertos", totalAcertos);
            perfil.put("taxaAcerto", taxaAcerto);
            
            ctx.json(perfil);
        });
        /////////////////////////////////////////
        
        ///////// rotas para categorias ////////
        app.get("/categorias", ctx -> ctx.json(categorias)); // lista todas as categorias

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
            ProgressoCategoria progresso = dadosService.obtemProgressoCategoria(categoriaId, usuarioId);
            
            if (progresso != null) {
                ctx.json(progresso);
            }
            else {
                ctx.status(404).result("Progresso não encontrado");
            }
        });
        /////////////////////////////////////////

        ////////// rotas para notícias //////////
        app.get("/noticias", ctx -> ctx.json(noticias)); // lista todas as notícias
        
        app.get("/noticias/random", ctx -> { // notícia aleatória
            Random random = new Random();
            Noticia noticia = noticias.get(random.nextInt(noticias.size()));
            ctx.json(noticia);
        });
        
        app.get("/noticias/random/{usuarioId}", ctx -> { // notícia por ID
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
        
        app.get("/noticias/categoria/{categoriaId}", ctx -> { // notícias por categoria
            Long categoriaId = Long.parseLong(ctx.pathParam("categoriaId"));
            List<Noticia> noticiasDaCategoria = dadosService.obtemNoticiasDaCategoria(categoriaId);
            ctx.json(noticiasDaCategoria);
        });

        app.post("/noticias", ctx -> { // cria nova notícia
            Noticia novaNoticia = ctx.bodyAsClass(Noticia.class);
            novaNoticia.setId(nextNoticiaId++);
            noticias.add(novaNoticia);
            ctx.status(201).json(novaNoticia);
        });
        /////////////////////////////////////////
        
        ///////// rotas para respostas //////////
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

            ProgressoCategoria progresso = dadosService.obtemProgressoCategoria(noticia.getCategoriaId(), usuarioId);
            if (progresso == null) {
                progresso = new ProgressoCategoria(nextProgressoId++, usuarioId, 
                                                   noticia.getCategoriaId(), 0, 0, new ArrayList<>()); // cria novo progresso
                progressos.add(progresso);
            }
            
            // abaixo, é atualizado o progresso na categoria
            int nivelAnterior = progresso.getNivelAtual();
            int novosPontos = progresso.getPontosMaestria() + pontosGanhos;
            if (novosPontos < 0) novosPontos = 0;
            progresso.setPontosMaestria(novosPontos);
            
            int novoNivel = dadosService.calcularNivel(novosPontos); // atualiza o nível, com base nos pontos
            progresso.setNivelAtual(novoNivel);
            
            boolean subiuNivel = novoNivel > nivelAnterior; // se subiu de nível, desbloqueia uma peça
            if (subiuNivel && !progresso.getPecasDesbloqueadas().contains(novoNivel)) {
                progresso.getPecasDesbloqueadas().add(novoNivel);
            }
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("acertou", acertou);
            resultado.put("pontosGanhos", pontosGanhos);
            resultado.put("explicacao", noticia.getExplicacao());
            resultado.put("subiuNivel", subiuNivel);
            resultado.put("nivelAtual", novoNivel);
            resultado.put("pontosCategoria", novosPontos);
            resultado.put("pecasDesbloqueadas", progresso.getPecasDesbloqueadas());
            ctx.status(201).json(resultado);
        });
        
        app.get("/usuarios/{id}/respostas", ctx -> { // respostas do usuário
            Long id = Long.parseLong(ctx.pathParam("id"));
            List<Resposta> respostasUsuario = dadosService.obtemRespostasUsuario(id);
            ctx.json(respostasUsuario);
        });
        /////////////////////////////////////////
        
        ///////// rotas para conquistas ////////
        app.get("/conquistas", ctx -> ctx.json(conquistas)); // lista todas as conquistas
        
        app.get("/usuarios/{id}/conquistas", ctx -> { // lista conquistas de um usuário
            Long id = Long.parseLong(ctx.pathParam("id"));
            List<ConquistaUsurario> conquistasDoUsuario = dadosService.obtemConquistasUsuario(id);
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

        conquistas.add(new Conquista(1L, "Primeiro Passo", "Responda sua primeira notícia", 
            "🎯", "total_respostas", "bronze", 1));
        conquistas.add(new Conquista(2L, "Sequência Perfeita", "Acerte 5 notícias seguidas", 
            "🔥", "acertos_seguidos", "prata", 5));
        conquistas.add(new Conquista(3L, "Mestre de Categoria", "Complete uma categoria", 
            "👑", "completar_categoria", "ouro", 1));
    }
}