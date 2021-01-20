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

# Golang
RUN curl -L https://dl.google.com/go/go1.10.5.linux-amd64.tar.gz > go1.10.5.linux-amd64.tar.gz
RUN tar -C /usr/local -xzf go1.10.5.linux-amd64.tar.gz

SHELL ["/bin/bash", "-c"]
RUN curl -s "https://get.sdkman.io" | bash
RUN source /root/.sdkman/bin/sdkman-init.sh; sdk install gradle 4.10.2
ENV PATH $PATH:/usr/local/go/bin

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
RUN git checkout 1.1.0
RUN ./gradlew installDist
WORKDIR /root/scalardb/tools/schema
RUN ls
RUN make

ENV SCHEMATOOL /root/scalardb/tools/schema

#SHELL ["/bin/sh", "-c"]

WORKDIR /server
