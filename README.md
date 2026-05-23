# Android WebView Open Template

Template Android para publicar um site como aplicativo nativo usando Kotlin, Jetpack Compose e Android WebView.

O projeto foi desenhado para ser reutilizavel: a identidade do app, URL inicial, hosts permitidos e logo sao configurados por ambiente, sem alterar codigo-fonte.

## Principais Recursos

- WebView configurada para sites modernos com JavaScript, cookies e local storage.
- Configuracao por `.env` para nome do app, URL, identificador Android e identidade visual.
- Splash screen nativa com logo e texto do produto.
- Tratamento de estado offline com acao de recarregar.
- Upload de arquivos via seletor nativo Android.
- Download de arquivos via `DownloadManager`.
- Separacao entre template, configuracao local e artefatos de build.
- Gradle Wrapper versionado para garantir builds reproduziveis.

## Arquitetura

```text
App Android - Embedded/
├─ README.md
├─ .gitignore
└─ android-webview-open-template/
   ├─ .env.example
   ├─ build.gradle.kts
   ├─ gradle.properties
   ├─ gradlew
   ├─ gradlew.bat
   ├─ settings.gradle.kts
   ├─ LICENSE
   ├─ app/
   │  ├─ build.gradle.kts
   │  └─ src/
   │     ├─ main/
   │     ├─ test/
   │     └─ androidTest/
   └─ gradle/
      ├─ libs.versions.toml
      └─ wrapper/
```

O modulo `app` concentra a aplicacao Android. A configuracao de produto fica fora do codigo Kotlin, no arquivo `.env`, mantendo o template reutilizavel para diferentes apps.

## Configuracao

Crie um arquivo `.env` dentro de `android-webview-open-template`, ao lado de `.env.example`:

```powershell
Copy-Item ".env.example" ".env"
```

Exemplo:

```properties
APP_NAME=Minha Empresa
APP_ID=br.com.minhaempresa.app
WEBVIEW_URL=https://minhaempresa.com.br
WEBVIEW_ALLOWED_HOSTS=cdn.minhaempresa.com.br,checkout.minhaempresa.com.br
SPLASH_SUBTITLE=Portal oficial
USER_AGENT_SUFFIX=MinhaEmpresaAndroid/1.0
APP_LOGO_PATH=branding/logo.jpeg
ALLOW_CLEARTEXT_TRAFFIC=false
```

## Variaveis De Ambiente

| Variavel | Descricao |
| --- | --- |
| `APP_NAME` | Nome exibido no launcher e na splash screen. |
| `APP_ID` | Identificador unico do app Android. Deve seguir o padrao de dominio reverso. |
| `WEBVIEW_URL` | URL inicial carregada pela WebView. |
| `WEBVIEW_ALLOWED_HOSTS` | Lista de hosts adicionais que podem continuar abrindo dentro do app. |
| `SPLASH_SUBTITLE` | Texto secundario exibido na splash screen. |
| `USER_AGENT_SUFFIX` | Identificador adicionado ao user agent da WebView. |
| `APP_LOGO_PATH` | Caminho relativo para o logo em PNG, JPG ou JPEG. |
| `ALLOW_CLEARTEXT_TRAFFIC` | Define se o app aceita trafego HTTP sem TLS. Em producao, mantenha `false`. |

## Identidade Visual

A pasta `branding` ja existe dentro de `android-webview-open-template`. Coloque nela o logo do app:

```text
android-webview-open-template/
└─ branding/
   └─ logo.jpeg
```

Depois configure:

```properties
APP_LOGO_PATH=branding/logo.jpeg
```

Durante o build, o logo e copiado para recursos gerados em `app/build/generated`, sem modificar arquivos versionados em `src/main`.

## Build

O projeto inclui Gradle Wrapper configurado para Gradle 9.3.1.

No Android Studio:

1. Abra a pasta `android-webview-open-template`.
2. Execute **File > Sync Project with Gradle Files**.
3. Execute **Build > Make Project**.

No terminal Windows:

```powershell
cd "android-webview-open-template"
.\gradlew.bat assembleDebug
```

No macOS/Linux:

```bash
cd android-webview-open-template
./gradlew assembleDebug
```

## Seguranca

- Use `https://` em `WEBVIEW_URL`.
- Mantenha `ALLOW_CLEARTEXT_TRAFFIC=false` em producao.
- Liste apenas hosts confiaveis em `WEBVIEW_ALLOWED_HOSTS`.
- Nao versione `.env`, keystores, APKs ou AABs.
- Configure uma chave de assinatura propria para builds de release.

## Troubleshooting

Se o app abrir uma tela de erro ao carregar um site HTTPS, valide o certificado TLS do dominio. A WebView Android rejeita certificados expirados, autoassinados ou com cadeia incompleta.

Para ambientes internos ou homologacao, instale uma cadeia de certificados confiavel no dispositivo/emulador ou publique o site com um certificado emitido por uma autoridade reconhecida pelo Android. O template nao ignora erros SSL por padrao.

## Publicacao

Para publicar na Play Store, gere um Android App Bundle assinado pelo Android Studio em **Build > Generate Signed Bundle / APK**.

O `APP_ID` deve ser definido antes da primeira publicacao e mantido estavel nas proximas versoes.

## Licenca

MIT. Consulte `android-webview-open-template/LICENSE`.
