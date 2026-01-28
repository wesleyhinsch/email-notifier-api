# Email Notifier API 📧➡️📱

API Java Spring Boot para receber notificações de emails específicos no WhatsApp via integração com n8n.

## 🎯 Funcionalidades

- ✅ Cadastrar monitores de email (email + remetente + WhatsApp)
- ✅ Webhook para n8n enviar dados de emails recebidos
- ✅ Histórico completo de notificações
- ✅ Ativar/Desativar monitores
- ✅ Banco de dados H2 (local) ou PostgreSQL (produção)
- ✅ Pronto para deploy no Oracle Cloud (100% grátis)

## 🏗️ Arquitetura

```
src/main/java/com/emailnotifier/
├── entity/              # Entidades JPA
│   ├── EmailMonitor     # Configuração de monitoramento
│   └── Notification     # Histórico de notificações
├── repository/          # Repositórios Spring Data
├── service/             # Lógica de negócio
├── controller/          # REST Controllers
└── dto/                 # Data Transfer Objects
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven

### Executar Localmente
```bash
# Modo desenvolvimento (rápido)
run-dev.bat

# Ou com Maven
mvn spring-boot:run
```

Acesse: http://localhost:8080

### Deploy no Oracle Cloud (100% Grátis)

**1. Configurar Git:**
```bash
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/SEU-USUARIO/email-notifier-api.git
git push -u origin main
```

**2. Deploy:**
Veja guia completo: **[ORACLE-CLOUD.md](ORACLE-CLOUD.md)**

## 📡 Endpoints da API

### 1. Criar Monitor
```http
POST /api/monitors
Content-Type: application/json

{
  "name": "Alertas de Vendas",
  "monitoredEmail": "meuemail@gmail.com",
  "senderFilter": "vendas@loja.com",
  "whatsappNumber": "5511999999999"
}
```

### 2. Listar Monitores
```http
GET /api/monitors
```

### 3. Buscar Monitor por ID
```http
GET /api/monitors/{id}
```

### 4. Ativar/Desativar Monitor
```http
PATCH /api/monitors/{id}/toggle
```

### 5. Deletar Monitor
```http
DELETE /api/monitors/{id}
```

### 6. Listar Notificações
```http
GET /api/notifications
```

### 7. Notificações por Monitor
```http
GET /api/notifications/monitor/{monitorId}
```

### 8. Webhook (usado pelo n8n)
```http
POST /api/webhook/email-received
Content-Type: application/json

{
  "from": "vendas@loja.com",
  "to": "meuemail@gmail.com",
  "subject": "Nova venda realizada",
  "body": "Você recebeu uma nova venda..."
}
```

**Resposta do Webhook:**
```json
{
  "status": "success",
  "whatsappNumber": "5511999999999",
  "message": "📧 *Novo Email Recebido*\n\n👤 *De:* vendas@loja.com..."
}
```

## 🔧 Integração com n8n

### Fluxo Completo

```
1. Email Trigger (IMAP/Gmail)
   ↓
2. Filter (verificar remetente)
   ↓
3. HTTP Request → POST /api/webhook/email-received
   {
     "from": "{{$json.from}}",
     "to": "{{$json.to}}",
     "subject": "{{$json.subject}}",
     "body": "{{$json.text}}"
   }
   ↓
4. IF Node (verificar response.status == "success")
   ↓
5. WhatsApp Node (Twilio/Evolution API)
   - Número: {{$json.whatsappNumber}}
   - Mensagem: {{$json.message}}
```

### Configuração n8n - Passo a Passo

#### 1. Email Trigger
- Node: **Email Trigger (IMAP)**
- Host: imap.gmail.com
- Port: 993
- User: seu-email@gmail.com
- Password: senha-de-app
- Mailbox: INBOX

#### 2. HTTP Request
- Method: POST
- URL: http://localhost:8080/api/webhook/email-received
- Body:
```json
{
  "from": "={{$json.from.value[0].address}}",
  "to": "={{$json.to.value[0].address}}",
  "subject": "={{$json.subject}}",
  "body": "={{$json.text}}"
}
```

#### 3. IF Node
- Condition: `{{$json.status}}` equals `success`

#### 4. WhatsApp (Evolution API)
- URL: sua-instancia-evolution/message/sendText
- Body:
```json
{
  "number": "={{$json.whatsappNumber}}",
  "text": "={{$json.message}}"
}
```

## 🗄️ Banco de Dados

### Local (H2)
H2 Database (arquivo local em `./data/emailnotifier.mv.db`)

**Console H2:**
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:file:./data/emailnotifier
- User: sa
- Password: (vazio)

### Produção (PostgreSQL)
PostgreSQL configurado via variáveis de ambiente
- Conexão via variável `DATABASE_URL`
- Migrations automáticas com Hibernate
- Suporte para Cloud SQL, Supabase, Neon

## 📊 Exemplo de Uso

### 1. Cadastrar Monitor
```bash
curl -X POST http://localhost:8080/api/monitors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alertas Mercado Livre",
    "monitoredEmail": "joao@gmail.com",
    "senderFilter": "noreply@mercadolivre.com",
    "whatsappNumber": "5511987654321"
  }'
```

### 2. Simular Webhook (testar)
```bash
curl -X POST http://localhost:8080/api/webhook/email-received \
  -H "Content-Type: application/json" \
  -d '{
    "from": "noreply@mercadolivre.com",
    "to": "joao@gmail.com",
    "subject": "Você vendeu um produto!",
    "body": "Parabéns! Você vendeu: iPhone 13 Pro por R$ 3.500"
  }'
```

### 3. Ver Histórico
```bash
curl http://localhost:8080/api/notifications
```

## 🔐 Segurança

Para produção, adicione:
- Autenticação JWT
- HTTPS
- Rate limiting
- Validação de webhook signature

## 📝 Tecnologias

- Java 17
- Spring Boot 3.2
- Spring Data JPA
- H2 Database (local)
- PostgreSQL (produção)
- Lombok
- Maven

## 📚 Documentação

- **[TESTE-LOCAL.md](TESTE-LOCAL.md)** - Testes locais
- **[ORACLE-CLOUD.md](ORACLE-CLOUD.md)** - Deploy Oracle Cloud (100% grátis)
- **[N8N_SETUP.md](N8N_SETUP.md)** - Configuração n8n

## 🤝 Contribuindo

1. Faça um fork
2. Crie uma branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## 📄 Licença

MIT License
