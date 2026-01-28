# ☁️ Oracle Cloud - Deploy Gratuito

## 💰 100% GRÁTIS PARA SEMPRE

- ✅ 1GB RAM, 1 vCPU
- ✅ PostgreSQL incluído
- ✅ Sem cold start (roda 24/7)
- ✅ IP público fixo
- ⚠️ Precisa cartão (não cobra)

---

## 🚀 Passo 1: Criar Conta (10 min)

1. Acesse: https://cloud.oracle.com
2. **Sign Up** → Preencha dados
3. Adicione cartão (verificação, não cobra)
4. Aguarde aprovação

---

## 🖥️ Passo 2: Criar VM (15 min)

1. Menu ☰ → **Compute** → **Instances**
2. **Create Instance**

**Configurações:**
```
Name: email-notifier-api
Image: Ubuntu 22.04
Shape: VM.Standard.E2.1.Micro (Always Free)
VCN: Create new
Public IP: Yes
SSH Keys: Generate (BAIXE AS CHAVES!)
```

3. **Create**
4. Copie o **IP público**

---

## 🔥 Passo 3: Abrir Portas (5 min)

1. Menu ☰ → **Networking** → **Virtual Cloud Networks**
2. Clique na VCN → **Security Lists** → **Default**
3. **Add Ingress Rules**

**Adicionar 3 regras:**
```
Porta 80:   Source: 0.0.0.0/0, Protocol: TCP, Port: 80
Porta 443:  Source: 0.0.0.0/0, Protocol: TCP, Port: 443
Porta 8080: Source: 0.0.0.0/0, Protocol: TCP, Port: 8080
```

---

## 🔧 Passo 4: Configurar VM (20 min)

### Conectar via SSH

**Windows (PowerShell):**
```bash
ssh -i "C:\caminho\sua-chave.key" ubuntu@SEU_IP_PUBLICO
```

**Ou use PuTTY**

### Executar script de instalação

```bash
# Baixar script
wget https://raw.githubusercontent.com/SEU-USUARIO/email-notifier-api/main/oracle-install.sh

# Executar
chmod +x oracle-install.sh
./oracle-install.sh
```

O script vai instalar:
- Java 17
- PostgreSQL
- Nginx
- Firewall

---

## 📦 Passo 5: Enviar JAR (5 min)

**No seu PC, compile:**
```bash
mvn clean package -DskipTests
```

**Envie para VM:**

**Windows (PowerShell):**
```bash
scp -i "C:\caminho\sua-chave.key" target\email-notifier-api-1.0.0.jar ubuntu@SEU_IP:/home/ubuntu/app/
```

**Ou use WinSCP (GUI)**

---

## ▶️ Passo 6: Iniciar Aplicação (2 min)

**Na VM:**
```bash
# Iniciar
sudo systemctl start email-notifier

# Habilitar auto-start
sudo systemctl enable email-notifier

# Ver logs
sudo journalctl -u email-notifier -f
```

---

## 🧪 Passo 7: Testar (2 min)

**Do seu PC:**
```bash
# Criar monitor
curl -X POST http://SEU_IP_PUBLICO/api/monitors ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Teste\",\"monitoredEmail\":\"teste@gmail.com\",\"senderFilter\":\"sender@test.com\",\"whatsappNumber\":\"5511999999999\"}"

# Listar
curl http://SEU_IP_PUBLICO/api/monitors
```

Se retornar dados, está funcionando! ✅

---

## 📊 Comandos Úteis

```bash
# Ver logs
sudo journalctl -u email-notifier -f

# Reiniciar
sudo systemctl restart email-notifier

# Status
sudo systemctl status email-notifier

# Atualizar aplicação
sudo systemctl stop email-notifier
# (envie novo JAR)
sudo systemctl start email-notifier
```

---

## 🎯 Próximo Passo

Configure n8n: **N8N_SETUP.md**

Use URL: `http://SEU_IP_PUBLICO/api/webhook/email-received`

---

**Custo Total: R$ 0,00 para sempre!** 🎉
