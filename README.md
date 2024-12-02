# TKDChampManager - Sistema de Controle de Campeonatos de Tae Kwon Do

O **TKDChampManager** é um sistema desenvolvido em Java para a gestão de campeonatos de Tae Kwon Do, com foco na precisão e justiça na contagem de pontos durante as lutas. Ele utiliza uma abordagem inovadora para marcar pontos baseada na concordância entre os juízes, tornando o processo mais transparente e confiável.

## 🚀 Funcionalidades Principais

- **Marcação de Pontos por Concordância:**  
  Quando um lutador executa um golpe válido, os juízes pressionam um botão em seus controles. Se a maioria dos juízes concordar dentro de um intervalo predeterminado, o ponto é computado automaticamente no placar.

- **Compatibilidade com Joysticks:**  
  O sistema suporta joysticks de videogames existentes, mas também permite a utilização de controles personalizados, dedicados à função de marcação de pontos.

- **Gestão de Lutas:**  
  Organização e registro de lutas, com suporte a múltiplos juízes para cada combate.

## 📋 Pré-requisitos

Para executar o sistema, você precisará:

- Java Development Kit (JDK) 8 ou superior.
- Uma máquina com sistema operacional compatível com Java (Windows, macOS ou Linux).
- Joysticks configurados e conectados ao sistema.

## 🛠️ Configuração e Execução

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/renatopinheirosouza/tkdchampmanager.git
   cd tkdchampmanager
   ```

2. **Compile o projeto:**

   ```bash
   javac -d bin src/*.java
   ```

3. **Execute o sistema:**

   ```bash
   java -cp bin Main
   ```

4. **Conecte os joysticks:**  
   Certifique-se de que os controles estejam conectados corretamente e configure o sistema para reconhecer os dispositivos.

## ✨ Contribuições

Este projeto é um legado de desenvolvimento e está aberto para melhorias e contribuições. Caso tenha sugestões ou correções, sinta-se à vontade para abrir uma [issue](https://github.com/renatopinheirosouza/tkdchampmanager/issues) ou enviar um [pull request](https://github.com/renatopinheirosouza/tkdchampmanager/pulls).

## 📝 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
