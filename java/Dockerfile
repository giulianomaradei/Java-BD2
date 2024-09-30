# Usando a imagem base do Ubuntu
FROM ubuntu:20.04

# Definir variáveis de ambiente para evitar interações durante a instalação
ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=Etc/UTC

# Instalação de pacotes necessários e do OpenJDK 11
RUN apt-get update && apt-get install -y \
    openjdk-11-jdk \
    ant \
    aptitude \
    libderby-java \
    mysql-server \
    postgresql \
    tzdata \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

 COPY mysql-connector-java.deb /tmp/

 # Instalar o MySQL Connector/J
 RUN dpkg -i /tmp/mysql-connector-java.deb && \
     rm /tmp/mysql-connector-java.deb
# Definir o diretório de trabalho
WORKDIR /app

# Copiar os arquivos do projeto para o contêiner
COPY . .

# Configurar o PATH para incluir o diretório do Java
ENV PATH=$PATH:/usr/lib/jvm/java-11-openjdk-amd64/bin

# Configurar o fuso horário
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

CMD ["tail", "-f", "/dev/null"]