// Configuração da API
const API_BASE = 'http://localhost:3000';

// Estado da aplicação
let currentUser = null;
let currentCategory = null;
let currentNoticia = null;

// Função para mostrar notificação toast
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
    try {
        const response = await fetch(`${API_BASE}${url}`, {
            ...options,
            credentials: 'include', // Importante para manter a sessão
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            }
        });

        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || `Erro ${response.status}`);
        }

        // Verifica se a resposta tem conteúdo JSON
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        } else {
            // Se não for JSON, retorna o texto
            return await response.text();
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
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

    if (!email) {
        showToast('Por favor, digite seu e-mail', 'error');
        return;
    }

    try {
        const data = await apiRequest('/login', {
            method: 'POST',
            body: JSON.stringify({ email })
        });

        currentUser = data.usuario;
        document.getElementById('user-name').textContent = currentUser.nome;

        // Exibir nível e título
        const nivelTexto = currentUser.tituloAtual
            ? `Nível ${currentUser.nivel} - ${currentUser.tituloAtual}`
            : `Nível ${currentUser.nivel}`;
        document.getElementById('user-level').textContent = nivelTexto;

        showToast(`Bem-vindo(a), ${currentUser.nome}! 🎮`, 'success');
        showScreen('menu-screen');
    } catch (error) {
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
        await apiRequest('/logout', { method: 'POST' });
        currentUser = null;
        showToast('Você saiu com sucesso! 👋', 'success');
        showScreen('login-screen');
        document.getElementById('email-input').value = '';
    } catch (error) {
        console.error('Erro ao fazer logout:', error);
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
            card.style.animationDelay = `${index * 0.1}s`;
            card.innerHTML = `
                <h3>${categoria.nome}</h3>
                <p>${categoria.descricao || 'Clique para jogar'}</p>
            `;
            card.addEventListener('click', () => {
                card.style.transform = 'scale(0.95)';
                setTimeout(() => startGame(categoria), 150);
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
        document.getElementById('noticia-conteudo').textContent = currentNoticia.conteudo;
        document.getElementById('noticia-card').style.display = 'block';
        document.getElementById('feedback-card').classList.add('hidden');
        
        // Habilitar botões
        document.getElementById('fake-btn').disabled = false;
        document.getElementById('fact-btn').disabled = false;
        
        await updateMaestriaDisplay();
    } catch (error) {
        // Se não conseguiu carregar, mostra erro e permanece na tela de categorias
        showToast(error.message, 'error');
    }
}

async function loadNextNoticia() {
    try {
        currentNoticia = await apiRequest(`/noticias/random/categoria/${currentCategory.id}`);

        document.getElementById('noticia-titulo').textContent = currentNoticia.titulo;
        document.getElementById('noticia-conteudo').textContent = currentNoticia.conteudo;

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

async function updateMaestriaDisplay() {
    try {
        const progresso = await apiRequest(`/categorias/${currentCategory.id}/meu-progresso`);
        document.getElementById('maestria-points').textContent = progresso.pontos || 0;
    } catch (error) {
        console.error('Erro ao carregar maestria:', error);
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

        // Efeito de confete visual para acertos
        if (resultado.acertou) {
            createConfetti();
        }

        mostrarFeedback(resultado);
        await updateMaestriaDisplay();
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

    document.getElementById('feedback-message').textContent = resultado.explicacao || '';
    document.getElementById('feedback-fonte').textContent = resultado.fonte ? `Fonte: ${resultado.fonte}` : '';

    const pontosDiv = document.getElementById('pontos-ganhos');
    pontosDiv.className = resultado.pontosGanhos > 0 ? 'positive' : 'negative';

    // Montar mensagem de pontos com informações extras
    let mensagemPontos = `${resultado.pontosGanhos > 0 ? '+' : ''}${resultado.pontosGanhos} pontos`;

    // Adicionar informação de nível global se subiu
    if (resultado.subiuNivelGlobal) {
        mensagemPontos += `\n🎉 Subiu para Nível ${resultado.nivelGlobal}!`;
    }

    // Adicionar informação de título se mudou
    if (resultado.mudouTitulo && resultado.tituloAtual) {
        mensagemPontos += `\n👑 Novo título: ${resultado.tituloAtual}`;
    }

    // Adicionar informação de nível de categoria se subiu
    if (resultado.subiuNivelCategoria) {
        mensagemPontos += `\n⭐ Subiu para nível ${resultado.nivelCategoria} nesta categoria!`;
    }

    pontosDiv.innerHTML = mensagemPontos.replace(/\n/g, '<br>');

    // Atualizar badge de nível no header
    if (resultado.nivelGlobal && resultado.tituloAtual) {
        document.getElementById('user-level').textContent = `Nível ${resultado.nivelGlobal} - ${resultado.tituloAtual}`;
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

        document.getElementById('total-respostas').textContent = perfil.totalRespostas || 0;
        document.getElementById('total-acertos').textContent = perfil.totalAcertos || 0;
        document.getElementById('taxa-acerto').textContent = perfil.taxaAcerto ? `${perfil.taxaAcerto.toFixed(1)}%` : '0%';
        document.getElementById('pontuacao-total').textContent = usuario.pontuacaoTotal || 0;

        // Carregar progresso por categoria
        const categorias = await apiRequest('/categorias');
        const progressoContainer = document.getElementById('categorias-progresso');
        progressoContainer.innerHTML = '';

        for (const categoria of categorias) {
            try {
                const progresso = await apiRequest(`/categorias/${categoria.id}/meu-progresso`);

                const progressoItem = document.createElement('div');
                progressoItem.className = 'progress-item';

                const pontos = progresso.pontos || 0;
                const nivelAtual = progresso.nivelAtual || 1;
                const pontosProximoNivel = progresso.pontosProximoNivel || 100;
                const percentual = Math.min((pontos / pontosProximoNivel) * 100, 100);

                progressoItem.innerHTML = `
                    <h4>${categoria.nome}</h4>
                    <p>Nível ${nivelAtual} - ${pontos} pontos</p>
                    <div class="progress-bar">
                        <div class="progress-fill" style="width: ${percentual}%"></div>
                    </div>
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

            // Agora os dados vêm diretamente no objeto conquista
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
            container.innerHTML = '<p style="color: white; font-size: 1.2em; margin-top: 50px;">🎯 Nenhuma conquista desbloqueada ainda. Continue jogando!</p>';
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
            document.getElementById('user-name').textContent = currentUser.nome;
            document.getElementById('user-level').textContent = `Nível ${currentUser.nivel}`;
            showScreen('menu-screen');
        }
    } catch (error) {
        // Não está logado, mostra tela de login
        showScreen('login-screen');
    }
});