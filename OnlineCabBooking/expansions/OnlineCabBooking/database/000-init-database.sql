-- Provisioning script for OnlineCabBooking
-- Database: POSTGRESQL
-- expanded with nsx-expanders:5.12.1, expansionResource net.democritus:sql-expanders:2.5.0

-- ------------------ --
-- Cleanup            --
-- ------------------ --

-- @anchor:before-cleanup:start
-- @anchor:before-cleanup:end
-- anchor:custom-before-cleanup:start
-- anchor:custom-before-cleanup:end

-- anchor:cleanup:start
DROP SCHEMA IF EXISTS UTILS CASCADE;
DROP SCHEMA IF EXISTS ACCOUNT CASCADE;
DROP SCHEMA IF EXISTS VALIDATION CASCADE;
DROP SCHEMA IF EXISTS ASSETS CASCADE;
DROP SCHEMA IF EXISTS WORKFLOW CASCADE;
DROP SCHEMA IF EXISTS ONLINECABBOOKINGCOMP CASCADE;
-- anchor:cleanup:end
-- @anchor:cleanup:start
DROP SEQUENCE IF EXISTS hibernate_sequence CASCADE;
-- @anchor:cleanup:end
-- anchor:custom-cleanup:start
-- anchor:custom-cleanup:end

-- ------------------ --
-- Create Schemas     --
-- ------------------ --

-- anchor:schemas:start
CREATE SCHEMA UTILS;
CREATE SCHEMA ACCOUNT;
CREATE SCHEMA VALIDATION;
CREATE SCHEMA ASSETS;
CREATE SCHEMA WORKFLOW;
CREATE SCHEMA ONLINECABBOOKINGCOMP;
-- anchor:schemas:end
-- @anchor:schemas:start
-- @anchor:schemas:end

-- ------------------- --
-- Create Sequences    --
-- ------------------- --

-- @anchor:sequences:start
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence INCREMENT BY 1;
-- @anchor:sequences:end

-- ------------------ --
-- Create Tables      --
-- ------------------ --

-- anchor:tables:start
-- > Execution
CREATE TABLE UTILS.Execution (
    id BIGINT NOT NULL,
    component VARCHAR(255),
    element VARCHAR(255),
    name VARCHAR(255),
    packageName VARCHAR(255),
    -- @anchor:utils-execution-table:start
    -- @anchor:utils-execution-table:end
    -- anchor:custom-utils-execution-table:start
    -- anchor:custom-utils-execution-table:end
    PRIMARY KEY (id)
);
-- < Execution

-- > IdCounter
CREATE TABLE UTILS.IdCounter (
    id BIGINT NOT NULL,
    counter BIGINT,
    name VARCHAR(255),
    -- @anchor:utils-idCounter-table:start
    -- @anchor:utils-idCounter-table:end
    -- anchor:custom-utils-idCounter-table:start
    -- anchor:custom-utils-idCounter-table:end
    PRIMARY KEY (id)
);
-- < IdCounter

-- > ParamTargetValue
CREATE TABLE UTILS.ParamTargetValue (
    id BIGINT NOT NULL,
    param VARCHAR(255),
    target VARCHAR(255),
    value VARCHAR(255),
    -- @anchor:utils-paramTargetValue-table:start
    -- @anchor:utils-paramTargetValue-table:end
    -- anchor:custom-utils-paramTargetValue-table:start
    -- anchor:custom-utils-paramTargetValue-table:end
    PRIMARY KEY (id)
);
-- < ParamTargetValue

-- > TagValuePair
CREATE TABLE UTILS.TagValuePair (
    id BIGINT NOT NULL,
    tag VARCHAR(255),
    value VARCHAR(255),
    -- @anchor:utils-tagValuePair-table:start
    -- @anchor:utils-tagValuePair-table:end
    -- anchor:custom-utils-tagValuePair-table:start
    -- anchor:custom-utils-tagValuePair-table:end
    PRIMARY KEY (id)
);
-- < TagValuePair

