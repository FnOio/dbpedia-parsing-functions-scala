# # Scala and sbt Dockerfile 
# # https://github.com/hseeberger/scala-sbt 



# # Pull base image 
FROM openjdk:8 
ENV SCALA_VERSION 2.12.1 
ENV SBT_VERSION 0.13.13 

EXPOSE 8080

# Scala expects this file 
RUN touch /usr/lib/jvm/java-8-openjdk-amd64/release

RUN update-ca-certificates -f

# Install Scala ## Piping curl directly in tar 
RUN \
  curl -fsL http://downloads.typesafe.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.tgz | tar xfz - -C /root/ && \
  echo >> /root/.bashrc && \
  echo 'export PATH=~/scala-$SCALA_VERSION/bin:$PATH' >> /root/.bashrc
# Install sbt 
RUN \
  curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion


# Clones the ontology server from the github repository
RUN git clone https://github.com/wmaroy/ontology-server

# Define working directory 
WORKDIR /ontology-server

# Starts the server (default is port 8080)
CMD ./sbt jetty:start jetty:join
