# Guia de Configuração n8n

## 📋 Pré-requisitos

1. **n8n instalado** (https://n8n.io)
2. **Email Notifier API rodando** (http://localhost:8080)
3. **WhatsApp API** (Evolution API, Twilio, ou similar)

## 🔧 Configuração Passo a Passo

### 1️⃣ Criar Workflow no n8n

Acesse n8n e crie um novo workflow.

### 2️⃣ Adicionar Email Trigger

**Node:** Email Trigger (IMAP)

**Configurações:**
- **Host:** imap.gmail.com (ou seu provedor)
- **Port:** 993
- **User:** seu-email@gmail.com
- **Password:** senha-de-app-do-gmail
- **Mailbox:** INBOX
- **Check Interval:** 1 minute

**Como criar senha de app no Gmail:**
1. Acesse: https://myaccount.google.com/security
2. Ative "Verificação em duas etapas"
3. Vá em "Senhas de app"
4. Gere uma senha para "Email"

### 3️⃣ Adicionar HTTP Request

**Node:** HTTP Request

**Configurações:**
- **Method:** POST
- **URL:** http://localhost:8080/api/webhook/email-received
- **Authentication:** None
- **Body Content Type:** JSON

**Body (JSON):**
```json
{
  "from": "={{$json.from.value[0].address}}",
  "to": "={{$json.to.value[0].address}}",
  "subject": "={{$json.subject}}",
  "body": "={{$json.text}}"
}
```

### 4️⃣ Adicionar IF Node

**Node:** IF

**Configurações:**
- **Condition:** String
- **Value 1:** `={{$json.status}}`
- **Operation:** Equal
- **Value 2:** `success`

### 5️⃣ Adicionar WhatsApp Node

**Opção A: Evolution API**

**Node:** HTTP Request

**Configurações:**
- **Method:** POST
- **URL:** https://sua-instancia.evolution.com/message/sendText/sua-instancia
- **Headers:**
  - `apikey`: sua-api-key
- **Body:**
```json
{
  "number": "={{$json.whatsappNumber}}",
  "text": "={{$json.message}}"
}
```

**Opção B: Twilio**

**Node:** Twilio

**Configurações:**
- **Resource:** Send Message
- **From:** whatsapp:+14155238886
- **To:** `whatsapp:+={{$json.whatsappNumber}}`
- **Message:** `={{$json.message}}`

### 6️⃣ Conectar os Nodes

```
Email Trigger → HTTP Request → IF → WhatsApp
```

## 🧪 Testar o Workflow

### 1. Cadastrar Monitor na API

```bash
curl -X POST http://localhost:8080/api/monitors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Meu Monitor",
    "monitoredEmail": "seu-email@gmail.com",
    "senderFilter": "remetente@exemplo.com",
    "whatsappNumber": "5511999999999"
  }'
```

### 2. Enviar Email de Teste

Envie um email de `remetente@exemplo.com` para `seu-email@gmail.com`

### 3. Verificar

- n8n deve capturar o email
- API deve processar e retornar dados
- WhatsApp deve receber a mensagem

## 📱 Provedores WhatsApp

### Evolution API (Recomendado)
- **Site:** https://evolution-api.com
- **Vantagens:** Gratuito, fácil, sem limites
- **Deploy:** Docker, VPS, Railway

### Twilio
- **Site:** https://www.twilio.com
- **Vantagens:** Oficial, confiável
- **Desvantagens:** Pago, requer aprovação

### Baileys (Avançado)
- **GitHub:** https://github.com/WhiskeySockets/Baileys
- **Vantagens:** Gratuito, completo
- **Desvantagens:** Requer conhecimento técnico

## 🔍 Troubleshooting

### Email não está sendo capturado
- Verifique credenciais IMAP
- Confirme que "Acesso a apps menos seguros" está ativo (Gmail)
- Use senha de app em vez da senha normal

### Webhook retorna "ignored"
- Verifique se o monitor está ativo
- Confirme que email e remetente correspondem exatamente
- Veja logs da API

### WhatsApp não recebe mensagem
- Teste a API do WhatsApp separadamente
- Verifique formato do número (com DDI)
- Confirme que a instância está conectada

## 📊 Monitoramento

### Ver logs da API
```bash
# No terminal onde a API está rodando
# Logs aparecem automaticamente
```

### Ver histórico de notificações
```bash
curl http://localhost:8080/api/notifications
```

### Console H2
- URL: http://localhost:8080/h2-console
- Veja tabelas `email_monitors` e `notifications`

## 🚀 Produção

### Deploy da API
- Heroku, Railway, AWS, Azure
- Configure variáveis de ambiente
- Use PostgreSQL em vez de H2

### n8n em Produção
- n8n Cloud (https://n8n.cloud)
- Self-hosted (Docker)
- Configure webhooks públicos

### Segurança
- Adicione autenticação JWT
- Use HTTPS
- Valide webhooks com assinatura
- Rate limiting

## 💡 Casos de Uso

1. **E-commerce:** Notificações de vendas
2. **Suporte:** Tickets urgentes
3. **Financeiro:** Alertas bancários
4. **Monitoramento:** Alertas de sistema
5. **Marketing:** Leads importantes
