CREATE KEYSPACE IF NOT EXISTS demo WITH REPLICATION ={'class': 'SimpleStrategy', 'replication_factor': '1'};

CREATE TABLE demo.user (
	group_id TEXT,
	username TEXT,
	id TEXT,
	firstname TEXT,
	lastname TEXT,
	email TEXT,
	password TEXT,
	phone TEXT,
	userstatus INT,

	before_id TEXT,
	before_firstname TEXT,
	before_lastname TEXT,
	before_email TEXT,
	before_password TEXT,
	before_phone TEXT,
	before_userstatus INT,

	tx_id text,
	tx_prepared_at bigint,
	tx_committed_at bigint,
	tx_state int,
	tx_version int,

	before_tx_id text,
	before_tx_prepared_at bigint,
	before_tx_committed_at bigint,
	before_tx_state int,
	before_tx_version int,

	PRIMARY KEY (group_id, username),
);


DROP KEYSPACE IF EXISTS coordinator;
CREATE KEYSPACE coordinator WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};

CREATE TABLE coordinator.state (
	tx_id text PRIMARY KEY,
	tx_created_at bigint,
	tx_state int
);