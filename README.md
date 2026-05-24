# Website-to-Android Jetpack Compose Boilerplate (Shell WebView Avançado)

Este é um projeto Android moderno desenvolvido em **Kotlin** e **Jetpack Compose** (Material 3) desenvolvido para encapsular (embedar) qualquer site responsivo/PWA em um aplicativo nativo robusto, profissional e pronto para produção.

Originalmente projetado para a rede social corporativa **GP Social** (da Grupo Pereira), a arquitetura deste projeto foi totalmente descentralizada e parametrizada para que qualquer pessoa ou empresa possa usar este mesmo código-fonte para colocar seu próprio site na Google Play Store em minutos!

---

## 🚀 Principais Recursos

-   **⚡ Splash Screen Animada**: Tela de carregamento integrada desenvolvida com Jetpack Compose, animações de fade-out e indicador de progresso fluído.
-   **🔌 Detecção Offline com Tela Amigável**: Tela nativa de erro de conexão com botão de reteste que evita a clássica tela branca de erro do navegador Chrome quando o usuário perde o acesso à internet.
-   **📂 Suporte Completo a Uploads (HTML5 File Choosers)**: Integração robusta com o selecionador nativo para upload de arquivos, fotos e anexos.
-   **📥 Gerenciador de Downloads Integrado (Android DownloadManager)**: Permite que os usuários baixem anexos, fotos ou PDFs diretamente do site em segundo plano no dispositivo móvel com notificações do sistema.
-   **🍪 Persistência Avançada de Cookies**: Configuração otimizada com `CookieManager` garantindo que os usuários permaneçam logados no aplicativo mesmo se reiniciarem o aparelho.
-   **📱 Edge-to-Edge Integrado**: Renderização fluída ocupando 100% da tela do usuário, respeitando a barra de status e barra de navegação virtual de forma natural.

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

O script inteligente integrado no **Gradle** copiará, limpará formatos anteriores autodetectados (evitando duplicações de recursos na compilação do Android Asset Studio) e integrará a imagem diretamente na Splash Screen nativa e nos ícones de launcher adaptivos automáticos!

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

## 📦 Como Compilar e Gerar o APK

Após configurar os itens acima, você pode rodar os testes nativos e compilar o projeto para gerar um APK pronto para instalar ou o pacote Bundle (.aab) para envio à Play Store:

Para verificar se a build e os testes unitários/Robolectric rodam perfeitamente:
```bash
gradle :app:testDebugUnitTest
```

Para gerar o APK oficial para teste:
```bash
gradle assembleDebug
```
O arquivo APK final será gerado no diretório `/app/build/outputs/apk/debug/app-debug.apk`.

---

## 📄 Licença

Este projeto é open-source e está licenciado sob a licença **MIT** - você é livre para usar, modificar e distribuir este projeto para fins pessoais ou profissionais. O uso, a configuração e quaisquer consequências decorrentes da utilização do projeto são de responsabilidade do usuário. Consulte o arquivo [LICENSE](./LICENSE) para obter mais detalhes.
