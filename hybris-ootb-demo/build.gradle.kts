import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "3.2.0" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
}

allprojects {
    group = "com.hybris"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.0")
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.slf4j:slf4j-api")
        compileOnly("org.projectlombok:lombok:1.18.30")
        annotationProcessor("org.projectlombok:lombok:1.18.30")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

// hybriscore: library module — no Spring Boot fat jar
project(":hybriscore") {
    tasks.named<Jar>("jar") { enabled = true }
}

// hybriscommercefacades: library module
project(":hybriscommercefacades") {
    tasks.named<Jar>("jar") { enabled = true }
    dependencies {
        implementation(project(":hybriscore"))
    }
}

// hybrisocc: library module
project(":hybrisocc") {
    tasks.named<Jar>("jar") { enabled = true }
    dependencies {
        implementation(project(":hybriscore"))
        implementation(project(":hybriscommercefacades"))
    }
}

// hybrisstorefront: runnable Spring Boot application
project(":hybrisstorefront") {
    apply(plugin = "org.springframework.boot")

    dependencies {
        implementation(project(":hybriscore"))
        implementation(project(":hybriscommercefacades"))
        implementation(project(":hybrisocc"))
    }

    tasks.named<BootJar>("bootJar") {
        enabled = true
        mainClass.set("com.hybris.storefront.HybrisApplication")
        archiveFileName.set("hybris-ootb-demo.jar")
    }
}
