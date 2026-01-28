# 🧪 Teste Local - Email Notifier API

## ✅ Pré-requisitos

- Java 17+ instalado
- Maven instalado (ou usar `mvnw`)

Verificar:
```bash
java -version
mvn -version
```

---

## 🚀 Passo 1: Iniciar a API

### Opção A: Modo Desenvolvimento (Rápido)
```bash
run-dev.bat
```

### Opção B: Modo Produção (Compilado)
```bash
run.bat
```

**Aguarde até ver:**
```
Started EmailNotifierApplication in X.XXX seconds
```

✅ **API rodando:** http://localhost:8080
✅ **Console H2:** http://localhost:8080/h2-console

---

## 🧪 Passo 2: Testar Endpoints

### 1. Criar um Monitor

**PowerShell:**
```powershell
$body = @{
    name = "Monitor Teste"
    monitoredEmail = "joao@gmail.com"
    senderFilter = "vendas@loja.com"
    whatsappNumber = "5511999999999"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/monitors" -Method POST -Body $body -ContentType "application/json"
```

**CMD (curl):**
```bash
curl -X POST http://localhost:8080/api/monitors -H "Content-Type: application/json" -d "{\"name\":\"Monitor Teste\",\"monitoredEmail\":\"joao@gmail.com\",\"senderFilter\":\"vendas@loja.com\",\"whatsappNumber\":\"5511999999999\"}"
```

**Resposta esperada:**
```json
{
  "id": 1,
  "name": "Monitor Teste",
  "monitoredEmail": "joao@gmail.com",
  "senderFilter": "vendas@loja.com",
  "whatsappNumber": "5511999999999",
  "active": true,
  "createdAt": "2024-01-15T10:30:00"
}
```

---

### 2. Listar Monitores

**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/monitors" -Method GET
```

**CMD:**
```bash
curl http://localhost:8080/api/monitors
```

---

### 3. Simular Webhook (Email Recebido)

**PowerShell:**
```powershell
$webhook = @{
    from = "vendas@loja.com"
    to = "joao@gmail.com"
    subject = "Nova venda realizada!"
    body = "Você vendeu um produto por R$ 150,00"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/webhook/email-received" -Method POST -Body $webhook -ContentType "application/json"
```

**CMD:**
```bash
curl -X POST http://localhost:8080/api/webhook/email-received -H "Content-Type: application/json" -d "{\"from\":\"vendas@loja.com\",\"to\":\"joao@gmail.com\",\"subject\":\"Nova venda!\",\"body\":\"Você vendeu um produto\"}"
```

**Resposta esperada (SUCESSO):**
```json
{
  "status": "success",
  "whatsappNumber": "5511999999999",
  "message": "📧 *Novo Email Recebido*\n\n👤 *De:* vendas@loja.com\n📩 *Assunto:* Nova venda!\n\n💬 *Mensagem:*\nVocê vendeu um produto"
}
```

**Resposta (email não monitorado):**
```json
{
  "status": "ignored",
  "reason": "No active monitor found for this email combination"
}
```

---

### 4. Ver Histórico de Notificações

**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/notifications" -Method GET
```

**CMD:**
```bash
curl http://localhost:8080/api/notifications
```

---

### 5. Desativar Monitor

**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/monitors/1/toggle" -Method PATCH
```

**CMD:**
```bash
curl -X PATCH http://localhost:8080/api/monitors/1/toggle
```

---

## 📊 Acessar Banco de Dados H2

1. Abra: http://localhost:8080/h2-console
2. Configure:
   - **JDBC URL:** `jdbc:h2:file:./data/emailnotifier`
   - **User:** `sa`
   - **Password:** (deixe vazio)
3. Clique em **Connect**

**Consultas úteis:**
```sql
-- Ver todos monitores
SELECT * FROM EMAIL_MONITORS;

-- Ver todas notificações
SELECT * FROM NOTIFICATIONS;

-- Ver notificações recentes
SELECT * FROM NOTIFICATIONS ORDER BY SENT_AT DESC LIMIT 10;
```

---

## 🎯 Teste Completo Automatizado

Execute:
```bash
test-api.bat
```

Isso vai:
1. ✅ Criar um monitor
2. ✅ Listar monitores
3. ✅ Simular webhook
4. ✅ Ver notificações
5. ✅ Desativar monitor

---

## 🐛 Troubleshooting

### Erro: "Port 8080 already in use"
```bash
# Encontrar processo usando porta 8080
netstat -ano | findstr :8080

# Matar processo (substitua PID)
taskkill /PID <PID> /F
```

### Erro: "JAVA_HOME not found"
```bash
# Verificar Java
java -version

# Configurar JAVA_HOME (Windows)
setx JAVA_HOME "C:\Program Files\Java\jdk-17"
```

### Erro: "mvn not found"
```bash
# Baixar Maven: https://maven.apache.org/download.cgi
# Ou usar wrapper incluído:
mvnw.cmd spring-boot:run
```

### Banco de dados corrompido
```bash
# Deletar banco e reiniciar
rmdir /s /q data
run-dev.bat
```

---

## 📱 Próximos Passos

1. ✅ API funcionando localmente
2. 🔄 Configurar n8n (ver N8N_SETUP.md)
3. 📲 Configurar WhatsApp API
4. ☁️ Deploy na nuvem (Railway/Render)

---

## 💡 Dicas

- Use **PowerShell** para testes mais fáceis
- Mantenha o console H2 aberto para debug
- Logs aparecem no terminal da API
- Dados ficam salvos em `./data/`

**Tudo funcionando?** Configure o n8n seguindo `N8N_SETUP.md` 🚀
