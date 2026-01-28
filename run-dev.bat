@echo off
chcp 65001 >nul
echo ========================================
echo   Email Notifier API - Maven Run
echo ========================================
echo.
echo ✅ API rodando em: http://localhost:8080
echo 📊 Console H2: http://localhost:8080/h2-console
echo.
echo Pressione Ctrl+C para parar
echo.

mvn spring-boot:run
