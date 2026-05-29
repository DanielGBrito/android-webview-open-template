# Website-to-Android Jetpack Compose Boilerplate (Shell WebView Avançado)

Este é um projeto Android moderno desenvolvido em **Kotlin** e **Jetpack Compose** (Material 3) desenvolvido para encapsular (embedar) qualquer site responsivo/PWA em um aplicativo nativo robusto, profissional e pronto para produção.


---

## 🚀 Principais Recursos

-   **⚡ Splash Screen Animada em Compose**: Tela de carregamento integrada com logotipo, nome do app, subtítulo, indicador circular e animações de fade-in/fade-out.
-   **🔌 Detecção Offline com Tela Amigável**: Tela de erro de conexão criada em Jetpack Compose com botão de reteste, evitando a clássica tela branca de erro do WebView quando o usuário perde o acesso à internet.
-   **📂 Uploads via HTML5 File Choosers**: Integração com o seletor nativo do Android para campos de upload do WebView, incluindo suporte a seleção única ou múltipla quando solicitado pelo site.
-   **📥 Downloads com Android DownloadManager**: Permite iniciar downloads disparados pelo site em segundo plano, salvando na pasta pública de Downloads e exibindo notificação do sistema ao concluir.
-   **🍪 Cookies e Armazenamento WebView**: Configuração de `CookieManager`, cookies de terceiros, DOM Storage e database storage para manter sessões e recursos web de forma compatível com o WebView.
-   **📱 Edge-to-Edge Integrado**: Uso de `enableEdgeToEdge()` e layout em tela cheia para ocupar 100% da área disponível do dispositivo.
-   **🔗 Navegação Nativa Inteligente**: Links do domínio configurado permanecem dentro do app, enquanto links externos são abertos no navegador padrão do usuário.
-   **⬅️ Botão Voltar Integrado**: O botão voltar do Android navega pelo histórico do WebView quando houver páginas anteriores carregadas.
-   **⚙️ Configuração por `.env`**: URL alvo, domínio interno, nome, subtítulo, package name, sufixo de User Agent e logotipo podem ser parametrizados sem alterar o código Kotlin.

---

## 🛠️ Como Customizar para o seu Próprio Site (Em 3 Passos)

Tudo que você precisa fazer para transformar este projeto no aplicativo oficial do seu próprio site é:

### 1. Alterar as Configurações Centrais no arquivo `.env`
Crie ou edite o arquivo `.env` na raiz do projeto (copiado de `.env.example`) e configure os parâmetros do seu site. O plugin de segredos do Gradle os compilará automaticamente para o aplicativo:

```properties
# Nome do aplicativo exibido no topo e nas telas do app
APP_NAME=Nome do Seu App

# Slogan ou descrição curta exibida na tela de splash
APP_SUBTITLE=Seu custom slogan ou descrição curta

# A URL do site ou PWA responsivo que o WebView irá carregar por padrão
TARGET_URL=https://seu-site.com.br/login

# ID único do aplicativo usado como package name na Play Store
APPLICATION_ID=com.suaempresa.seuapp

# O host/domínio principal do site para controlar redirecionamentos internos
TARGET_HOST=seu-site.com.br

# Sufixo customizado adicionado ao User Agent se necessário identificar o app no back-end
USER_AGENT_SUFFIX=Meu_App_Android_Shell/1.0

# Caminho da imagem do logotipo para a Splash Screen e o ícone adaptivo (Cópia automática em tempo de build)
# Formatos suportados: png, jpg, jpeg, webp.
# O script do Gradle limpa arquivos antigos e sincroniza este novo logo automaticamente!
APP_LOGO_PATH=app/src/main/res/drawable/custom_app_logo.png
```

Se preferir, o nome exibido na tela inicial do celular (Android launcher label) e as traduções amigáveis de mensagens offline continuam configuráveis em `/app/src/main/res/values/strings.xml`:

```xml
<resources>
    <!-- Identidade visual do launcher (gaveta de aplicativos) -->
    <string name="app_name">Nome do Seu App</string>
    <string name="app_subtitle">Sua Mensagem de Splash</string>
</resources>
```

### 2. Sincronizar o Logotipo Automaticamente
Basta colocar o arquivo de imagem da logo do seu app em uma pasta do projeto de sua preferência (por exemplo, na raiz) e apontar o caminho dela no parâmetro `APP_LOGO_PATH` do seu arquivo `.env`.

