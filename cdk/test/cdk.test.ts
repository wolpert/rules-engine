import * as cdk from 'aws-cdk-lib';
import {Template} from 'aws-cdk-lib/assertions';
import * as Cdk from '../lib/messaging-stack';
import * as Dstore from '../lib/datastore-stack';

test('SQS Queue and SNS Topic Created', () => {
    const app = new cdk.App();
    // WHEN
    const stack = new Cdk.MessagingStack(app, 'MyTestStack');
    // THEN

    const template = Template.fromStack(stack);

    template.hasResourceProperties('AWS::SQS::Queue', {
        VisibilityTimeout: 300
    });
    template.resourceCountIs('AWS::SNS::Topic', 1);
});
test('Database and Bucket Created', () => {
    const app = new cdk.App();
    // WHEN
    const stack = new Dstore.DataStoreStack(app, 'MyTestStack');
    // THEN
    const template = Template.fromStack(stack);
    template.hasResourceProperties('AWS::S3::Bucket', {
        BucketName: "codehead-rules-bucket"
    });
    template.hasResourceProperties('AWS::DynamoDB::Table', {
        TableName: "rules_data_store"
    })

});
