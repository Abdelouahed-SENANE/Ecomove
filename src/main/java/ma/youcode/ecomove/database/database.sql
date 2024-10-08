
CREATE TYPE transportation_type as ENUM ('AIR' , 'TRAIN' , 'BUS');
CREATE TYPE partnership_status as ENUM ('ACTIVE' , 'INACTIVE' , 'SUSPENDED');
CREATE TYPE contract_status as ENUM ('ONGOING' , 'ENDED' , 'SUSPENDED');
CREATE TYPE offer_status as ENUM ('ACTIVE' , 'SUSPENDED' , 'EXPIRED');
CREATE TYPE discount_type as ENUM ('FIX_AMOUNT' , 'PERCENTAGE');
CREATE TYPE ticket_status as ENUM ('SOLD' , 'CANCELLED' , 'PENDING');

CREATE TABLE IF NOT EXISTS partners (
    partnerId VARCHAR(255) PRIMARY KEY,
    companyName VARCHAR(255),
    commercialContact VARCHAR(255),
    transportationType transportation_type,
    geographicZone VARCHAR(255),
    specialConditions TEXT,
    partnershipStatus partnership_status,
    creationDate TIMESTAMP
);


CREATE TABLE IF NOT EXISTS contracts (
    contractId VARCHAR(255) PRIMARY KEY,
    startingDate TIMESTAMP,
    endDate TIMESTAMP,
    specialRate DOUBLE PRECISION,
    agreementConditions VARCHAR(255),
    renewable BOOLEAN,
    contractStatus contract_status,
    partnerId VARCHAR(255),
    FOREIGN KEY (partnerId) REFERENCES partners(partnerId)
);

CREATE TABLE IF NOT EXISTS specialoffers (
    offerId VARCHAR(255) PRIMARY KEY,
    offerName VARCHAR(255),
    offerDescription TEXT,
    startingDate TIMESTAMP,
    endDate TIMESTAMP,
    discountType VARCHAR(255),
    discountValue DOUBLE PRECISION,
    conditions TEXT,
    offerStatus offer_status,
    contractId VARCHAR(255),
    FOREIGN KEY (contractId) REFERENCES contracts(contractId)
);


CREATE TABLE IF NOT EXISTS tickets (
    ticketId VARCHAR(255) PRIMARY KEY,
    transportaionType transportation_type,
    boughtFor DOUBLE PRECISION,
    sellingPrice DOUBLE PRECISION,
    soldAt TIMESTAMP,
    ticketStatus ticket_status,
    contractId VARCHAR(255),
    FOREIGN KEY (contractId) REFERENCES contracts(contractId)

);


CREATE table  if not exists preferences (
    id VARCHAR(255) PRIMARY KEY Not NULL,
    passengeremail VARCHAR(255) ,
    foreign key (passengeremail) references passengers(email),
    preferreddeparture VARCHAR(255),
    preferreddestination VARCHAR(255),
    transportationtype transportation_type,
    preferredTime Timestamp
    )