FROM mcr.microsoft.com/devcontainers/java:1-17-bullseye

# Install Gradle 8.7
RUN wget https://services.gradle.org/distributions/gradle-8.7-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-8.7-bin.zip \
    && ln -s /opt/gradle/gradle-8.7/bin/gradle /usr/local/bin/gradle \
    && rm /tmp/gradle-8.7-bin.zip

ENV GRADLE_HOME=/opt/gradle/gradle-8.7
ENV PATH=$PATH:$GRADLE_HOME/bin

WORKDIR /workspace