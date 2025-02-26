CREATE TABLE IF NOT EXISTS tcandidate (
	id UUID NOT NULL,
	name VARCHAR(255) NOT NULL,
	date_of_birth DATE NOT NULL,
	citizen_id VARCHAR(32) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tcandidate_role (
	candidate_id UUID NOT NULL,
	role_id UUID NOT NULL,
	PRIMARY KEY (candidate_id, role_id),
	FOREIGN KEY (candidate_id) REFERENCES tcandidate(id) ON DELETE CASCADE,
	FOREIGN KEY (role_id) REFERENCES trole(id) ON DELETE CASCADE
);
