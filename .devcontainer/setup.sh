#!/bin/bash
# Setup script for Fact or Fake Dev Container
# This runs automatically when the container is created

set -e  # Para em caso de erro

echo "🎮 Configurando ambiente Fact or Fake..."
echo ""

# 1. Verificar Java e Maven
echo "☕ Verificando Java e Maven..."
java -version
mvn -version
echo ""

# 2. Aguardar PostgreSQL ficar pronto
echo "🐘 Aguardando PostgreSQL inicializar..."
RETRIES=60
until pg_isready -h localhost -p 5432 -U postgres 2>/dev/null || [ $RETRIES -eq 0 ]; do
    echo "  Aguardando PostgreSQL... ($RETRIES tentativas restantes)"
    RETRIES=$((RETRIES - 1))
    sleep 2
done

if pg_isready -h localhost -p 5432 -U postgres 2>/dev/null; then
    echo "✅ PostgreSQL está rodando!"
    
    # Verificar se as tabelas foram criadas
    echo ""
    echo "📊 Verificando banco de dados..."
    export PGPASSWORD=postgres
    
    TABLES=$(psql -h localhost -U postgres -d fact_or_fake -t -c "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public';" 2>/dev/null | tr -d ' ')
    
    if [ "$TABLES" -gt 0 ]; then
        echo "✅ Banco de dados criado e populado automaticamente!"
        echo "   Tabelas encontradas: $TABLES"
    else
        echo "⚠️  Nenhuma tabela encontrada. Os scripts SQL serão executados automaticamente."
    fi
else
    echo "❌ PostgreSQL não respondeu após 2 minutos."
    echo "   Verifique os logs do container."
    exit 1
fi
echo ""

# 3. Compilar o projeto
echo "🔨 Compilando projeto com Maven..."
cd /workspaces/gamification-2025b-renato-e-otavio-pias-do-rs
if mvn clean compile; then
    echo "✅ Compilação concluída com sucesso!"
else
    echo "❌ Erro na compilação. Verifique os logs acima."
    exit 1
fi
echo ""

# 4. Mostrar instruções
echo "════════════════════════════════════════════════════════════"
echo "✨ Ambiente PRONTO para uso! Tudo configurado automaticamente:"
echo "════════════════════════════════════════════════════════════"
echo ""
echo "✅ Java 17 instalado"
echo "✅ Maven instalado e projeto compilado"
echo "✅ PostgreSQL rodando (localhost:5432)"
echo "✅ Banco 'fact_or_fake' criado"
echo "✅ Tabelas criadas (criar_tabelas_iniciais.sql)"
echo "✅ Dados populados (popular_tabelas.sql)"
echo ""
echo "════════════════════════════════════════════════════════════"
echo "🚀 PARA INICIAR A APLICAÇÃO:"
echo "════════════════════════════════════════════════════════════"
echo ""
echo "   mvn exec:java"
echo ""
echo "🌐 A API estará disponível em: http://localhost:3000"
echo ""
echo "════════════════════════════════════════════════════════════"
echo "🔧 COMANDOS ÚTEIS:"
echo "════════════════════════════════════════════════════════════"
echo ""
echo "🐘 Conectar ao PostgreSQL:"
echo "   psql -h localhost -U postgres -d fact_or_fake"
echo "   (senha: postgres)"
echo ""
echo "📊 Ver tabelas criadas:"
echo "   psql -h localhost -U postgres -d fact_or_fake -c '\\dt'"
echo ""
echo "🔍 Ver dados inseridos (exemplo):"
echo "   psql -h localhost -U postgres -d fact_or_fake -c 'SELECT * FROM categorias;'"
echo ""
echo "🧪 Testar API:"
echo "   curl http://localhost:3000/categorias"
echo ""
echo "════════════════════════════════════════════════════════════"
echo ""



