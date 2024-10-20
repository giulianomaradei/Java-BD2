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
    tzdata \
    wget \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

# Definir o diretório de trabalho
WORKDIR /app

# Copiar os arquivos do projeto para o contêiner
COPY . .

ENV PATH=$PATH:/usr/lib/jvm/java-11-openjdk-amd64/bin

ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

COPY postgresql-42.7.4.jar /app/postgresql-jdbc.jar
# Configurar variáveis de ambiente para o driver do PostgreSQL
ENV CLASSPATH=$CLASSPATH:/app/postgresql-jdbc.jar

CMD ["tail", "-f", "/dev/null"]