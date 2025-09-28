

interface InjectedExecOps {
    @get:Inject val execOps: ExecOperations
}

val envMap = mapOf(
    "AWS_ACCESS_KEY_ID" to "AKIAIOSFODNN7EXAMPLE",
    "AWS_SECRET_ACCESS_KEY" to "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY",
    "AWS_DEFAULT_REGION" to "us-east-1"
)

val injected = project.objects.newInstance<InjectedExecOps>()

tasks.register("docker.up") {
    doLast {
        injected.execOps.exec {
            commandLine("docker-compose", "up", "--detach")
        }
    }
}

tasks.register("docker.down") {
    doLast {
        injected.execOps.exec {
            commandLine("docker-compose", "down", "--volumes")
        }
    }
}

tasks.register("cdk.bootstrap") {
    doLast {
        injected.execOps.exec {
            environment(envMap)
            commandLine("cdklocal", "bootstrap")
        }
    }
    dependsOn("docker.up")
}

tasks.register("cdk.deploy") {
    doLast {
        injected.execOps.exec {
            environment(envMap)
            commandLine("cdklocal", "deploy", "--require-approval", "never", "--all", "--hotswap-fallback")
        }
    }
}

tasks.register("cdk.destroy") {
    doLast {
        injected.execOps.exec {
            environment(envMap)
            commandLine("cdklocal", "destroy", "--force", "--all")
        }
    }
}

tasks.register("up") {
    dependsOn("cdk.bootstrap", "cdk.deploy")
}

tasks.register("update") {
    dependsOn("cdk.deploy")
}

tasks.register("reset") {
    dependsOn("cdk.destroy", "cdk.bootstrap", "cdk.deploy")
}

tasks.register("down") {
    dependsOn("docker.down")
}