#!/bin/bash
# Setup script for Fact or Fake Dev Container
# This runs automatically when the container is created

set -e  # Para em caso de erro

echo "🎮 Configurando ambiente Fact or Fake..."
echo ""

# 1. Verificar Java
echo "☕ Verificando Java..."
java -version
echo ""

# 2. Compilar o projeto
echo "🔨 Compilando projeto com Maven..."
if mvn clean package -DskipTests; then
    echo "✅ Compilação concluída com sucesso!"
else
    echo "❌ Erro na compilação. Verifique os logs acima."
    exit 1
fi
echo ""

# 3. Verificar se PostgreSQL está disponível
echo "🐘 Verificando PostgreSQL..."
if command -v psql &> /dev/null; then
    echo "PostgreSQL client instalado"

    # Aguardar PostgreSQL ficar pronto (até 30 segundos)
    echo "Aguardando PostgreSQL inicializar..."
    RETRIES=30
    until pg_isready -h localhost -p 5432 -U postgres &> /dev/null || [ $RETRIES -eq 0 ]; do
        echo "  Aguardando... ($RETRIES tentativas restantes)"
        RETRIES=$((RETRIES - 1))
        sleep 1
    done

    if pg_isready -h localhost -p 5432 -U postgres &> /dev/null; then
        echo "✅ PostgreSQL está rodando!"

        # Criar banco de dados se não existir
        if psql -h localhost -U postgres -lqt 2>/dev/null | cut -d \| -f 1 | grep -qw fact_or_fake; then
            echo "✅ Banco de dados 'fact_or_fake' já existe"
        else
            echo "📊 Criando banco de dados 'fact_or_fake'..."
            PGPASSWORD=postgres psql -h localhost -U postgres -c "CREATE DATABASE fact_or_fake;" 2>/dev/null || true
        fi
    else
        echo "⚠️  PostgreSQL não respondeu. Configure manualmente se necessário."
    fi
else
    echo "⚠️  PostgreSQL não instalado no container"
    echo "   Você pode usar um PostgreSQL externo"
fi
echo ""

# 4. Mostrar instruções
echo "════════════════════════════════════════════════════════════"
echo "✨ Ambiente pronto para desenvolvimento!"
echo "════════════════════════════════════════════════════════════"
echo ""
echo "📌 Para rodar a aplicação:"
echo "   mvn exec:java"
echo ""
echo "🌐 A API estará disponível em:"
echo "   http://localhost:3000"
echo ""
echo "🧪 Para testar endpoints (Linux/Mac/Codespaces):"
echo "   curl http://localhost:3000/usuarios"
echo ""
echo "🐘 Para conectar ao PostgreSQL:"
echo "   psql -h localhost -U postgres -d fact_or_fake"
echo ""
echo "📚 Documentação do projeto: README.md"
echo "════════════════════════════════════════════════════════════"
echo ""



