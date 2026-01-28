@echo off
chcp 65001 >nul
echo ========================================
echo   Email Notifier API
echo ========================================
echo.

echo [1] Compilando projeto...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo.
    echo ❌ Erro na compilação!
    pause
    exit /b 1
)

echo.
echo [2] Iniciando aplicação...
echo.
echo ✅ API rodando em: http://localhost:8080
echo 📊 Console H2: http://localhost:8080/h2-console
echo.
echo Pressione Ctrl+C para parar
echo.

java -jar target\email-notifier-api-1.0.0.jar
