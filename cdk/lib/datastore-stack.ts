import {RemovalPolicy, Stack, StackProps} from "aws-cdk-lib";
import {Construct} from "constructs";
import * as db from "aws-cdk-lib/aws-dynamodb";
import * as s3 from "aws-cdk-lib/aws-s3";

export class DataStoreStack extends Stack {
    datastore: db.Table;
    rulesBucket: s3.Bucket;

    constructor(scope: Construct, id: string, props?: StackProps) {
        super(scope, id, props);
        this.datastore = new db.Table(this, "DataStore", {
            tableName: "rules_data_store",
            partitionKey: {name: "hash_key", type: db.AttributeType.STRING},
            sortKey: {name: "sort_key", type: db.AttributeType.STRING},
            removalPolicy: RemovalPolicy.DESTROY,
            billingMode: db.BillingMode.PAY_PER_REQUEST,
        });
        this.rulesBucket = new s3.Bucket(this, "RulesBucket", {
            bucketName: "codehead-rules-bucket",
            removalPolicy: RemovalPolicy.DESTROY,
            autoDeleteObjects: true,
        });
    }

    public getDataStore() {
        return this.datastore;
    }

    public getRulesBucket() {
        return this.rulesBucket;
    }
}
