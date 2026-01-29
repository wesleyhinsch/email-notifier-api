# 🚀 Deploy Rápido - Email Notifier API

## ✅ Passo 1: Configurar Secrets no GitHub

### Acesse: https://github.com/wesleyhinsch/email-notifier-api/settings/secrets/actions

### Secret 1: ORACLE_HOST
```
Name: ORACLE_HOST
Value: 204.216.143.125
```

### Secret 2: ORACLE_SSH_KEY
```
Name: ORACLE_SSH_KEY
Value: (cole a chave SSH completa que mostrei anteriormente)
```

---

## ✅ Passo 2: Fazer Deploy

```bash
# Adicionar mudanças
git add .

# Commit
git commit -m "deploy: configurar para Oracle Linux"

# Push (vai disparar o deploy automático)
git push
```

---

## 📊 Acompanhar Deploy

1. Acesse: https://github.com/wesleyhinsch/email-notifier-api/actions
2. Veja o workflow rodando
3. Aguarde ~2 minutos
4. ✅ Deploy concluído!

---

## 🌐 Testar API

Após deploy, acesse:
```
http://204.216.143.125:8080
```

Testar endpoint:
```bash
curl http://204.216.143.125:8080/api/monitors
```

---

## 🔧 Comandos Úteis

### Conectar na VM:
```bash
ssh -i %USERPROFILE%\Downloads\ssh-key-2026-01-29.key opc@204.216.143.125
```

### Ver logs da aplicação:
```bash
ssh -i %USERPROFILE%\Downloads\ssh-key-2026-01-29.key opc@204.216.143.125 "sudo journalctl -u email-notifier -f"
```

### Ver status:
```bash
ssh -i %USERPROFILE%\Downloads\ssh-key-2026-01-29.key opc@204.216.143.125 "sudo systemctl status email-notifier"
```

---

## ⚠️ Importante

Se a porta 8080 não estiver acessível externamente:

1. **Configurar Security List no Oracle Cloud:**
   - Acesse: Networking → Virtual Cloud Networks → vcn-email-notifier
   - Security Lists → Default Security List
   - Add Ingress Rule:
     - Source CIDR: 0.0.0.0/0
     - Destination Port: 8080
     - Protocol: TCP

2. **Firewall já está configurado no workflow!**
   - O deploy automático já abre a porta 8080

---

## 🎯 Checklist

- [ ] ORACLE_HOST = 204.216.143.125
- [ ] ORACLE_SSH_KEY configurada
- [ ] git push executado
- [ ] Workflow concluído com sucesso
- [ ] API acessível em http://204.216.143.125:8080