-- > Thumbnail
CREATE TABLE UTILS.Thumbnail (
    id BIGINT NOT NULL,
    border INTEGER,
    clickAction VARCHAR(255),
    depth INTEGER,
    fullName VARCHAR(255),
    height INTEGER,
    hooverAction VARCHAR(255),
    leftX INTEGER,
    name VARCHAR(255),
    targetId BIGINT,
    targetName VARCHAR(255),
    targetType VARCHAR(255),
    thumbName VARCHAR(255),
    thumbType VARCHAR(255),
    topY INTEGER,
    uri VARCHAR(255),
    width INTEGER,
    -- @anchor:utils-thumbnail-table:start
    -- @anchor:utils-thumbnail-table:end
    -- anchor:custom-utils-thumbnail-table:start
    -- anchor:custom-utils-thumbnail-table:end
    PRIMARY KEY (id)
);
-- < Thumbnail

-- > Account
CREATE TABLE ACCOUNT.Account (
    id BIGINT NOT NULL,
    address VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),
    email VARCHAR(255),
    fullName VARCHAR(255),
    name VARCHAR(255),
    phone VARCHAR(255),
    refId VARCHAR(255),
    status VARCHAR(255),
    style VARCHAR(255),
    zipCode VARCHAR(255),
    -- @anchor:account-account-table:start
    -- @anchor:account-account-table:end
    -- anchor:custom-account-account-table:start
    -- anchor:custom-account-account-table:end
    PRIMARY KEY (id)
);
-- < Account

-- > Component
CREATE TABLE ACCOUNT.Component (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    -- @anchor:account-component-table:start
    -- @anchor:account-component-table:end
    -- anchor:custom-account-component-table:start
    -- anchor:custom-account-component-table:end
    PRIMARY KEY (id)
);
-- < Component

-- > DataAccess
CREATE TABLE ACCOUNT.DataAccess (
    id BIGINT NOT NULL,
    authorized VARCHAR(255),
    disabled VARCHAR(255),
    element VARCHAR(255),
    enteredAt TIMESTAMP,
    forProfile_id BIGINT,
    forUser_id BIGINT,
    forUserGroup_id BIGINT,
    functionality VARCHAR(255),
    lastModifiedAt TIMESTAMP,
    name VARCHAR(255),
    target VARCHAR(255),
    -- @anchor:account-dataAccess-table:start
    -- @anchor:account-dataAccess-table:end
    -- anchor:custom-account-dataAccess-table:start
    -- anchor:custom-account-dataAccess-table:end
    PRIMARY KEY (id)
);
-- < DataAccess

-- > HelpInfo
CREATE TABLE ACCOUNT.HelpInfo (
    id BIGINT NOT NULL,
    description VARCHAR(255),
    name VARCHAR(255),
    -- @anchor:account-helpInfo-table:start
    -- @anchor:account-helpInfo-table:end
    -- anchor:custom-account-helpInfo-table:start
    -- anchor:custom-account-helpInfo-table:end
    PRIMARY KEY (id)
);
-- < HelpInfo

-- > Menu
CREATE TABLE ACCOUNT.Menu (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    portal_id BIGINT,
    profile_id BIGINT,
    -- @anchor:account-menu-table:start
    -- @anchor:account-menu-table:end
    -- anchor:custom-account-menu-table:start
    -- anchor:custom-account-menu-table:end
    PRIMARY KEY (id)
);
-- < Menu

-- > MenuItem
CREATE TABLE ACCOUNT.MenuItem (
    id BIGINT NOT NULL,
    menu_id BIGINT,
    menuItem_id BIGINT,
    name VARCHAR(255),
    screen_id BIGINT,
    sortOrder INTEGER,
    -- @anchor:account-menuItem-table:start
    -- @anchor:account-menuItem-table:end
    -- anchor:custom-account-menuItem-table:start
    -- anchor:custom-account-menuItem-table:end
    PRIMARY KEY (id)
);
-- < MenuItem

-- > Portal
CREATE TABLE ACCOUNT.Portal (
    id BIGINT NOT NULL,
    description VARCHAR(255),
    name VARCHAR(255),
    version VARCHAR(255),
    -- @anchor:account-portal-table:start
    -- @anchor:account-portal-table:end
    -- anchor:custom-account-portal-table:start
    -- anchor:custom-account-portal-table:end
    PRIMARY KEY (id)
);
-- < Portal

