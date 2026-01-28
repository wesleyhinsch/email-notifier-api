# 📦 Git e GitHub - Setup Rápido

## 1. Configurar Git (primeira vez)

```bash
git config --global user.name "Seu Nome"
git config --global user.email "seu-email@gmail.com"
```

## 2. Inicializar Repositório

```bash
cd c:\repo\email-notifier-api
git init
git add .
git commit -m "Initial commit"
```

## 3. Criar Repositório no GitHub

1. Acesse: https://github.com/new
2. Nome: `email-notifier-api`
3. Deixe tudo desmarcado
4. **Create repository**

## 4. Conectar e Enviar

```bash
# Substituir SEU-USUARIO pelo seu usuário GitHub
git remote add origin https://github.com/SEU-USUARIO/email-notifier-api.git
git branch -M main
git push -u origin main
```

**Primeira vez:** GitHub vai pedir login

## 5. Próximo Passo

Agora vá para: **GOOGLE-CLOUD-DEPLOY.md**
