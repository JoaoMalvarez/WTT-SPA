@echo off
setlocal
echo Configurando o ambiente portavel...

:: 1. Deteta automaticamente a pasta onde o .bat está guardado (ex: E:\wtt\ ou C:\MeusProjetos\wtt\)
set "BASE_DIR=%~dp0"

:: Remove a barra final para evitar caminhos com barra dupla
if "%BASE_DIR:~-1%"=="\" set "BASE_DIR=%BASE_DIR:~0,-1%"

:: 2. Define caminhos relativos à pasta do script
set "NODE_DIR=%BASE_DIR%\portables\nodejs"
set "SPA_DIR=%BASE_DIR%\wttspa"
set "VSCODE_EXE=%BASE_DIR%\portables\vscode\Code.exe"

:: 3. Adiciona o Node.js ao PATH da sessão atual
set "PATH=%NODE_DIR%;%PATH%"

:: 4. Configura o NPM para manter as definições e cache portáteis
set "NPM_CONFIG_PREFIX=%NODE_DIR%"
set "NPM_CONFIG_CACHE=%NODE_DIR%\npm-cache"

:: 5. Instala as dependências do projeto SPA
echo Instalando dependencias do wttspa...
call npm install --prefix "%SPA_DIR%"

:: 6. Inicia o VS Code abrindo a pasta principal e todas as subpastas
echo Abrindo VS Code em %BASE_DIR%...
if exist "%VSCODE_EXE%" (
    start "" "%VSCODE_EXE%" "%BASE_DIR%" --user-data-dir "%BASE_DIR%\portables\vscode\data" --extensions-dir "%BASE_DIR%\portables\vscode\data\extensions"
) else (
    echo [ERRO] Executavel do VS Code nao encontrado em: "%VSCODE_EXE%"
)

echo Concluido!
pause