O script inteligente integrado no **Gradle** copiará, limpará formatos anteriores autodetectados (evitando duplicações de recursos na compilação do Android Asset Studio) e integrará a imagem diretamente na Splash Screen em Compose e nos ícones de launcher adaptivos automáticos!

### 3. Alterar o ID do Aplicativo (Package Name)
Defina o `APPLICATION_ID` no arquivo `.env`. O Gradle lê esse valor automaticamente e aplica no `applicationId` do app durante a compilação:

```properties
APPLICATION_ID=com.suaempresa.seuapp
```

O arquivo `/app/build.gradle.kts` já está preparado para buscar esse valor:

```kotlin
android {
    namespace = "com.example"
    compileSdk = 35

    defaultConfig {
        applicationId = envProps["APPLICATION_ID"] ?: "com.aistudio.webshell.app"
        // ...
    }
}
```

Se `APPLICATION_ID` não estiver definido no `.env`, o projeto usará `com.aistudio.webshell.app` como valor padrão.

---

## 📦 Como Compilar, Gerar e Instalar o APK

Após configurar o `.env`, o logotipo e o nome do app, você pode gerar um APK de teste instalável diretamente em um celular Android, sem precisar publicar na Play Store.

> O APK `debug` é suficiente para testes internos e instalação manual no seu próprio aparelho. Para distribuição oficial ou produção, gere uma build `release` assinada com uma chave própria.

### Pré-requisitos

Antes de compilar, confira se a máquina tem:

- **Android SDK** instalado.
- **JDK** instalado. O Android Studio já inclui um JDK em `C:\Program Files\Android\Android Studio\jbr`.
- **Gradle** disponível no terminal ou uma distribuição Gradle local.
- Arquivo `.env` configurado na raiz do projeto.

No Windows, se estiver usando o VS Code, abra o terminal integrado na pasta raiz do projeto.

### 1. Testar a build

Para verificar se os testes unitários/Robolectric rodam corretamente:

```bash
gradle :app:testDebugUnitTest
```

Se o projeto tiver os arquivos do Gradle Wrapper (`gradlew` ou `gradlew.bat`), você também pode usar:

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

### 2. Gerar o APK debug

Para gerar o APK instalável:

```bash
gradle :app:assembleDebug
```

Ou, no Windows com Gradle Wrapper:

```powershell
.\gradlew.bat :app:assembleDebug
```

Se o terminal retornar que `gradle` não é reconhecido, configure o `JAVA_HOME` e use o caminho completo do `gradle.bat` instalado na sua máquina:

```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
$env:ANDROID_SDK_ROOT = "$env:LOCALAPPDATA\Android\Sdk"
& "C:\caminho\para\gradle\bin\gradle.bat" :app:assembleDebug
```

No Android Studio, você também pode gerar o APK pelo menu:

```text
Build > Build Bundle(s) / APK(s) > Build APK(s)
```

### 3. Localizar o APK gerado

Ao final da compilação, o arquivo será criado em:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Esse é o arquivo que deve ser enviado ou instalado no celular.

### 4. Instalar manualmente no celular

Para instalar sem Play Store:

1. Envie o arquivo `app-debug.apk` para o celular.
2. Abra o arquivo no Android.
3. Autorize a instalação de apps desconhecidos quando o sistema solicitar.
4. Confirme a instalação.

### 5. Instalar via cabo USB com ADB

Se o celular estiver com **Opções do desenvolvedor** e **Depuração USB** ativadas, conecte o aparelho no computador e rode:

```powershell
adb devices
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

No Windows, caso o `adb` não esteja no `PATH`, use o caminho completo:

```powershell
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" devices
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" install -r "app/build/outputs/apk/debug/app-debug.apk"
```

### Observação sobre o nome do app

O nome exibido no launcher do Android vem de `app/src/main/res/values/strings.xml`. Mesmo que `APP_NAME` esteja configurado no `.env`, confira se os valores abaixo também foram ajustados:

```xml
<string name="app_name">Nome do Seu App</string>
<string name="app_subtitle">Sua Mensagem de Splash</string>
```

---

## 📄 Licença

Este projeto é open-source e está licenciado sob a licença **MIT** - você é livre para usar, modificar e distribuir este projeto para fins pessoais ou profissionais. O uso, a configuração e quaisquer consequências decorrentes da utilização do projeto são de responsabilidade do usuário. Consulte o arquivo [LICENSE](./LICENSE) para obter mais detalhes.
