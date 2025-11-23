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

// ========== TELA DE LOGIN ==========
document.getElementById('login-btn').addEventListener('click', async () => {
    const email = document.getElementById('email-input').value.trim();

    // Limpar possíveis "toasts" antigos
    document.querySelectorAll('.toast').forEach(toast => toast.remove());

    if (!email) {
        showToast('Por favor, digite seu e-mail', 'error');
        return;
    }

    console.log('🔵 Iniciando login com email:', email);

    try {
        // Fazer requisição diretamente com fetch para ter mais controle
        const response = await fetch(`${API_BASE}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify({ email })
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

        console.log('✅ Login completo!');
    } catch (error) {
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

    if (!nome || !email) {
        showToast('Por favor, preencha todos os campos', 'error');
        return;
    }

    try {
        await apiRequest('/usuarios', {
            method: 'POST',
            body: JSON.stringify({ nome, email })
        });

        showToast('Conta criada com sucesso! 🎉 Faça login agora.', 'success');
        showScreen('login-screen');
        document.getElementById('email-input').value = email;
    } catch (error) {
        showToast('Erro ao criar conta: ' + error.message, 'error');
    }
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

    // Limpar campo de email
    const emailInput = document.getElementById('email-input');
    if (emailInput) {
        emailInput.value = '';
    }
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

            card.innerHTML = `
                <div class="category-image-placeholder" aria-hidden="true"></div>
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
        const progresso = await apiRequest(`/categorias/${currentCategory.id}/meu-progresso`);
        // percentualProgresso pode estar em estatisticas ou diretamente no objeto
        let percentualProgresso = 0;
        if (progresso.estatisticas && progresso.estatisticas.percentualProgresso !== undefined) {
            percentualProgresso = progresso.estatisticas.percentualProgresso;
        } else if (progresso.percentualProgresso !== undefined) {
            percentualProgresso = progresso.percentualProgresso;
        }
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

    if (resultado.acertou) {
        feedbackCard.classList.add('correct');
        document.getElementById('feedback-title').textContent = '✅ Correto!';
    } else {
        feedbackCard.classList.add('incorrect');
        document.getElementById('feedback-title').textContent = '❌ Incorreto!';
    }

    document.getElementById('feedback-message').innerHTML = formatarTexto(resultado.explicacao || '');
    document.getElementById('feedback-fonte').textContent = resultado.fonte ? `Fonte: ${resultado.fonte}` : '';

    const pontosDiv = document.getElementById('pontos-ganhos');
    pontosDiv.className = resultado.pontosGanhos > 0 ? 'positive' : 'negative';

    // Montar mensagem de pontos com informações extras
    let mensagemPontos = `${resultado.pontosGanhos > 0 ? '+' : ''}${resultado.pontosGanhos} pontos`;

    // adicionar informação de nível global se subiu
    if (resultado.subiuNivelGlobal) {
        mensagemPontos += `\n🎉 Subiu para Nível ${resultado.nivelGlobal}!`;
    }

    // adicionar informação de título, se mudou
    if (resultado.mudouTitulo && resultado.tituloAtual) {
        mensagemPontos += `\n👑 Novo título: ${resultado.tituloAtual}`;
    }

    // adicionar informação de nível da categoria se subiu
    if (resultado.desbloqueouNovaPeca) {
        mensagemPontos += `\n⭐ Subiu para nível ${resultado.nivelCategoria} nesta categoria!`;
        
        // se subiu de nível, significa que desbloqueou uma nova peça
        if (resultado.nivelCategoria > 0 && resultado.nivelCategoria <= 4) {
            mensagemPontos += `\n🎨 Nova peça da imagem desbloqueada! (${resultado.nivelCategoria}/4)`;
        }
    }

    // Mostrar popup para conquistas desbloqueadas
    if (resultado.conquistasDesbloqueadas && resultado.conquistasDesbloqueadas.length > 0) {
        resultado.conquistasDesbloqueadas.forEach(conquista => {
            showToast(`🏆 Conquista desbloqueada: ${conquista.nome}!`, 'success');
        });
    }

    pontosDiv.innerHTML = mensagemPontos.replace(/\n/g, '<br>');

    // atualizar badge de nível no header
    if (resultado.nivelGlobal !== undefined) {
        document.getElementById('navbar-user-level').textContent = `Nv.${resultado.nivelGlobal}`;
    }
}

document.getElementById('next-btn').addEventListener('click', () => {
    loadNextNoticia();
});

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

        document.getElementById('total-respostas').textContent = perfil.totalRespostas || 0;
        document.getElementById('total-acertos').textContent = perfil.totalAcertos || 0;
        document.getElementById('taxa-acerto').textContent = perfil.taxaAcerto ? `${perfil.taxaAcerto.toFixed(1)}%` : '0%';

        // Carregar progresso por categoria
        const categorias = await apiRequest('/categorias');
        const progressoContainer = document.getElementById('categorias-progresso');
        progressoContainer.innerHTML = '';

        for (const categoria of categorias) {
            try {
                const progresso = await apiRequest(`/categorias/${categoria.id}/meu-progresso`);

                // Obter estatísticas (pode estar em progresso.estatisticas ou direto em progresso)
                const estatisticas = progresso.estatisticas || progresso;
                const percentualProgresso = estatisticas.percentualProgresso || 0;
                const acertosUnicos = estatisticas.acertosUnicos || 0;
                const totalNoticias = estatisticas.totalNoticias || 0;
                const taxaAcertoCategoria = estatisticas.taxaAcertoCategoria || 0;
                const tentativasNaCategoria = estatisticas.tentativasNaCategoria || 0;

                // Peças desbloqueadas do backend
                const pecasDesbloqueadas = progresso.pecasDesbloqueadas || [];
                const nivelCategoria = progresso.nivelAtual || 0;

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
                    const data = new Date(conquista.dataDesbloqueio);
                    dataFormatada = `<small>Desbloqueada em ${data.toLocaleDateString('pt-BR', {
                        day: '2-digit',
                        month: '2-digit',
                        year: 'numeric',
                        hour: '2-digit',
                        minute: '2-digit'
                    })}</small>`;
                } catch (e) {
                    console.error('Erro ao formatar data:', e);
                    dataFormatada = '';
                }
            }

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