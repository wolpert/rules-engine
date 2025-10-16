#!/usr/bin/env node
import * as cdk from 'aws-cdk-lib';
import { MessagingStack } from '../lib/messaging-stack';
import { DataStoreStack} from "../lib/datastore-stack";

const app = new cdk.App();
new DataStoreStack(app, 'DataStoreStack');
new MessagingStack(app, 'MessagingStack');