-- > Profile
CREATE TABLE ACCOUNT.Profile (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    userGroup_id BIGINT,
    weight INTEGER,
    -- @anchor:account-profile-table:start
    -- @anchor:account-profile-table:end
    -- anchor:custom-account-profile-table:start
    -- anchor:custom-account-profile-table:end
    PRIMARY KEY (id)
);
-- < Profile

-- > Screen
CREATE TABLE ACCOUNT.Screen (
    id BIGINT NOT NULL,
    component_id BIGINT,
    link VARCHAR(255),
    name VARCHAR(255),
    sortOrder INTEGER,
    -- @anchor:account-screen-table:start
    -- @anchor:account-screen-table:end
    -- anchor:custom-account-screen-table:start
    -- anchor:custom-account-screen-table:end
    PRIMARY KEY (id)
);
-- < Screen

-- > ScreenProfile
CREATE TABLE ACCOUNT.ScreenProfile (
    id BIGINT NOT NULL,
    profile_id BIGINT,
    screen_id BIGINT,
    -- @anchor:account-screenProfile-table:start
    -- @anchor:account-screenProfile-table:end
    -- anchor:custom-account-screenProfile-table:start
    -- anchor:custom-account-screenProfile-table:end
    PRIMARY KEY (id)
);
-- < ScreenProfile

-- > User
CREATE TABLE ACCOUNT.User (
    id BIGINT NOT NULL,
    account_id BIGINT,
    disabled VARCHAR(255),
    email VARCHAR(255),
    encryptedPassword VARCHAR(255),
    enteredAt TIMESTAMP,
    firstName VARCHAR(255),
    fullName VARCHAR(255),
    language VARCHAR(255),
    lastModifiedAt TIMESTAMP,
    lastName VARCHAR(255),
    mobile VARCHAR(255),
    name VARCHAR(255),
    password VARCHAR(255),
    persNr VARCHAR(255),
    profile_id BIGINT,
    timeout INTEGER,
    -- @anchor:account-user-table:start
    -- @anchor:account-user-table:end
    -- anchor:custom-account-user-table:start
    -- anchor:custom-account-user-table:end
    PRIMARY KEY (id)
);
-- < User

-- > UserGroup
CREATE TABLE ACCOUNT.UserGroup (
    id BIGINT NOT NULL,
    disabled VARCHAR(255),
    enteredAt TIMESTAMP,
    lastModifiedAt TIMESTAMP,
    name VARCHAR(255),
    type VARCHAR(255),
    -- @anchor:account-userGroup-table:start
    -- @anchor:account-userGroup-table:end
    -- anchor:custom-account-userGroup-table:start
    -- anchor:custom-account-userGroup-table:end
    PRIMARY KEY (id)
);
-- < UserGroup

-- > Validation
CREATE TABLE VALIDATION.Validation (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    -- @anchor:validation-validation-table:start
    -- @anchor:validation-validation-table:end
    -- anchor:custom-validation-validation-table:start
    -- anchor:custom-validation-validation-table:end
    PRIMARY KEY (id)
);
-- < Validation

-- > Asset
CREATE TABLE ASSETS.Asset (
    id BIGINT NOT NULL,
    byteSize BIGINT,
    complete BOOL,
    contentType VARCHAR(255),
    externalAsset_id BIGINT,
    fileAsset_id BIGINT,
    fileId VARCHAR(255),
    internalAsset_id BIGINT,
    name VARCHAR(255),
    type VARCHAR(255),
    -- @anchor:assets-asset-table:start
    -- @anchor:assets-asset-table:end
    -- anchor:custom-assets-asset-table:start
    -- anchor:custom-assets-asset-table:end
    PRIMARY KEY (id)
);
-- < Asset

-- > ExternalAsset
CREATE TABLE ASSETS.ExternalAsset (
    id BIGINT NOT NULL,
    byteSize BIGINT,
    contentType VARCHAR(255),
    name VARCHAR(255),
    uri VARCHAR(255),
    -- @anchor:assets-externalAsset-table:start
    -- @anchor:assets-externalAsset-table:end
    -- anchor:custom-assets-externalAsset-table:start
    -- anchor:custom-assets-externalAsset-table:end
    PRIMARY KEY (id)
);
-- < ExternalAsset

