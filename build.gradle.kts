plugins {
    id("java")
}

group = "com.chotchip"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {

    apply(plugin = "java")
    dependencies{
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
        implementation("org.springframework.boot:spring-boot-starter-web")
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

}

tasks.test {
    useJUnitPlatform()
}