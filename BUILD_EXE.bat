@echo off
chcp 65001 >nul
cd /d "%~dp0"
echo Установка зависимостей...
call npm install
if errorlevel 1 (echo Ошибка установки зависимостей.& pause & exit /b 1)
echo Сборка PixelForge.exe...
call npm run dist:win
if errorlevel 1 (echo Ошибка сборки.& pause & exit /b 1)
echo Готово: папка dist
pause