-- > FileAsset
CREATE TABLE ASSETS.FileAsset (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    uploadUri VARCHAR(255),
    -- @anchor:assets-fileAsset-table:start
    -- @anchor:assets-fileAsset-table:end
    -- anchor:custom-assets-fileAsset-table:start
    -- anchor:custom-assets-fileAsset-table:end
    PRIMARY KEY (id)
);
-- < FileAsset

-- > InternalAsset
CREATE TABLE ASSETS.InternalAsset (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    -- @anchor:assets-internalAsset-table:start
    -- @anchor:assets-internalAsset-table:end
    -- anchor:custom-assets-internalAsset-table:start
    -- anchor:custom-assets-internalAsset-table:end
    PRIMARY KEY (id)
);
-- < InternalAsset

-- > InternalAssetChunk
CREATE TABLE ASSETS.InternalAssetChunk (
    id BIGINT NOT NULL,
    byteSize INTEGER,
    content BYTEA,
    internalAsset_id BIGINT,
    isLast BOOL,
    name VARCHAR(255),
    position INTEGER,
    -- @anchor:assets-internalAssetChunk-table:start
    -- @anchor:assets-internalAssetChunk-table:end
    -- anchor:custom-assets-internalAssetChunk-table:start
    -- anchor:custom-assets-internalAssetChunk-table:end
    PRIMARY KEY (id)
);
-- < InternalAssetChunk

-- > RemoteAsset
CREATE TABLE ASSETS.RemoteAsset (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    url VARCHAR(255),
    -- @anchor:assets-remoteAsset-table:start
    -- @anchor:assets-remoteAsset-table:end
    -- anchor:custom-assets-remoteAsset-table:start
    -- anchor:custom-assets-remoteAsset-table:end
    PRIMARY KEY (id)
);
-- < RemoteAsset

-- > EngineNode
CREATE TABLE WORKFLOW.EngineNode (
    id BIGINT NOT NULL,
    activeSince TIMESTAMP,
    hostname VARCHAR(255),
    lastActive TIMESTAMP,
    master BOOL,
    name VARCHAR(255),
    status VARCHAR(255),
    -- @anchor:workflow-engineNode-table:start
    -- @anchor:workflow-engineNode-table:end
    -- anchor:custom-workflow-engineNode-table:start
    -- anchor:custom-workflow-engineNode-table:end
    PRIMARY KEY (id)
);
-- < EngineNode

-- > EngineNodeService
CREATE TABLE WORKFLOW.EngineNodeService (
    id BIGINT NOT NULL,
    engineNode_id BIGINT,
    engineService_id BIGINT,
    lastRunAt TIMESTAMP,
    name VARCHAR(255),
    nextRun TIMESTAMP,
    status VARCHAR(255),
    -- @anchor:workflow-engineNodeService-table:start
    -- @anchor:workflow-engineNodeService-table:end
    -- anchor:custom-workflow-engineNodeService-table:start
    -- anchor:custom-workflow-engineNodeService-table:end
    PRIMARY KEY (id)
);
-- < EngineNodeService

-- > EngineService
CREATE TABLE WORKFLOW.EngineService (
    id BIGINT NOT NULL,
    batchSize INTEGER,
    busy VARCHAR(255),
    changed VARCHAR(255),
    collector BIGINT,
    lastRunAt TIMESTAMP,
    maximumNumberOfNodes INTEGER,
    name VARCHAR(255),
    status VARCHAR(255),
    timeWindowGroup_id BIGINT,
    waitTime INTEGER,
    workflow_id BIGINT,
    -- @anchor:workflow-engineService-table:start
    -- @anchor:workflow-engineService-table:end
    -- anchor:custom-workflow-engineService-table:start
    -- anchor:custom-workflow-engineService-table:end
    PRIMARY KEY (id)
);
-- < EngineService

-- > StateTask
CREATE TABLE WORKFLOW.StateTask (
    id BIGINT NOT NULL,
    beginState VARCHAR(255),
    endState VARCHAR(255),
    failedState VARCHAR(255),
    implementation VARCHAR(255),
    interimState VARCHAR(255),
    maxConcurrentTasks INTEGER,
    name VARCHAR(255),
    params VARCHAR(255),
    processor VARCHAR(255),
    timeout BIGINT,
    workflow_id BIGINT,
    -- @anchor:workflow-stateTask-table:start
    -- @anchor:workflow-stateTask-table:end
    -- anchor:custom-workflow-stateTask-table:start
    -- anchor:custom-workflow-stateTask-table:end
    PRIMARY KEY (id)
);
-- < StateTask

