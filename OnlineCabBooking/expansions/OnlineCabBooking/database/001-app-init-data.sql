-- Initial data script for OnlineCabBooking
-- Database: POSTGRESQL
-- expanded with nsx-expanders:5.12.1, expansionResource net.democritus:sql-expanders:2.5.0

INSERT INTO UTILS.ParamTargetValue (
  id,
  param,
  target,
  value
) VALUES (
  nextval('hibernate_sequence'),
  'applicationName',
  'default',
  'OnlineCabBooking'
),
(
  nextval('hibernate_sequence'),
  'ScreenInfoRetrieverActionImpl',
  'default',
  'net.democritus.web.action.ScreenInfoRetrieverAction'
),
(
  nextval('hibernate_sequence'),
  'defaultStyle',
  'default',
  'nsxbootstrap'
),
(
  nextval('hibernate_sequence'),
  'fileUploadDirUri',
  'default',
  ''
),
(
  nextval('hibernate_sequence'),
  'assetChunkSize',
  'default',
  '524288'
),
(
  nextval('hibernate_sequence'),
  'useEncryptedPassword',
  'default',
  'true'
);

INSERT INTO UTILS.TagValuePair (
  id,
  tag,
  value
) VALUES (
  nextval('hibernate_sequence'),
  'account_style',
  'nsxbootstrap'
),
(
  nextval('hibernate_sequence'),
  'account_style',
  'nsx'
),
(
  nextval('hibernate_sequence'),
  'account_style',
  'basic'
),
(
  nextval('hibernate_sequence'),
  'account_style',
  'naked'
),
(
  nextval('hibernate_sequence'),
  'user_language',
  'dutch'
),
(
  nextval('hibernate_sequence'),
  'user_language',
  'english'
),
(
  nextval('hibernate_sequence'),
  'user_timeout',
  '3'
),
(
  nextval('hibernate_sequence'),
  'user_timeout',
  '5'
),
(
  nextval('hibernate_sequence'),
  'user_timeout',
  '15'
),
(
  nextval('hibernate_sequence'),
  'user_timeout',
  '30'
),
(
  nextval('hibernate_sequence'),
  'user_timeout',
  '60'
),
(
  nextval('hibernate_sequence'),
  'user_timeout',
  '360'
),
(
  nextval('hibernate_sequence'),
  'user_timeout',
  '720'
),
(
  nextval('hibernate_sequence'),
  'user_timeout',
  '1220'
),
(
  nextval('hibernate_sequence'),
  'user_timeout',
  '1440'
),
(
  nextval('hibernate_sequence'),
  'engineService_status',
  'stop'
),
(
  nextval('hibernate_sequence'),
  'engineService_status',
  'start'
),
(
  nextval('hibernate_sequence'),
  'engineService_changed',
  'no'
),
(
  nextval('hibernate_sequence'),
  'engineService_changed',
  'yes'
),
(
  nextval('hibernate_sequence'),
  'engineService_busy',
  'no'
),
(
  nextval('hibernate_sequence'),
  'engineService_busy',
  'yes'
),
(
  nextval('hibernate_sequence'),
  'stateTimer_requiredAction',
  'DoTask'
),
(
  nextval('hibernate_sequence'),
  'stateTimer_requiredAction',
  'AlterState'
),
(
  nextval('hibernate_sequence'),
  'stateTimer_requiredAction',
  'DoAndAlter'
),
(
  nextval('hibernate_sequence'),
  'timeTask_requiredAction',
  'DoTask'
),
(
  nextval('hibernate_sequence'),
  'timeTask_requiredAction',
  'AlterState'
),
(
  nextval('hibernate_sequence'),
  'timeTask_requiredAction',
  'DoAndAlter'
),
(
  nextval('hibernate_sequence'),
  'execution_name',
  'importCsv'
),
(
  nextval('hibernate_sequence'),
  'execution_name',
  'provisionFlow'
),
(
  nextval('hibernate_sequence'),
  'execution_name',
  'startEngine'
),
(
  nextval('hibernate_sequence'),
  'asset_type',
  'FILE'
),
(
  nextval('hibernate_sequence'),
  'asset_type',
  'INTERNAL'
);

INSERT INTO UTILS.IdCounter (
  id,
  counter,
  name
) VALUES (
  nextval('hibernate_sequence'),
  0,
  'object_id'
),
(
  nextval('hibernate_sequence'),
  0,
  'account_ref_id'
),
(
  nextval('hibernate_sequence'),
  0,
  'dataAccess_ref_id'
);

INSERT INTO ACCOUNT.Account (
  id,
  address,
  city,
  country,
  email,
  fullName,
  name,
  phone,
  refId,
  status,
  style,
  zipCode
) VALUES (
  nextval('hibernate_sequence'),
  'Galileilaan 15',
  'Niel',
  'BE',
  'info@nsx.normalizedsystems.org',
  'Normalized Systems Expanders',
  'NSX',
  '+32 (0) 3 826 93 75',
  '1',
  'Activated',
  'nsxbootstrap',
  '2845'
);

INSERT INTO ACCOUNT.Profile (
  id,
  name,
  userGroup_id,
  weight
) VALUES (
  nextval('hibernate_sequence'),
  'admin',
  NULL,
  1
),
(
  nextval('hibernate_sequence'),
  'user',
  NULL,
  1
);

INSERT INTO ACCOUNT.User (
  id,
  account_id,
  disabled,
  email,
  encryptedPassword,
  enteredAt,
  firstName,
  fullName,
  language,
  lastModifiedAt,
  lastName,
  mobile,
  name,
  password,
  persNr,
  profile_id,
  timeout
) VALUES (
  nextval('hibernate_sequence'),
  (SELECT id FROM ACCOUNT.Account WHERE name = 'NSX'),
  'no',
  'beheerder@nsx.normalizedsystems.org',
  'iYRBSCp7s9q2OM1hvRrV2HyKYqwNjqR8veYLptkcIv0=',
  current_date,
  '',
  'Beheerder',
  'dutch',
  current_date,
  '',
  '',
  'admin',
  '',
  '',
  (SELECT id FROM ACCOUNT.Profile WHERE name = 'admin'),
  3600
);

INSERT INTO ACCOUNT.DataAccess (
  id,
  authorized,
  disabled,
  element,
  enteredAt,
  forProfile_id,
  forUser_id,
  forUserGroup_id,
  functionality,
  lastModifiedAt,
  name,
  target
) VALUES (
  nextval('hibernate_sequence'),
  'true',
  'no',
  '*',
  current_date,
  (SELECT id FROM ACCOUNT.Profile WHERE name = 'admin'),
  NULL,
  NULL,
  'all',
  current_date,
  'Profile admin - all components full access',
  ''
),
(
  nextval('hibernate_sequence'),
  'true',
  'no',
  'onlineCabBookingComp_*',
  current_date,
  (SELECT id FROM ACCOUNT.Profile WHERE name = 'user'),
  NULL,
  NULL,
  'all',
  current_date,
  'Profile user - onlineCabBookingComp component full access',
  ''
);

-- ------------------ --
-- Custom Statements  --
-- ------------------ --

-- @anchor:statements:start
-- @anchor:statements:end
-- anchor:custom-statements:start
-- anchor:custom-statements:end
