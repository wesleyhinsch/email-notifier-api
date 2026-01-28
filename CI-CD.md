# 🔄 CI/CD Automático - GitHub Actions

## O que faz

Toda vez que você fizer `git push` na branch `main`:
1. ✅ Compila o projeto
2. ✅ Envia JAR para Oracle Cloud
3. ✅ Reinicia aplicação
4. ✅ Deploy automático em ~2 minutos

---

## 📋 Configurar (Uma vez só)

### 1. Adicionar Secrets no GitHub

1. Acesse: https://github.com/wesleyhinsch/email-notifier-api/settings/secrets/actions
2. **New repository secret**

**Secret 1:**
```
Name: ORACLE_HOST
Value: SEU_IP_PUBLICO_DA_VM
```

**Secret 2:**
```
Name: ORACLE_SSH_KEY
Value: (cole o conteúdo da sua chave privada SSH)
```

Para pegar a chave SSH:
```bash
# Windows
type C:\caminho\sua-chave.key

# Copie TODO o conteúdo (incluindo BEGIN e END)
```

---

## ✅ Testar

### 1. Fazer uma mudança
```bash
# Edite qualquer arquivo
notepad README.md

# Commit e push
git add .
git commit -m "Teste CI/CD"
git push
```

### 2. Ver deploy
1. Acesse: https://github.com/wesleyhinsch/email-notifier-api/actions
2. Veja o workflow rodando
3. Aguarde ~2 minutos
4. Deploy concluído! ✅

---

## 🔄 Workflow

```
git push
    ↓
GitHub Actions detecta
    ↓
Compila projeto (Maven)
    ↓
Envia JAR para Oracle Cloud
    ↓
Reinicia aplicação
    ↓
Deploy concluído! 🎉
```

---

## 🐛 Troubleshooting

### Deploy falhou

**Ver logs:**
1. GitHub → Actions → Clique no workflow
2. Veja qual step falhou

**Erros comuns:**

**"Permission denied"**
- Verifique se a chave SSH está correta
- Teste: `ssh -i sua-chave.key ubuntu@SEU_IP`

**"Connection refused"**
- Verifique se o IP está correto
- Verifique se a VM está rodando

**"systemctl restart failed"**
- Conecte na VM: `ssh ubuntu@SEU_IP`
- Veja logs: `sudo journalctl -u email-notifier -n 50`

---

## 💡 Dicas

### Deploy manual (se precisar)
```bash
# Compilar
mvn clean package -DskipTests

# Enviar
scp -i sua-chave.key target/email-notifier-api-1.0.0.jar ubuntu@SEU_IP:/home/ubuntu/app/

# Reiniciar
ssh -i sua-chave.key ubuntu@SEU_IP "sudo systemctl restart email-notifier"
```

### Ver status do deploy
```bash
ssh -i sua-chave.key ubuntu@SEU_IP "sudo systemctl status email-notifier"
```

---

## ✅ Pronto!

Agora todo `git push` faz deploy automático! 🚀