-- > StateTimer
CREATE TABLE WORKFLOW.StateTimer (
    id BIGINT NOT NULL,
    allowedPeriod BIGINT,
    alteredState VARCHAR(255),
    beginState VARCHAR(255),
    implementation VARCHAR(255),
    name VARCHAR(255),
    params VARCHAR(255),
    processor VARCHAR(255),
    requiredAction VARCHAR(255),
    targetState VARCHAR(255),
    workflow_id BIGINT,
    -- @anchor:workflow-stateTimer-table:start
    -- @anchor:workflow-stateTimer-table:end
    -- anchor:custom-workflow-stateTimer-table:start
    -- anchor:custom-workflow-stateTimer-table:end
    PRIMARY KEY (id)
);
-- < StateTimer

-- > TimeTask
CREATE TABLE WORKFLOW.TimeTask (
    id BIGINT NOT NULL,
    implementation VARCHAR(255),
    intervalPeriod BIGINT,
    name VARCHAR(255),
    params VARCHAR(255),
    processor VARCHAR(255),
    requiredAction VARCHAR(255),
    triggerState VARCHAR(255),
    workflow_id BIGINT,
    -- @anchor:workflow-timeTask-table:start
    -- @anchor:workflow-timeTask-table:end
    -- anchor:custom-workflow-timeTask-table:start
    -- anchor:custom-workflow-timeTask-table:end
    PRIMARY KEY (id)
);
-- < TimeTask

-- > TimeWindow
CREATE TABLE WORKFLOW.TimeWindow (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    startTime VARCHAR(255),
    stopTime VARCHAR(255),
    -- @anchor:workflow-timeWindow-table:start
    -- @anchor:workflow-timeWindow-table:end
    -- anchor:custom-workflow-timeWindow-table:start
    -- anchor:custom-workflow-timeWindow-table:end
    PRIMARY KEY (id)
);
-- < TimeWindow

-- > TimeWindowGroup
CREATE TABLE WORKFLOW.TimeWindowGroup (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    visible VARCHAR(255),
    -- @anchor:workflow-timeWindowGroup-table:start
    -- @anchor:workflow-timeWindowGroup-table:end
    -- anchor:custom-workflow-timeWindowGroup-table:start
    -- anchor:custom-workflow-timeWindowGroup-table:end
    PRIMARY KEY (id)
);
-- < TimeWindowGroup

-- > Workflow
CREATE TABLE WORKFLOW.Workflow (
    id BIGINT NOT NULL,
    className VARCHAR(255),
    componentName VARCHAR(255),
    name VARCHAR(255),
    responsible_id BIGINT,
    sequencingStrategy VARCHAR(255),
    target VARCHAR(255),
    -- @anchor:workflow-workflow-table:start
    -- @anchor:workflow-workflow-table:end
    -- anchor:custom-workflow-workflow-table:start
    -- anchor:custom-workflow-workflow-table:end
    PRIMARY KEY (id)
);
-- < Workflow

-- > Address
CREATE TABLE ONLINECABBOOKINGCOMP.Address (
    id BIGINT NOT NULL,
    city VARCHAR(255),
    houseNumber INTEGER,
    name VARCHAR(255),
    pincode VARCHAR(255),
    state VARCHAR(255),
    street VARCHAR(255),
    -- @anchor:onlineCabBookingComp-address-table:start
    -- @anchor:onlineCabBookingComp-address-table:end
    -- anchor:custom-onlineCabBookingComp-address-table:start
    -- anchor:custom-onlineCabBookingComp-address-table:end
    PRIMARY KEY (id)
);
-- < Address

-- > Cab
CREATE TABLE ONLINECABBOOKINGCOMP.Cab (
    id BIGINT NOT NULL,
    carType_id BIGINT,
    driver_id BIGINT,
    name VARCHAR(255),
    ratePerKm INTEGER,
    -- @anchor:onlineCabBookingComp-cab-table:start
    -- @anchor:onlineCabBookingComp-cab-table:end
    -- anchor:custom-onlineCabBookingComp-cab-table:start
    -- anchor:custom-onlineCabBookingComp-cab-table:end
    PRIMARY KEY (id)
);
-- < Cab

