CREATE TABLE Activity (
    activityName VARCHAR(100) PRIMARY KEY,
    educativeLevel VARCHAR(50),
    timeSlot VARCHAR(50),
    maxAssistants NUMBER,
    neededMonitors NUMBER
);

CREATE TABLE Assistant (
    id NUMBER PRIMARY KEY,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    birthDate DATE,
    requireSpecialAttention CHAR(1)
);

CREATE TABLE Camp (
    campID NUMBER PRIMARY KEY,
    start DATE,
    end DATE,
    educativeLevel VARCHAR(50),
    capacity NUMBER,
    principalMonitorId NUMBER NULL,
    specialMonitorId NUMBER NULL,
    FOREIGN KEY (principalMonitorId) REFERENCES Monitor(id),
    FOREIGN KEY (specialMonitorId) REFERENCES Monitor(id)
);

CREATE TABLE CampActivity (
    campId NUMBER,
    activityName VARCHAR(100),
    PRIMARY KEY (campId, activityName),
    FOREIGN KEY (campId) REFERENCES Camp(campID),
    FOREIGN KEY (activityName) REFERENCES Activity(activityName)
);

CREATE TABLE Inscription (
    assistantId NUMBER,
    campId NUMBER,
    inscriptionDate DATE,
    price FLOAT,
    canBeCanceled CHAR(1),
    isPartial CHAR(1),
    PRIMARY KEY (assistantId, campId),
    FOREIGN KEY (assistantId) REFERENCES Assistant(id),
    FOREIGN KEY (campId) REFERENCES Camp(campID)
);

CREATE TABLE MonitorActivity (
    monitorId NUMBER,
    activityName VARCHAR(100),
    PRIMARY KEY (monitorId, activityName),
    FOREIGN KEY (monitorId) REFERENCES Monitor(id),
    FOREIGN KEY (activityName) REFERENCES Activity(activityName)
);

CREATE TABLE Monitor (
    id NUMBER PRIMARY KEY,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    specialEducator CHAR(1)
);

CREATE TABLE User (
  email VARCHAR(100),
  password VARCHAR(100),
  role VARCHAR(20),
  PRIMARY KEY (email),
);

CREATE TABLE AssistantUser (
  userEmail VARCHAR(100),
  assistantId NUMBER UNIQUE,
  PRIMARY KEY (userEmail),
  FOREIGN KEY (userEmail) REFERENCES User(email),
  FOREIGN KEY (assistantId) REFERENCES Assistant(id),
);
