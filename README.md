# Android Embedded WebView Template

Template open source para transformar um site em um app Android simples usando Kotlin, Jetpack Compose e Android WebView.

## O que este template entrega

- URL do site configurada por `.env`
- Nome do app configurado por `.env`
- Logo de splash configuravel por JPEG ou PNG
- Splash screen nativa antes do carregamento do site
- WebView com JavaScript, cookies, storage, upload de arquivos e downloads
- Tela de erro offline com acao de tentar novamente
- Links externos abertos fora do app quando o host nao esta permitido

## Requisitos

- Android Studio
- JDK compativel com o Android Gradle Plugin do projeto

## Como usar

1. Clone ou copie este template.
2. Crie o arquivo `.env` na raiz do projeto:

```bash
cp .env.example .env
```

3. Edite o `.env` com os dados do seu app:

```properties
APP_NAME=Minha Empresa
APP_ID=br.com.minhaempresa.app
WEBVIEW_URL=https://minhaempresa.com.br
WEBVIEW_ALLOWED_HOSTS=cdn.minhaempresa.com.br,checkout.minhaempresa.com.br
SPLASH_SUBTITLE=Portal oficial
USER_AGENT_SUFFIX=MinhaEmpresaAndroid/1.0
APP_LOGO_PATH=branding/logo.jpg
ALLOW_CLEARTEXT_TRAFFIC=false
```

4. Coloque seu logo no caminho informado em `APP_LOGO_PATH`.

O logo pode ser `.jpg`, `.jpeg` ou `.png`. Se `APP_LOGO_PATH` ficar vazio ou apontar para um arquivo inexistente, o app usa o logo padrao do template.

5. Abra o projeto no Android Studio e execute o app.

## Variaveis do `.env`

| Variavel | Descricao |
| --- | --- |
| `APP_NAME` | Nome exibido no launcher e na splash screen. |
| `APP_ID` | `applicationId` Android. Deve ser unico para publicar o app. |
| `WEBVIEW_URL` | URL inicial carregada dentro do WebView. |
| `WEBVIEW_ALLOWED_HOSTS` | Hosts extras que continuam abrindo dentro do app. |
| `SPLASH_SUBTITLE` | Texto secundario exibido na splash screen. |
| `USER_AGENT_SUFFIX` | Sufixo adicionado ao user agent do WebView. |
| `APP_LOGO_PATH` | Caminho relativo para o logo JPEG/PNG. |
| `ALLOW_CLEARTEXT_TRAFFIC` | Use `true` apenas para sites HTTP ou recursos sem HTTPS. |

## Build

O projeto ja inclui Gradle Wrapper configurado para Gradle 9.3.1. Pelo Android Studio, use **File > Sync Project with Gradle Files** e depois **Build > Make Project**.

Pelo terminal:

```bash
./gradlew assembleDebug
```

No Windows:

```powershell
.\gradlew.bat assembleDebug
```

## Publicacao

Antes de publicar na Play Store, configure uma chave de release propria e remova qualquer artefato local que nao deva ir para o repositorio. O template ja ignora `.env`, APKs, AABs e keystores comuns.

## Licenca

Este projeto usa a licenca MIT.

Antes de publicar o seu app ou repositorio, abra o arquivo `LICENSE` e substitua esta linha:

```text
Copyright (c) 2026
```

Por uma linha com o nome do autor, empresa ou organizacao responsavel pelo projeto:

```text
Copyright (c) 2026 Sua Empresa
```
