# Use an official Java runtime as a parent image
FROM openjdk:8-jdk

# Set environment variables for Android SDK installation
ENV ANDROID_SDK_URL="https://dl.google.com/android/repository/commandlinetools-linux-6858069_latest.zip" \
    ANDROID_HOME="/opt/android-sdk" \
    PATH="$PATH:/opt/android-sdk/tools/bin:/opt/android-sdk/platform-tools:/opt/android-sdk/cmdline-tools/tools/bin"

# Install required dependencies
RUN apt-get update && \
    apt-get install -y wget unzip && \
    rm -rf /var/lib/apt/lists/*

# Install Android SDK
RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    wget -q ${ANDROID_SDK_URL} -O /tmp/sdk.zip && \
    unzip /tmp/sdk.zip -d ${ANDROID_HOME}/cmdline-tools && \
    rm /tmp/sdk.zip

# Install SDK packages
RUN yes | sdkmanager --licenses && \
    sdkmanager "platform-tools" "platforms;android-33" "build-tools;33.0.0"

# Install Gradle
ENV GRADLE_VERSION=7.0.3
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip \
    && ln -s /opt/gradle/gradle-${GRADLE_VERSION} /opt/gradle/latest \
    && rm /tmp/gradle-${GRADLE_VERSION}-bin.zip
ENV PATH=/opt/gradle/latest/bin:$PATH

# Set the working directory in the container
WORKDIR /app

# Copy the project files to the container
COPY . .

# Clean and build the project
RUN gradle clean build

# Define the command to run the application
CMD ["gradle", "assembleDebug"]
