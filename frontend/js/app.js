// Configuração da API
// Local: localhost:3000
// Render: mesmo domínio (URL vazia)
// itch.io: URL completa do backend no Render
const API_BASE = window.location.hostname === 'localhost' 
    ? 'http://localhost:3000' 
    : window.location.hostname.includes('itch.zone') || window.location.hostname.includes('itch.io')
        ? 'https://gamification-2025b-renato-e-otavio-pias.onrender.com'
        : '';

// Estado da aplicação
let currentUser = null;
let currentCategory = null;
let currentNoticia = null;

// Função para formatar texto de notícias (converte quebras de linha e markdown)
function formatarTexto(texto) {
    if (!texto) return '';

    // Escapar HTML para segurança
    const textoSeguro = texto
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;');

    // Aplicar negrito para textos entre **asteriscos** (ANTES de processar parágrafos)
    let formatado = textoSeguro.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');

    // Aplicar itálico para textos entre *asterisco simples*
    formatado = formatado.replace(/\*([^\*]+?)\*/g, '<em>$1</em>');

    // Converter quebras de linha duplas em parágrafos
    const paragrafos = formatado.split(/\n\s*\n/);
    formatado = paragrafos
        .filter(p => p.trim())
        .map(paragrafo => {
            // Dentro de cada parágrafo, quebras de linhas simples viram <br>
            let comBr = paragrafo.replace(/\n/g, '<br>');

            // Substituir hífens no início de linhas por bullets estilizados
            comBr = comBr.replace(/^-\s+/gm, '<span class="bullet">●</span> ');
            comBr = comBr.replace(/<br>\s*-\s+/g, '<br><span class="bullet">●</span> ');

            return `<p>${comBr}</p>`;
        })
        .join('');

    return formatado;
}