-- > CarType
CREATE TABLE ONLINECABBOOKINGCOMP.CarType (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    -- @anchor:onlineCabBookingComp-carType-table:start
    -- @anchor:onlineCabBookingComp-carType-table:end
    -- anchor:custom-onlineCabBookingComp-carType-table:start
    -- anchor:custom-onlineCabBookingComp-carType-table:end
    PRIMARY KEY (id)
);
-- < CarType

-- > Customer
CREATE TABLE ONLINECABBOOKINGCOMP.Customer (
    id BIGINT NOT NULL,
    journeyStatus BOOL,
    name VARCHAR(255),
    person_id BIGINT,
    -- @anchor:onlineCabBookingComp-customer-table:start
    -- @anchor:onlineCabBookingComp-customer-table:end
    -- anchor:custom-onlineCabBookingComp-customer-table:start
    -- anchor:custom-onlineCabBookingComp-customer-table:end
    PRIMARY KEY (id)
);
-- < Customer

-- > Driver
CREATE TABLE ONLINECABBOOKINGCOMP.Driver (
    id BIGINT NOT NULL,
    cab_id BIGINT,
    isAvailable BOOL,
    licenseNo INTEGER,
    name VARCHAR(255),
    rating DOUBLE PRECISION,
    tripBooking_id BIGINT,
    -- @anchor:onlineCabBookingComp-driver-table:start
    -- @anchor:onlineCabBookingComp-driver-table:end
    -- anchor:custom-onlineCabBookingComp-driver-table:start
    -- anchor:custom-onlineCabBookingComp-driver-table:end
    PRIMARY KEY (id)
);
-- < Driver

-- > Payment
CREATE TABLE ONLINECABBOOKINGCOMP.Payment (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    statusPayed BOOL,
    -- @anchor:onlineCabBookingComp-payment-table:start
    -- @anchor:onlineCabBookingComp-payment-table:end
    -- anchor:custom-onlineCabBookingComp-payment-table:start
    -- anchor:custom-onlineCabBookingComp-payment-table:end
    PRIMARY KEY (id)
);
-- < Payment

-- > Person
CREATE TABLE ONLINECABBOOKINGCOMP.Person (
    id BIGINT NOT NULL,
    address_id BIGINT,
    email VARCHAR(255),
    mobile VARCHAR(255),
    name VARCHAR(255),
    password VARCHAR(255),
    username VARCHAR(255),
    -- @anchor:onlineCabBookingComp-person-table:start
    -- @anchor:onlineCabBookingComp-person-table:end
    -- anchor:custom-onlineCabBookingComp-person-table:start
    -- anchor:custom-onlineCabBookingComp-person-table:end
    PRIMARY KEY (id)
);
-- < Person

-- > TripBooking
CREATE TABLE ONLINECABBOOKINGCOMP.TripBooking (
    id BIGINT NOT NULL,
    customer_id BIGINT,
    driver_id BIGINT,
    fromDateTime TIMESTAMP,
    fromLocation_id BIGINT,
    km DOUBLE PRECISION,
    name VARCHAR(255),
    payment_id BIGINT,
    toDateTime TIMESTAMP,
    toLocation_id BIGINT,
    totalAmount DOUBLE PRECISION,
    -- @anchor:onlineCabBookingComp-tripBooking-table:start
    -- @anchor:onlineCabBookingComp-tripBooking-table:end
    -- anchor:custom-onlineCabBookingComp-tripBooking-table:start
    -- anchor:custom-onlineCabBookingComp-tripBooking-table:end
    PRIMARY KEY (id)
);
-- < TripBooking
-- anchor:tables:end
-- @anchor:tables:start
-- @anchor:tables:end

-- ------------------ --
-- Create Join Tables --
-- ------------------ --

-- anchor:join-tables:start
-- > Execution
-- < Execution

-- > IdCounter
-- < IdCounter

-- > ParamTargetValue
-- < ParamTargetValue

-- > TagValuePair
-- < TagValuePair

