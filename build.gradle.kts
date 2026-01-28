plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.github.sobczakartur"
version = "0.0.1-SNAPSHOT"
description = "Proxy API that returns non-fork GitHub repositories with branch and commit information."

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

repositories {
	mavenCentral()
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation ("com.fasterxml.jackson.core:jackson-databind")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:4.1.4")
	testImplementation("org.apache.commons:commons-lang3:3.18.0")
	testImplementation("com.github.tomakehurst:wiremock-jre8-standalone:2.35.1")
	testImplementation("org.springframework.boot:spring-boot-test-autoconfigure")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