// Função para mostrar notificações "toast"
function showToast(message, type = 'success') {
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.innerHTML = `
        <strong>${type === 'success' ? '✅' : '❌'}</strong>
        <span style="margin-left: 10px;">${message}</span>
    `;

    document.body.appendChild(toast);

    setTimeout(() => {
        toast.style.animation = 'slideInRight 0.3s ease-out reverse';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

// Função auxiliar para fazer requisições
async function apiRequest(url, options = {}) {
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };
    
    // Adicionar token no header se estiver no localStorage (para o itch.io)
    const token = localStorage.getItem('userToken');
    if (token) {
        headers['X-User-Token'] = token;
    }
    
    try {
        const response = await fetch(`${API_BASE}${url}`, {
            ...options,
            credentials: 'include', // Importante para manter a sessão
            headers
        });

        console.log(`[API] ${options.method || 'GET'} ${url} - Status: ${response.status}`);

        // Ler o corpo da resposta uma vez
        const responseText = await response.text();
        console.log(`[API] Response body:`, responseText);

        if (!response.ok) {
            // Se não for OK (200-299), lança erro
            throw new Error(responseText || `Erro HTTP ${response.status}`);
        }

        // Se a resposta estiver vazia, retornar objeto vazio
        if (!responseText || responseText.trim() === '') {
            return {};
        }

        // Tentar fazer parse do JSON
        try {
            return JSON.parse(responseText);
        } catch (parseError) {
            console.warn('[API] Resposta não é JSON válido, retornando como texto');
            return responseText;
        }
    } catch (error) {
        console.error('[API] Erro na requisição:', error);
        throw error;
    }
}

// Navegação entre telas
function showScreen(screenId) {
    document.querySelectorAll('.screen').forEach(screen => {
        screen.classList.remove('active');
    });
    document.getElementById(screenId).classList.add('active');
}

// função para mostrar/ocultar loading
function showLoading(show = true, text = 'Carregando...', subtext = 'Por favor, aguarde') {
    let overlay = document.getElementById('loading-overlay');
    
    if (!overlay) {
        // criar overlay se não existir
        overlay = document.createElement('div');
        overlay.id = 'loading-overlay';
        overlay.className = 'loading-overlay';
        overlay.innerHTML = `
            <div class="loading-content">
                <div class="loading-spinner"></div>
                <div class="loading-text" id="loading-text">${text}</div>
                <div class="loading-subtext" id="loading-subtext">${subtext}</div>
            </div>
        `;
        document.body.appendChild(overlay);
    }
    
    // atualizar os textos
    const textEl = document.getElementById('loading-text');
    const subtextEl = document.getElementById('loading-subtext');
    if (textEl) textEl.textContent = text;
    if (subtextEl) subtextEl.textContent = subtext;
    
    // mostrar ou ocultar
    if (show) {
        overlay.classList.add('active');
    }
    else {
        overlay.classList.remove('active');
    }
}

// ========== TELA DE LOGIN ==========
document.getElementById('login-btn').addEventListener('click', async () => {
    const email = document.getElementById('email-input').value.trim();
    const senha = document.getElementById('senha-input').value.trim();

    // Limpar possíveis "toasts" antigos
    document.querySelectorAll('.toast').forEach(toast => toast.remove());

    if (!email || !senha) {
        showToast('Por favor, preencha email e senha', 'error');
        return;
    }

    console.log('🔵 Iniciando login com email:', email);
    
    // Mostrar loading
    showLoading(true, 'Fazendo login...', 'Aguarde enquanto verificamos suas credenciais');

    try {
        // Fazer requisição diretamente com fetch para ter mais controle
        const response = await fetch(`${API_BASE}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify({ email, senha })
        });

        console.log('🔵 Status da resposta:', response.status);
        console.log('🔵 Headers:', [...response.headers.entries()]);

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `Erro HTTP ${response.status}`);
        }

        const responseText = await response.text();
        console.log('🔵 Resposta bruta:', responseText);

        const data = JSON.parse(responseText);
        console.log('🔵 Resposta parseada:', data);

        // Validar resposta
        if (!data || !data.usuario) {
            console.error('❌ Resposta inválida - falta usuario');
            throw new Error('Resposta do servidor inválida');
        }

        currentUser = data.usuario;
        console.log('✅ CurrentUser definido:', currentUser);

        // Salvar token no localStorage (para o itch.io)
        if (data.token) {
            localStorage.setItem('userToken', data.token);
            console.log('✅ Token salvo');
        }


        // Atualizar elementos da navbar
        const navbarUserNameEl = document.getElementById('navbar-user-name');
        const navbarUserLevelEl = document.getElementById('navbar-user-level');

        console.log('🔵 Procurando elementos do DOM...');
        console.log('  - navbar-user-name:', navbarUserNameEl);
        console.log('  - navbar-user-level:', navbarUserLevelEl);

        if (navbarUserNameEl) {
            navbarUserNameEl.textContent = currentUser.nome;
            console.log('✅ Nome atualizado para:', currentUser.nome);
        } else {
            console.warn('⚠️ Elemento navbar-user-name não encontrado');
        }

        if (navbarUserLevelEl) {
            navbarUserLevelEl.textContent = `Nv.${currentUser.nivel}`;
            console.log('✅ Nível atualizado para: Nv.' + currentUser.nivel);
        } else {
            console.warn('⚠️ Elemento navbar-user-level não encontrado');
        }

        console.log('🔵 Mostrando toast de sucesso...');
        showToast(`Bem-vindo(a), ${currentUser.nome}! 🎮`, 'success');

        console.log('🔵 Mudando para tela menu-screen...');
        showScreen('menu-screen');

        // Iniciar música de fundo
        if (window.startBackgroundMusic) {
            window.startBackgroundMusic();
        }

        // Ocultar loading
        showLoading(false);
        
        console.log('✅ Login completo!');
    } catch (error) {
        // ocultar loading em caso de erro
        showLoading(false);
        console.error('❌ ERRO CAPTURADO:', error);
        console.error('❌ Tipo do erro:', typeof error);
        console.error('❌ error.message:', error.message);
        console.error('❌ error.stack:', error.stack);
        showToast('Erro ao fazer login: ' + error.message, 'error');
    }
});

// ========== TELA DE REGISTRO ==========
document.getElementById('register-link').addEventListener('click', (e) => {
    e.preventDefault();
    showScreen('register-screen');
});

document.getElementById('login-link').addEventListener('click', (e) => {
    e.preventDefault();
    showScreen('login-screen');
});

document.getElementById('register-btn').addEventListener('click', async () => {
    const nome = document.getElementById('nome-input').value.trim();
    const email = document.getElementById('email-reg-input').value.trim();
    const senha = document.getElementById('senha-reg-input').value.trim();

    if (!nome || !email || !senha) {
        showToast('Por favor, preencha todos os campos', 'error');
        return;
    }
    
    // mostrar o loading
    showLoading(true, 'Criando conta...', 'Aguarde enquanto preparamos sua credencial');

    try {
        await apiRequest('/usuarios', {
            method: 'POST',
            body: JSON.stringify({ nome, email, senha })
        });

        // ocultar o loading
        showLoading(false);
        
        showToast('Conta criada com sucesso! 🎉 Faça login agora.', 'success');
        showScreen('login-screen');
        document.getElementById('email-input').value = email;
    }
    catch (error) {
        // ocultar o loading em caso de erro
        showLoading(false);
        showToast('Erro ao criar conta: ' + error.message, 'error');
    }
});

// ========== MOSTRAR/OCULTAR SENHA ==========
document.getElementById('show-password-login').addEventListener('change', function() {
    const senhaInput = document.getElementById('senha-input');
    senhaInput.type = this.checked ? 'text' : 'password';
});

document.getElementById('show-password-register').addEventListener('change', function() {
    const senhaInput = document.getElementById('senha-reg-input');
    senhaInput.type = this.checked ? 'text' : 'password';
});

// ========== MENU PRINCIPAL ==========
document.getElementById('logout-btn').addEventListener('click', async () => {
    try {
        // Tentar fazer logout no backend (não crítico caso falhar)
        await apiRequest('/logout', { method: 'POST' });
    } catch (error) {
        console.warn('Logout no backend falhou (não crítico):', error);
    }

    // Sempre limpar estado local e voltar ao login
    currentUser = null;
    localStorage.removeItem('userToken');
    sessionStorage.clear();

    showToast('Você saiu com sucesso! 👋', 'success');
    showScreen('login-screen');

    // Limpar TODOS os campos de login
    const emailInput = document.getElementById('email-input');
    const senhaInput = document.getElementById('senha-input');
    const showPasswordLogin = document.getElementById('show-password-login');
    if (emailInput) emailInput.value = '';
    if (senhaInput) {
        senhaInput.value = '';
        senhaInput.type = 'password'; // Garantir que volta para as bolinhas
    }
    if (showPasswordLogin) showPasswordLogin.checked = false;

    // Limpar TODOS os campos de registro
    const nomeRegInput = document.getElementById('nome-input');
    const emailRegInput = document.getElementById('email-reg-input');
    const senhaRegInput = document.getElementById('senha-reg-input');
    const showPasswordRegister = document.getElementById('show-password-register');
    if (nomeRegInput) nomeRegInput.value = '';
    if (emailRegInput) emailRegInput.value = '';
    if (senhaRegInput) {
        senhaRegInput.value = '';
        senhaRegInput.type = 'password'; // Garantir que volta para as bolinhas
    }
    if (showPasswordRegister) showPasswordRegister.checked = false;
});

document.getElementById('play-btn').addEventListener('click', async () => {
    await loadCategories();
    showScreen('category-screen');
});

document.getElementById('profile-btn').addEventListener('click', async () => {
    await loadProfile();
    showScreen('profile-screen');
});

document.getElementById('achievements-btn').addEventListener('click', async () => {
    await loadAchievements();
    showScreen('achievements-screen');
});

// ========== CATEGORIAS ==========
async function loadCategories() {
    try {
        const categorias = await apiRequest('/categorias');
        const container = document.getElementById('categories-list');
        container.innerHTML = '';

        categorias.forEach((categoria, index) => {
            const card = document.createElement('div');
            card.className = 'category-card';
            // usar style attribute para permitir seletor [style] animação
            card.setAttribute('style', `animation-delay: ${index * 0.1}s`);

            const imagemCategoria = categoria.caminhoImagemCategoria || 'assets/images/default.png';

            card.innerHTML = `
                <div class="category-image-placeholder" aria-hidden="true">
                    <img src="${imagemCategoria}" alt="${categoria.nome}">
                </div>
                <div class="category-content">
                    <div class="category-name">${categoria.nome}</div>
                    <div class="category-desc">${categoria.descricao || 'Clique para jogar'}</div>
                </div>
            `;

            card.addEventListener('click', () => {
                card.style.transform = 'scale(0.98)';
                setTimeout(() => startGame(categoria), 120);
            });

            container.appendChild(card);
        });
    } catch (error) {
        alert('Erro ao carregar categorias: ' + error.message);
    }
}

document.getElementById('back-to-menu').addEventListener('click', () => {
    showScreen('menu-screen');
});

// ========== JOGO ==========
async function startGame(categoria) {
    currentCategory = categoria;
    
    // Primeiro tenta carregar a notícia antes de mudar de tela
    try {
        currentNoticia = await apiRequest(`/noticias/random/categoria/${categoria.id}`);
        
        // Se conseguiu carregar, então muda para a tela do jogo
        document.getElementById('category-name').textContent = categoria.nome;
        showScreen('game-screen');
        
        // Atualiza a interface com a notícia carregada
        document.getElementById('noticia-titulo').textContent = currentNoticia.titulo;
        document.getElementById('noticia-conteudo').innerHTML = formatarTexto(currentNoticia.conteudo);
        document.getElementById('noticia-card').style.display = 'block';
        document.getElementById('feedback-card').classList.add('hidden');
        
        // Habilitar botões
        document.getElementById('fake-btn').disabled = false;
        document.getElementById('fact-btn').disabled = false;
        
        await updateProgressoDisplay();
    } catch (error) {
        // Se não conseguiu carregar, mostra erro e permanece na tela de categorias
        showToast(error.message, 'error');
    }
}

async function loadNextNoticia() {
    try {
        currentNoticia = await apiRequest(`/noticias/random/categoria/${currentCategory.id}`);

        document.getElementById('noticia-titulo').textContent = currentNoticia.titulo;
        document.getElementById('noticia-conteudo').innerHTML = formatarTexto(currentNoticia.conteudo);

        document.getElementById('noticia-card').style.display = 'block';
        document.getElementById('feedback-card').classList.add('hidden');

        // Habilitar botões
        document.getElementById('fake-btn').disabled = false;
        document.getElementById('fact-btn').disabled = false;
    } catch (error) {
        showToast(error.message, 'error');
        showScreen('category-screen');
    }
}

async function updateProgressoDisplay() {
    try {
        const response = await apiRequest(`/categorias/${currentCategory.id}/meu-progresso`);
        // Composição OO: response.estatisticas.percentualProgresso
        const percentualProgresso = response.estatisticas?.percentualProgresso || 0;
        document.getElementById('progresso-percent').textContent = `${percentualProgresso.toFixed(1)}%`;
    } catch (error) {
        console.error('Erro ao carregar progresso:', error);
    }
}

document.getElementById('fake-btn').addEventListener('click', () => responderNoticia(false));
document.getElementById('fact-btn').addEventListener('click', () => responderNoticia(true));

async function responderNoticia(resposta) {
    // Desabilitar botões
    document.getElementById('fake-btn').disabled = true;
    document.getElementById('fact-btn').disabled = true;

    // Adicionar efeito visual no botão clicado
    const botaoClicado = resposta ? document.getElementById('fact-btn') : document.getElementById('fake-btn');
    botaoClicado.style.transform = 'scale(0.95)';
    setTimeout(() => {
        botaoClicado.style.transform = '';
    }, 200);

    try {
        const resultado = await apiRequest(`/noticias/${currentNoticia.id}/responder`, {
            method: 'POST',
            body: JSON.stringify({ resposta })
        });

        // Efeito de confete visual quando o usuário acerta
        if (resultado.acertou) {
            createConfetti();
        }

        mostrarFeedback(resultado);
        await updateProgressoDisplay();
    } catch (error) {
        alert('Erro ao responder: ' + error.message);
        document.getElementById('fake-btn').disabled = false;
        document.getElementById('fact-btn').disabled = false;
    }
}

// Função para criar efeito de confete
function createConfetti() {
    const colors = ['#ffd700', '#ff6348', '#2ed573', '#667eea', '#764ba2'];
    const confettiCount = 50;

    for (let i = 0; i < confettiCount; i++) {
        const confetti = document.createElement('div');
        confetti.style.position = 'fixed';
        confetti.style.width = '10px';
        confetti.style.height = '10px';
        confetti.style.backgroundColor = colors[Math.floor(Math.random() * colors.length)];
        confetti.style.left = Math.random() * window.innerWidth + 'px';
        confetti.style.top = '-10px';
        confetti.style.opacity = '1';
        confetti.style.borderRadius = '50%';
        confetti.style.pointerEvents = 'none';
        confetti.style.zIndex = '9999';
        confetti.style.transition = 'all 2s ease-out';

        document.body.appendChild(confetti);

        setTimeout(() => {
            confetti.style.top = window.innerHeight + 'px';
            confetti.style.opacity = '0';
            confetti.style.transform = `rotate(${Math.random() * 360}deg)`;
        }, 100);

        setTimeout(() => {
            confetti.remove();
        }, 2100);
    }
}

function mostrarFeedback(resultado) {
    document.getElementById('noticia-card').style.display = 'none';

    const feedbackCard = document.getElementById('feedback-card');
    feedbackCard.classList.remove('hidden', 'correct', 'incorrect');

    // Preencher o carimbo (símbolo) e texto separados
    const carimboSimbolo = document.querySelector('.carimbo-simbolo');
    const carimboTexto = document.querySelector('.carimbo-texto-separado');

    if (resultado.acertou) {
        feedbackCard.classList.add('correct');
        carimboSimbolo.textContent = '✓';
        carimboTexto.textContent = 'CORRETO';
    } else {
        feedbackCard.classList.add('incorrect');
        carimboSimbolo.textContent = '✗';
        carimboTexto.textContent = 'INCORRETO';
    }

    document.getElementById('feedback-message').innerHTML = formatarTexto(resultado.explicacao || '');

    const pontosDiv = document.getElementById('pontos-ganhos');
    pontosDiv.className = resultado.pontosGanhos > 0 ? 'veredito-pontos positive' : 'veredito-pontos negative';

    // Montar mensagem de pontos com estrutura HTML melhorada
    let mensagemPontos = `<div class="pontos-principal">${resultado.pontosGanhos > 0 ? '+' : ''}${resultado.pontosGanhos} PONTOS</div>`;

    let infoExtras = [];

    // adicionar informação de nível global se subiu
    if (resultado.subiuNivelGlobal) {
        infoExtras.push(`<div class="pontos-info nivel">🎉 Subiu para Nível ${resultado.nivelGlobal}!</div>`);
    }

    // adicionar informação de título, se mudou
    if (resultado.mudouTitulo && resultado.tituloAtual) {
        infoExtras.push(`<div class="pontos-info titulo">👑 Novo título: ${resultado.tituloAtual}</div>`);
    }

    // adicionar informação de nível da categoria se subiu
    if (resultado.desbloqueouNovaPeca) {
        infoExtras.push(`<div class="pontos-info categoria">⭐ Nível ${resultado.nivelCategoria} nesta categoria!</div>`);

        // se subiu de nível, significa que desbloqueou uma nova peça
        if (resultado.nivelCategoria > 0 && resultado.nivelCategoria <= 4) {
            infoExtras.push(`<div class="pontos-info peca">🎨 Nova peça desbloqueada! (${resultado.nivelCategoria}/4)</div>`);
        }
    }

    // Verificar se categoria foi concluída (100%)
    const categoriaCompleta = resultado.percentualProgresso >= 100;
    if (categoriaCompleta) {
        infoExtras.push(`<div class="pontos-info completa">🎊 Categoria 100% concluída!</div>`);
    }

    // Adicionar informações extras se houver
    if (infoExtras.length > 0) {
        mensagemPontos += `<div class="pontos-extras">${infoExtras.join('')}</div>`;
    }

    // Mostrar popup para conquistas desbloqueadas
    if (resultado.conquistasDesbloqueadas && resultado.conquistasDesbloqueadas.length > 0) {
        // Tocar som de conquista
        if (window.playAchievementSound) {
            window.playAchievementSound();
        }

        resultado.conquistasDesbloqueadas.forEach(conquista => {
            showToast(`🏆 Conquista desbloqueada: ${conquista.nome}!`, 'success');
        });
    }

    pontosDiv.innerHTML = mensagemPontos;

    // atualizar badge de nível no header
    if (resultado.nivelGlobal !== undefined) {
        document.getElementById('navbar-user-level').textContent = `Nv.${resultado.nivelGlobal}`;
    }

    // Atualizar botão "Próxima Notícia" baseado no progresso
    const nextBtn = document.getElementById('next-btn');
    const botaoTexto = nextBtn.querySelector('.botao-texto');
    const botaoIcon = nextBtn.querySelector('.botao-icon');

    if (categoriaCompleta) {
        if (botaoTexto) botaoTexto.textContent = 'VOLTAR PARA O MENU';
        if (botaoIcon) botaoIcon.textContent = '←';
        nextBtn.onclick = () => showScreen('category-screen');
    } else {
        if (botaoTexto) botaoTexto.textContent = 'PRÓXIMA NOTÍCIA';
        if (botaoIcon) botaoIcon.textContent = '▶';
        nextBtn.onclick = () => loadNextNoticia();
    }
}

document.getElementById('back-to-categories').addEventListener('click', () => {
    showScreen('category-screen');
});

// ========== PERFIL ==========
async function loadProfile() {
    try {
        const perfil = await apiRequest('/meu-perfil');

        const usuario = perfil.usuario || perfil;

        document.getElementById('profile-name').textContent = usuario.nome || 'Usuário';

        // Exibir nível e título
        const nivelTexto = usuario.tituloAtual
            ? `Nível ${usuario.nivel} - ${usuario.tituloAtual}`
            : `Nível ${usuario.nivel}`;
        document.getElementById('profile-level').textContent = nivelTexto;

        // Atualizar barra de progresso de nível global
        const pontosAtuais = perfil.pontosAtuais || 0;
        const pontosFaltam = perfil.pontosFaltam || 100;
        const pontosProximoNivel = perfil.pontosProximoNivel || 100;

        // Calcular percentual de progresso para o próximo nível
        const pontosNoNivelAtual = pontosProximoNivel - pontosFaltam;
        const percentualNivel = (pontosNoNivelAtual / pontosProximoNivel) * 100;

        document.getElementById('nivel-pontos-atual').textContent = pontosAtuais;
        document.getElementById('nivel-pontos-faltam').textContent = pontosFaltam;
        document.getElementById('nivel-progress-fill').style.width = `${percentualNivel}%`;

        const totalRespostas = perfil.totalRespostas || 0;
        const totalAcertos = perfil.totalAcertos || 0;
        const totalErros = totalRespostas - totalAcertos;

        document.getElementById('total-respostas').textContent = totalRespostas;
        document.getElementById('total-acertos').textContent = totalAcertos;
        document.getElementById('total-erros').textContent = totalErros;
        document.getElementById('taxa-acerto').textContent = perfil.taxaAcerto ? `${perfil.taxaAcerto.toFixed(1)}%` : '0%';

        // Carregar progresso por categoria
        const categorias = await apiRequest('/categorias');
        const progressoContainer = document.getElementById('categorias-progresso');
        progressoContainer.innerHTML = '';

        for (const categoria of categorias) {
            try {
                const response = await apiRequest(`/categorias/${categoria.id}/meu-progresso`);

                // Composição OO: objetos separados (categoria, progresso, estatisticas)
                const percentualProgresso = response.estatisticas?.percentualProgresso || 0;
                const acertosUnicos = response.estatisticas?.acertosUnicos || 0;
                const totalNoticias = response.estatisticas?.totalNoticias || 0;
                const taxaAcertoCategoria = response.estatisticas?.taxaAcertoCategoria || 0;
                const tentativasNaCategoria = response.estatisticas?.tentativasNaCategoria || 0;
                const pecasDesbloqueadas = response.progresso?.pecasDesbloqueadas || [];
                const nivelCategoria = response.progresso?.nivelAtual || 0;

                const progressoItem = document.createElement('div');
                progressoItem.className = 'progress-item';

                // Criar puzzle de imagem por quadrantes - usando o caminho do banco de dados
                const imagemCategoria = categoria.caminhoImagemCompleta || 'assets/images/default.png';
                
                const puzzleHtml = `
                    <div class="reward-image-container">
                        <div class="image-puzzle">
                            <div class="puzzle-piece ${pecasDesbloqueadas.includes(1) ? 'unlocked' : 'locked'}" 
                                 data-piece="1" 
                                 style="${pecasDesbloqueadas.includes(1) ? `background-image: url('${imagemCategoria}')` : ''}"></div>
                            <div class="puzzle-piece ${pecasDesbloqueadas.includes(2) ? 'unlocked' : 'locked'}" 
                                 data-piece="2"
                                 style="${pecasDesbloqueadas.includes(2) ? `background-image: url('${imagemCategoria}')` : ''}"></div>
                            <div class="puzzle-piece ${pecasDesbloqueadas.includes(3) ? 'unlocked' : 'locked'}" 
                                 data-piece="3"
                                 style="${pecasDesbloqueadas.includes(3) ? `background-image: url('${imagemCategoria}')` : ''}"></div>
                            <div class="puzzle-piece ${pecasDesbloqueadas.includes(4) ? 'unlocked' : 'locked'}" 
                                 data-piece="4"
                                 style="${pecasDesbloqueadas.includes(4) ? `background-image: url('${imagemCategoria}')` : ''}"></div>
                        </div>
                        <div class="pieces-legend">
                            <span class="legend-item"><span class="legend-box unlocked"></span> Desbloqueado (${pecasDesbloqueadas.length}/4)</span>
                            <span class="legend-item"><span class="legend-box locked"></span> Bloqueado</span>
                        </div>
                    </div>
                `;

                progressoItem.innerHTML = `
                    <h4>${categoria.nome}</h4>
                    <p>Nível ${nivelCategoria} • ${acertosUnicos}/${totalNoticias} questões acertadas</p>
                    <p style="margin-top: 5px; font-size: 0.9rem; opacity: 0.8;">
                        Taxa de acertos: <strong>${taxaAcertoCategoria.toFixed(1)}%</strong> 
                        ${tentativasNaCategoria > 0 ? `(${tentativasNaCategoria} tentativa${tentativasNaCategoria > 1 ? 's' : ''})` : ''}
                    </p>
                    <div class="progress-bar">
                        <div class="progress-fill" style="width: ${percentualProgresso}%"></div>
                    </div>
                    ${puzzleHtml}
                `;
                progressoContainer.appendChild(progressoItem);
            } catch (error) {
                console.error(`Erro ao carregar progresso de ${categoria.nome}:`, error);
            }
        }
    } catch (error) {
        alert('Erro ao carregar perfil: ' + error.message);
    }
}

document.getElementById('back-to-menu-profile').addEventListener('click', () => {
    showScreen('menu-screen');
});

// ========== CONQUISTAS ==========
async function loadAchievements() {
    try {
        const conquistas = await apiRequest('/minhas-conquistas');
        const container = document.getElementById('conquistas-list');
        container.innerHTML = '';

        conquistas.forEach((conquista, index) => {
            const card = document.createElement('div');
            card.className = 'achievement-card';
            card.style.animationDelay = `${index * 0.1}s`;

            // os dados vêm diretamente no objeto conquista
            if (!conquista.desbloqueada) {
                card.classList.add('locked');
            }

            // Formatar data de desbloqueio
            let dataFormatada = '';
            if (conquista.desbloqueada && conquista.dataDesbloqueio) {
                try {
                    // LocalDateTime do Java pode vir como array [ano, mês, dia, hora, minuto, segundo]
                    let data;
                    if (Array.isArray(conquista.dataDesbloqueio)) {
                        // Formato: [2025, 11, 26, 14, 30, 45]
                        const [ano, mes, dia, hora, minuto] = conquista.dataDesbloqueio;
                        data = new Date(ano, mes - 1, dia, hora || 0, minuto || 0);
                    } else {
                        // Formato string ISO ou timestamp
                        data = new Date(conquista.dataDesbloqueio);
                    }
                    
                    if (!isNaN(data.getTime())) {
                        dataFormatada = `<small>Desbloqueada em ${data.toLocaleDateString('pt-BR', {
                            day: '2-digit',
                            month: '2-digit',
                            year: 'numeric',
                            hour: '2-digit',
                            minute: '2-digit'
                        })}</small>`;
                    }
                } catch (e) {
                    console.error('Erro ao formatar data:', e);
                    dataFormatada = '';
                }
            }

            // Herança - ConquistaComStatus extends Conquista
            card.innerHTML = `
                <div class="achievement-icon">${conquista.desbloqueada ? '🏆' : '🔒'}</div>
                <h4>${conquista.nome || 'Conquista'}</h4>
                <p>${conquista.descricao || 'Descrição não disponível'}</p>
                ${dataFormatada}
            `;
            container.appendChild(card);
        });

        if (conquistas.length === 0) {
            container.innerHTML = `
        <div class="no-achievements-card">
            <h3 class="no-achievements-title">Nenhuma Conquista Desbloqueada</h3>
            <p class="no-achievements-text">Continue investigando casos para desbloquear suas primeiras conquistas!</p>
        </div>
    `;
        }
    } catch (error) {
        showToast('Erro ao carregar conquistas: ' + error.message, 'error');
        console.error('Erro detalhado:', error);
    }
}

document.getElementById('back-to-menu-achievements').addEventListener('click', () => {
    showScreen('menu-screen');
});

// Verificar se já está logado ao carregar a página
window.addEventListener('load', async () => {
    try {
        const usuario = await apiRequest('/session/usuario');
        if (usuario && usuario.id) {
            currentUser = usuario;
            // Atualizar elementos da navbar
            document.getElementById('navbar-user-name').textContent = currentUser.nome;
            document.getElementById('navbar-user-level').textContent = `Nv.${currentUser.nivel}`;
            showScreen('menu-screen');
        }
    } catch (error) {
        // Não está logado, mostra tela de login
        showScreen('login-screen');
    }
});

// ========== INICIALIZAÇÃO DO ÁUDIO ==========
const backgroundMusic = document.getElementById('background-music');
const achievementSound = document.getElementById('achievement-sound');
let audioStarted = false;

// Função global para iniciar o áudio
window.startBackgroundMusic = function() {
    if (!audioStarted && backgroundMusic) {
        // Garantir que o volume está configurado
        if (backgroundMusic.volume === 0) {
            backgroundMusic.volume = 0.7;
        }

        backgroundMusic.play().then(() => {
            audioStarted = true;
            console.log('🎵 Música de fundo iniciada com sucesso!');
        }).catch(err => {
            console.log('ℹ️ Aguardando interação do usuário para iniciar música:', err.message);
        });
    }
};

// Função global para tocar som de conquista com fade
window.playAchievementSound = function() {
    if (!achievementSound || !backgroundMusic) return;

    // Salvar volume original da música de fundo
    const originalVolume = backgroundMusic.volume;

    // Fade out da música de fundo (reduzir para 10% em ~1 segundo)
    let fadeOutSteps = 0;
    const maxFadeOutSteps = 20;
    const fadeOutInterval = setInterval(() => {
        fadeOutSteps++;
        const targetVolume = originalVolume * 0.1; // 10% do volume original
        const step = (originalVolume - targetVolume) / maxFadeOutSteps;

        if (fadeOutSteps < maxFadeOutSteps) {
            backgroundMusic.volume = Math.max(targetVolume, backgroundMusic.volume - step);
        } else {
            clearInterval(fadeOutInterval);
            backgroundMusic.volume = targetVolume;
        }
    }, 50); // A cada 50ms

    // Tocar som de conquista
    achievementSound.currentTime = 0;
    achievementSound.volume = 0.8; // Volume fixo para o som de conquista

    achievementSound.play().catch(err => {
        console.log('ℹ️ Não foi possível tocar som de conquista:', err.message);
        // Se falhar, restaurar volume da música imediatamente
        clearInterval(fadeOutInterval);
        backgroundMusic.volume = originalVolume;
    });

    // Quando o som de conquista terminar, fazer fade in da música de fundo
    achievementSound.onended = function() {
        let fadeInSteps = 0;
        const maxFadeInSteps = 20;
        const currentVolume = backgroundMusic.volume;

        const fadeInInterval = setInterval(() => {
            fadeInSteps++;
            const step = (originalVolume - currentVolume) / maxFadeInSteps;

            if (fadeInSteps < maxFadeInSteps) {
                backgroundMusic.volume = Math.min(originalVolume, backgroundMusic.volume + step);
            } else {
                clearInterval(fadeInInterval);
                backgroundMusic.volume = originalVolume; // Restaurar volume original exato
            }
        }, 50); // A cada 50ms
    };
};

// Tentar iniciar áudio na primeira interação do usuário (qualquer clique)
document.addEventListener('click', window.startBackgroundMusic, { once: true });

// Tentar também em outras interações
document.addEventListener('keydown', window.startBackgroundMusic, { once: true });
document.addEventListener('touchstart', window.startBackgroundMusic, { once: true });

// ========== CONTROLE DE VOLUME FLUTUANTE ==========

(function() {
    // Elementos do DOM
    const audio = document.getElementById('background-music');
    const volumeIcon = document.getElementById('volume-icon');
    const volumeEmoji = document.getElementById('volume-emoji');
    const sliderContainer = document.getElementById('volume-slider-container');
    const volumeSlider = document.getElementById('volume-slider');
    const volumePercentage = document.getElementById('volume-percentage');

    // Estado do controle
    let isSliderVisible = false;
    let isMuted = false;
    let previousVolume = 0.7; // Volume padrão 70%
    let hideSliderTimeout = null;

    // Inicializar volume
    audio.volume = 0.7;
    volumeSlider.value = 70;

    // Função para atualizar o emoji do volume
    function updateVolumeEmoji(volume) {
        if (volume === 0 || isMuted) {
            volumeEmoji.textContent = '🔇';
        } else if (volume < 0.3) {
            volumeEmoji.textContent = '🔉';
        } else {
            volumeEmoji.textContent = '🔊';
        }
    }

    // Função para mostrar o slider
    function showSlider() {
        sliderContainer.classList.remove('volume-slider-hidden');
        sliderContainer.classList.add('volume-slider-visible');
        isSliderVisible = true;
        resetHideSliderTimeout();
    }

    // Função para esconder o slider
    function hideSlider() {
        sliderContainer.classList.remove('volume-slider-visible');
        sliderContainer.classList.add('volume-slider-hidden');
        isSliderVisible = false;
    }

    // Função para resetar o timeout de esconder o slider
    function resetHideSliderTimeout() {
        if (hideSliderTimeout) {
            clearTimeout(hideSliderTimeout);
        }
        hideSliderTimeout = setTimeout(() => {
            hideSlider();
        }, 2000);
    }

    // Evento de clique no ícone
    let clickCount = 0;
    let clickTimeout = null;

    volumeIcon.addEventListener('click', (e) => {
        e.stopPropagation();
        clickCount++;

        // Limpar timeout anterior
        if (clickTimeout) {
            clearTimeout(clickTimeout);
        }

        // Primeiro clique: mostrar slider
        if (clickCount === 1) {
            clickTimeout = setTimeout(() => {
                if (!isSliderVisible) {
                    showSlider();
                }
                clickCount = 0;
            }, 300);
        }
        // Segundo clique: mutar/desmutar
        else if (clickCount === 2) {
            clearTimeout(clickTimeout);
            clickCount = 0;

            if (isMuted) {
                // Desmutar
                audio.volume = previousVolume;
                volumeSlider.value = previousVolume * 100;
                volumePercentage.textContent = Math.round(previousVolume * 100) + '%';
                isMuted = false;
                updateVolumeEmoji(previousVolume);
            } else {
                // Mutar
                previousVolume = audio.volume;
                audio.volume = 0;
                volumeSlider.value = 0;
                volumePercentage.textContent = '0%';
                isMuted = true;
                updateVolumeEmoji(0);
            }
        }
    });

    // Evento de mudança no slider
    volumeSlider.addEventListener('input', (e) => {
        const value = e.target.value / 100;
        audio.volume = value;
        volumePercentage.textContent = e.target.value + '%';

        // Se estava mutado, desmutar
        if (isMuted && value > 0) {
            isMuted = false;
        }

        // Se foi para 0, considerar mutado
        if (value === 0) {
            isMuted = true;
        } else {
            previousVolume = value;
        }

        updateVolumeEmoji(value);
        resetHideSliderTimeout();
    });

    // Manter slider visível ao interagir com ele
    sliderContainer.addEventListener('mouseenter', () => {
        if (hideSliderTimeout) {
            clearTimeout(hideSliderTimeout);
        }
    });

    sliderContainer.addEventListener('mouseleave', () => {
        resetHideSliderTimeout();
    });

    // Esconder slider ao clicar fora
    document.addEventListener('click', (e) => {
        if (!volumeIcon.contains(e.target) && !sliderContainer.contains(e.target)) {
            if (isSliderVisible) {
                hideSlider();
            }
        }
    });

    // Impedir que cliques no slider fechem ele
    sliderContainer.addEventListener('click', (e) => {
        e.stopPropagation();
        resetHideSliderTimeout();
    });

    // Atualizar emoji inicial
    updateVolumeEmoji(audio.volume);
})();
