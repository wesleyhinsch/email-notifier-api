# 🔑 Atualizar Secrets SSH no GitHub

## Secrets Necessárias

Seu workflow precisa de 2 secrets:
1. **ORACLE_SSH_KEY** - Chave privada SSH
2. **ORACLE_HOST** - IP público da VM

---

## 📝 Passo a Passo

### 1. Acessar Configurações de Secrets

Acesse: https://github.com/wesleyhinsch/email-notifier-api/settings/secrets/actions

### 2. Atualizar ORACLE_HOST

1. Clique em **ORACLE_HOST** (se já existe) ou **New repository secret**
2. **Name:** `ORACLE_HOST`
3. **Value:** Seu IP público da VM Oracle (ex: `123.45.67.89`)
4. Clique em **Update secret** ou **Add secret**

### 3. Atualizar ORACLE_SSH_KEY

#### Pegar a chave privada SSH:

**Windows (PowerShell):**
```powershell
# Substitua pelo caminho da sua chave
Get-Content C:\Users\SeuUsuario\.ssh\oracle-vm-key.key | clip
```

**Windows (CMD):**
```cmd
type C:\Users\SeuUsuario\.ssh\oracle-vm-key.key
```

**Copie TODO o conteúdo**, incluindo:
```
-----BEGIN OPENSSH PRIVATE KEY-----
...todo o conteúdo...
-----END OPENSSH PRIVATE KEY-----
```

#### Adicionar no GitHub:

1. Clique em **ORACLE_SSH_KEY** (se já existe) ou **New repository secret**
2. **Name:** `ORACLE_SSH_KEY`
3. **Value:** Cole o conteúdo completo da chave privada
4. Clique em **Update secret** ou **Add secret**

---

## ✅ Testar as Secrets

### 1. Fazer um commit de teste
```bash
git add .
git commit -m "test: verificar secrets SSH"
git push
```

### 2. Ver o workflow rodando
1. Acesse: https://github.com/wesleyhinsch/email-notifier-api/actions
2. Clique no workflow mais recente
3. Acompanhe os logs

### 3. Verificar se funcionou
- ✅ Build com Maven: deve compilar
- ✅ Setup VM and Deploy: deve conectar via SSH
- ✅ Deploy completed: aplicação reiniciada

---

## 🔍 Localizar sua Chave SSH

### Locais comuns:

**Windows:**
```
C:\Users\SeuUsuario\.ssh\id_rsa
C:\Users\SeuUsuario\.ssh\oracle-vm-key.key
C:\Users\SeuUsuario\Downloads\ssh-key-*.key
```

**Listar chaves SSH:**
```powershell
dir C:\Users\$env:USERNAME\.ssh\*.key
```

### Se não encontrar a chave:

**Opção 1: Gerar nova chave SSH**
```bash
ssh-keygen -t rsa -b 4096 -f C:\Users\SeuUsuario\.ssh\oracle-vm-key.key
```

**Opção 2: Baixar do Oracle Cloud**
1. Acesse Oracle Cloud Console
2. Compute → Instances → Sua VM
3. Veja se tem backup da chave

---

## 🐛 Troubleshooting

### Erro: "Permission denied (publickey)"

**Causa:** Chave SSH incorreta ou não corresponde à VM

**Solução:**
1. Teste a chave localmente:
```bash
ssh -i C:\caminho\sua-chave.key ubuntu@SEU_IP
```

2. Se não funcionar, você precisa:
   - Encontrar a chave correta
   - Ou adicionar nova chave pública na VM

### Erro: "Invalid format"

**Causa:** Chave copiada incorretamente

**Solução:**
- Copie TODO o conteúdo (incluindo BEGIN e END)
- Não adicione espaços ou quebras de linha extras
- Use `clip` no PowerShell para copiar direto

### Erro: "Connection refused"

**Causa:** IP incorreto ou VM desligada

**Solução:**
1. Verifique o IP público da VM no Oracle Cloud
2. Verifique se a VM está rodando
3. Teste: `ping SEU_IP`

---

## 📋 Checklist Final

- [ ] ORACLE_HOST configurado com IP correto
- [ ] ORACLE_SSH_KEY configurado com chave privada completa
- [ ] Chave SSH testada localmente
- [ ] Workflow executado com sucesso
- [ ] API acessível em http://SEU_IP:8080

---

## 💡 Dica de Segurança

**Nunca compartilhe sua chave privada SSH!**
- ✅ Adicione apenas no GitHub Secrets (privado)
- ❌ Não commite no repositório
- ❌ Não envie por email/chat
- ❌ Não poste em fóruns

---

## 🆘 Precisa de Ajuda?

Se continuar com problemas:

1. **Ver logs do workflow:**
   - GitHub → Actions → Clique no workflow → Ver cada step

2. **Testar SSH manualmente:**
```bash
ssh -i sua-chave.key ubuntu@SEU_IP "echo 'Conexão OK!'"
```

3. **Ver logs da aplicação na VM:**
```bash
ssh -i sua-chave.key ubuntu@SEU_IP "sudo journalctl -u email-notifier -n 50"
```