-- > Thumbnail
-- < Thumbnail

-- > Account
-- < Account

-- > Component
-- < Component

-- > DataAccess
CREATE TABLE ACCOUNT.dataAccess_userGroups (
    dataAccess_id BIGINT,
    userGroup_id BIGINT
    -- @anchor:account-dataAccess-userGroups-jointable:start
    -- @anchor:account-dataAccess-userGroups-jointable:end
    -- anchor:custom-account-dataAccess-userGroups-jointable:start
    -- anchor:custom-account-dataAccess-userGroups-jointable:end
);

ALTER TABLE ACCOUNT.dataAccess_userGroups
ADD FOREIGN KEY (dataAccess_id)
REFERENCES ACCOUNT.DataAccess (id) DEFERRABLE;

ALTER TABLE ACCOUNT.dataAccess_userGroups
ADD FOREIGN KEY (userGroup_id)
REFERENCES ACCOUNT.UserGroup (id) DEFERRABLE;
-- < DataAccess

-- > HelpInfo
-- < HelpInfo

-- > Menu
-- < Menu

-- > MenuItem
-- < MenuItem

-- > Portal
-- < Portal

-- > Profile
CREATE TABLE ACCOUNT.profile_screens (
    profile_id BIGINT,
    screen_id BIGINT
    -- @anchor:account-profile-screens-jointable:start
    -- @anchor:account-profile-screens-jointable:end
    -- anchor:custom-account-profile-screens-jointable:start
    -- anchor:custom-account-profile-screens-jointable:end
);

ALTER TABLE ACCOUNT.profile_screens
ADD FOREIGN KEY (profile_id)
REFERENCES ACCOUNT.Profile (id) DEFERRABLE;

ALTER TABLE ACCOUNT.profile_screens
ADD FOREIGN KEY (screen_id)
REFERENCES ACCOUNT.Screen (id) DEFERRABLE;
-- < Profile

-- > Screen
-- < Screen

-- > ScreenProfile
CREATE TABLE ACCOUNT.screenProfile_screens (
    screenProfile_id BIGINT,
    screen_id BIGINT
    -- @anchor:account-screenProfile-screens-jointable:start
    -- @anchor:account-screenProfile-screens-jointable:end
    -- anchor:custom-account-screenProfile-screens-jointable:start
    -- anchor:custom-account-screenProfile-screens-jointable:end
);

ALTER TABLE ACCOUNT.screenProfile_screens
ADD FOREIGN KEY (screenProfile_id)
REFERENCES ACCOUNT.ScreenProfile (id) DEFERRABLE;

ALTER TABLE ACCOUNT.screenProfile_screens
ADD FOREIGN KEY (screen_id)
REFERENCES ACCOUNT.Screen (id) DEFERRABLE;
-- < ScreenProfile

-- > User
CREATE TABLE ACCOUNT.user_userGroups (
    user_id BIGINT,
    userGroup_id BIGINT
    -- @anchor:account-user-userGroups-jointable:start
    -- @anchor:account-user-userGroups-jointable:end
    -- anchor:custom-account-user-userGroups-jointable:start
    -- anchor:custom-account-user-userGroups-jointable:end
);

ALTER TABLE ACCOUNT.user_userGroups
ADD FOREIGN KEY (user_id)
REFERENCES ACCOUNT.User (id) DEFERRABLE;

ALTER TABLE ACCOUNT.user_userGroups
ADD FOREIGN KEY (userGroup_id)
REFERENCES ACCOUNT.UserGroup (id) DEFERRABLE;
-- < User

-- > UserGroup
-- < UserGroup

-- > Validation
-- < Validation

-- > Asset
-- < Asset

-- > ExternalAsset
-- < ExternalAsset

-- > FileAsset
-- < FileAsset

-- > InternalAsset
-- < InternalAsset

-- > InternalAssetChunk
-- < InternalAssetChunk

-- > RemoteAsset
-- < RemoteAsset

-- > EngineNode
-- < EngineNode

-- > EngineNodeService
-- < EngineNodeService

-- > EngineService
-- < EngineService

-- > StateTask
-- < StateTask

-- > StateTimer
-- < StateTimer

-- > TimeTask
-- < TimeTask

-- > TimeWindow
-- < TimeWindow

