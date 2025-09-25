#!/usr/bin/env node
import * as cdk from 'aws-cdk-lib';
import { CdkStack } from '../lib/cdk-stack';
import { DataStoreStack} from "../lib/datastore-stack";

const app = new cdk.App();
new CdkStack(app, 'CdkStack');
new DataStoreStack(app, 'DataStoreStack');
