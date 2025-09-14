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