-- > TimeWindowGroup
CREATE TABLE WORKFLOW.timeWindowGroup_timeWindows (
    timeWindowGroup_id BIGINT,
    timeWindow_id BIGINT
    -- @anchor:workflow-timeWindowGroup-timeWindows-jointable:start
    -- @anchor:workflow-timeWindowGroup-timeWindows-jointable:end
    -- anchor:custom-workflow-timeWindowGroup-timeWindows-jointable:start
    -- anchor:custom-workflow-timeWindowGroup-timeWindows-jointable:end
);

ALTER TABLE WORKFLOW.timeWindowGroup_timeWindows
ADD FOREIGN KEY (timeWindowGroup_id)
REFERENCES WORKFLOW.TimeWindowGroup (id) DEFERRABLE;

ALTER TABLE WORKFLOW.timeWindowGroup_timeWindows
ADD FOREIGN KEY (timeWindow_id)
REFERENCES WORKFLOW.TimeWindow (id) DEFERRABLE;
-- < TimeWindowGroup

-- > Workflow
-- < Workflow

-- > Address
-- < Address

-- > Cab
-- < Cab

-- > CarType
-- < CarType

-- > Customer
-- < Customer

-- > Driver
-- < Driver

-- > Payment
-- < Payment

-- > Person
-- < Person

-- > TripBooking
-- < TripBooking
-- anchor:join-tables:end
-- @anchor:join-tables:start
-- @anchor:join-tables:end

-- ------------------ --
-- Add Foreign Keys   --
-- ------------------ --

-- anchor:foreign-keys:start
-- > Execution
-- < Execution

-- > IdCounter
-- < IdCounter

-- > ParamTargetValue
-- < ParamTargetValue

-- > TagValuePair
-- < TagValuePair

-- > Thumbnail
-- < Thumbnail

-- > Account
-- < Account

-- > Component
-- < Component

-- > DataAccess
-- < DataAccess

-- > HelpInfo
-- < HelpInfo

-- > Menu
-- < Menu

-- > MenuItem
-- < MenuItem

-- > Portal
-- < Portal

-- > Profile
-- < Profile

-- > Screen
-- < Screen

-- > ScreenProfile
-- < ScreenProfile

-- > User
-- < User

-- > UserGroup
-- < UserGroup

-- > Validation
-- < Validation

-- > Asset
-- < Asset

-- > ExternalAsset
-- < ExternalAsset

-- > FileAsset
-- < FileAsset

-- > InternalAsset
-- < InternalAsset

-- > InternalAssetChunk
ALTER TABLE ASSETS.InternalAssetChunk
ADD FOREIGN KEY (internalAsset_id)
REFERENCES ASSETS.InternalAsset (id) DEFERRABLE;
-- < InternalAssetChunk

-- > RemoteAsset
-- < RemoteAsset

-- > EngineNode
-- < EngineNode

-- > EngineNodeService
ALTER TABLE WORKFLOW.EngineNodeService
ADD FOREIGN KEY (engineNode_id)
REFERENCES WORKFLOW.EngineNode (id) DEFERRABLE;

ALTER TABLE WORKFLOW.EngineNodeService
ADD FOREIGN KEY (engineService_id)
REFERENCES WORKFLOW.EngineService (id) DEFERRABLE;
-- < EngineNodeService

-- > EngineService
-- < EngineService

-- > StateTask
-- < StateTask

-- > StateTimer
-- < StateTimer

-- > TimeTask
-- < TimeTask

-- > TimeWindow
-- < TimeWindow

-- > TimeWindowGroup
-- < TimeWindowGroup

-- > Workflow
-- < Workflow

-- > Address
-- < Address

-- > Cab
-- < Cab

-- > CarType
-- < CarType

-- > Customer
-- < Customer

-- > Driver
-- < Driver

-- > Payment
-- < Payment

-- > Person
-- < Person

-- > TripBooking
-- < TripBooking
-- anchor:foreign-keys:end
-- @anchor:foreign-keys:start
-- @anchor:foreign-keys:end

-- ------------------ --
-- Custom Statements  --
-- ------------------ --

-- @anchor:statements:start
-- @anchor:statements:end
-- anchor:custom-statements:start
-- anchor:custom-statements:end
