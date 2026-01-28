#!/bin/bash
# Script de instalação para Oracle Cloud VM

echo "=========================================="
echo "  Email Notifier API - Oracle Cloud"
echo "=========================================="
echo ""

# Atualizar sistema
echo "[1/6] Atualizando sistema..."
sudo apt update && sudo apt upgrade -y

# Instalar Java 17
echo "[2/6] Instalando Java 17..."
sudo apt install openjdk-17-jdk -y

# Instalar PostgreSQL
echo "[3/6] Instalando PostgreSQL..."
sudo apt install postgresql postgresql-contrib -y
sudo systemctl start postgresql
sudo systemctl enable postgresql

# Configurar banco
echo "[4/6] Configurando banco de dados..."
read -sp "Digite senha para o banco: " DB_PASSWORD
echo ""

sudo -u postgres psql << EOF
CREATE DATABASE emailnotifier;
CREATE USER emailnotifier WITH PASSWORD '$DB_PASSWORD';
GRANT ALL PRIVILEGES ON DATABASE emailnotifier TO emailnotifier;
\q
EOF

# Configurar firewall
echo "[5/6] Configurando firewall..."
sudo iptables -I INPUT 6 -m state --state NEW -p tcp --dport 8080 -j ACCEPT
sudo iptables -I INPUT 6 -m state --state NEW -p tcp --dport 80 -j ACCEPT
sudo iptables -I INPUT 6 -m state --state NEW -p tcp --dport 443 -j ACCEPT
sudo apt install iptables-persistent -y

# Criar diretório
mkdir -p /home/ubuntu/app
cd /home/ubuntu/app

# Criar configuração
cat > application-prod.properties << EOF
server.port=8080
spring.application.name=email-notifier-api
spring.datasource.url=jdbc:postgresql://localhost:5432/emailnotifier
spring.datasource.username=emailnotifier
spring.datasource.password=$DB_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
logging.level.com.emailnotifier=INFO
EOF

# Criar serviço systemd
echo "[6/6] Criando serviço..."
sudo tee /etc/systemd/system/email-notifier.service > /dev/null << EOF
[Unit]
Description=Email Notifier API
After=network.target postgresql.service

[Service]
Type=simple
User=ubuntu
WorkingDirectory=/home/ubuntu/app
ExecStart=/usr/bin/java -jar -Dspring.config.location=/home/ubuntu/app/application-prod.properties /home/ubuntu/app/email-notifier-api-1.0.0.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF

sudo systemctl daemon-reload

echo ""
echo "=========================================="
echo "  INSTALAÇÃO CONCLUÍDA!"
echo "=========================================="
echo ""
echo "Próximos passos:"
echo "1. Envie o JAR para /home/ubuntu/app/"
echo "2. sudo systemctl start email-notifier"
echo "3. sudo systemctl enable email-notifier"
echo ""
