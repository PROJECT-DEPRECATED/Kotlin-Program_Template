plugins {
    kotlin("jvm") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    `maven-publish`
}

group = properties["programGroup"]!!
version = properties["programVersion"]!!

repositories {
    mavenCentral()

    jcenter()
    maven("https://jitpack.io/")
}

dependencies {
    implementation(kotlin("stdlib"))
}

tasks {
    javadoc {
        options.encoding = "UTF-8"
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    create<Jar>("sourceJar") {
        archiveClassifier.set("source")
        sourceSets["main"].allSource
    }

    jar {
        manifest {
            attributes["Main-Class"] = project.properties["programMain"]!!
        }
    }

    shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            artifact(tasks["sourceJar"])
            from(components["java"])
        }
    }
}
