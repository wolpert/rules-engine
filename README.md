# Rules Engine

Started project from the basic server template.

Goal of the project is to have a scalable backend rules engine with
velocity features that supports a multi-tenant system, dynamic rules
and unstructured data.

The actual rules engine will be drools. The velocity service an internal
distributed counter with windows. Intended for AWS deployment and uses
DynamoDB for the velocity service and configuration. Rules are stored
in S3.

The project uses localstack for testing, with services running in ECS or
lambda. The control plan is and data plan are separate ECS services.
A front-end for management is lower-priority. (Because I hate doing UIs.)

## Execution path

### Execution Environment

The application runs within a given execution environment. These can be pre-prod,
like dev or QA; where pre-prod means environments for the development of the rule
engine. They can be production environments like Sandbox or Product, or even 
regionalize production. These environments are used by those managing rule executions.

Rules can be migrated between environments.

### Rule Management

Rules are available at the global level, tenant and event. Rules are versioned and only
one version can be active at a time in a given execution environment.  Rules can be exported
or imported into environments, and enabled or run in shadow mode. They exist in as versions
in an environment.

### Rule Engine Execution

A rule set is a given set of rules of the three types; global, tenant and event. The types of
rules exist within a given version. Requests can be made against a RuleSet with a given set
of facts. Those requests are executed with creates a result. Executions can be 'live' or 'shadow'. 
Shadow results do not modify the datastore for the tenant/events, which is the history of events
and the velocity setup for them.

### Velocity

Rule executions can result in an update to a datastore for the rules themselves. 

# Testing

Running tests normally will only execute the unit tests.

If you want to run the integration tests, you need docker installed, and cdk/cdk-local 
both installed. As in

```bash
npm install -g aws-cdk
npm install -g aws-cdk-local
````

To finish the local installation, in the cdk directory, run

```bash
npm install
````

Then, to run the integration tests, run in the top-level directory:

```bash
gradle test -Pinteg
```

# License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details.

# Code Rules

* Use Junit5 for testing
* Use AssertJ for assertions
* Mock external dependencies with Mockito
* Structure tests with Given-When-Then format

## Unit tests

* Use Mockito Extensions for cleaner mock initialization, such as @ExtendWith(MockitoExtension.class), @Mock and @Captor, @BeforeEach, @BeforeAll, etc
* Name test methods with descriptive names using underscores to separate words

## Integration tests

* Upload data into the s3 bucket before running tests. Can be done as a @BeforeAll as needed.
* 
