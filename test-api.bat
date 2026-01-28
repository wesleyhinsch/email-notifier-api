@echo off
chcp 65001 >nul
echo ========================================
echo   Email Notifier API - Testes
echo ========================================
echo.

echo [1] Criando monitor de email...
curl -X POST http://localhost:8080/api/monitors ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Alertas Vendas\",\"monitoredEmail\":\"meuemail@gmail.com\",\"senderFilter\":\"vendas@loja.com\",\"whatsappNumber\":\"5511999999999\"}"
echo.
echo.

timeout /t 2 >nul

echo [2] Listando todos os monitores...
curl http://localhost:8080/api/monitors
echo.
echo.

timeout /t 2 >nul

echo [3] Simulando recebimento de email (webhook)...
curl -X POST http://localhost:8080/api/webhook/email-received ^
  -H "Content-Type: application/json" ^
  -d "{\"from\":\"vendas@loja.com\",\"to\":\"meuemail@gmail.com\",\"subject\":\"Nova venda realizada!\",\"body\":\"Você vendeu 1x Produto XYZ por R$ 150,00\"}"
echo.
echo.

timeout /t 2 >nul

echo [4] Consultando histórico de notificações...
curl http://localhost:8080/api/notifications
echo.
echo.

echo ========================================
echo   Testes concluídos!
echo ========================================
pause
