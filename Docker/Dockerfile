FROM  ubuntu:16.04

# jdk
RUN apt-get update
RUN apt install -y openjdk-8-jdk

# Cassandra
RUN apt-get install -y ca-certificates curl apt-transport-https zip unzip git make
RUN echo "deb http://www.apache.org/dist/cassandra/debian 311x main" |  tee -a /etc/apt/sources.list.d/cassandra.sources.list
RUN curl https://downloads.apache.org/cassandra/KEYS |apt-key add -
RUN apt-get update
RUN apt-get install -y cassandra

RUN mkdir -p /data/cassandra/data
RUN mkdir -p /data/cassandra/commitlog
RUN mkdir -p /data/cassandra/hints
RUN mkdir -p /data/cassandra/saved_caches
RUN chown -R cassandra:cassandra /data/cassandra

# Env File
RUN mkdir /etc/scalar
COPY database.properties /etc/scalar/
COPY cassandra.yaml /etc/cassandra/
#RUN service cassandra start

# ScalarDB
WORKDIR /root
RUN git clone https://github.com/scalar-labs/scalardb.git
ENV SCALARDB_HOME /root/scalardb
WORKDIR /root/scalardb
RUN ./gradlew installDist
RUN mkdir /var/log/scalar
RUN chmod 777 /var/log/scalar
WORKDIR /root/scalardb/docs/getting-started
RUN curl -L https://github.com/scalar-labs/scalardb/releases/download/v2.4.1/scalar-schema-standalone-2.4.1.jar > scalar-schema-standalone-2.4.1.jar
ENV SCHEMATOOL /root/scalardb/docs/getting-started

#SHELL ["/bin/sh", "-c"]

WORKDIR /server
