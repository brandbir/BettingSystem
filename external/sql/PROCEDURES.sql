/*
 * This procedure is used to insert a user record in BETTING.USERS table. This procedure insert the compulsory fields of a user.
 * This can be executed by calling the following statement: 
 * 
 * CALL CREATE_USER('<username>', '<password>', '<name>', '<surname>', '<DOB>', '<premium>', <creditcard>, '<expirydate>', <cvv>);
 */
CREATE PROCEDURE CREATE_USER (IN usrname VARCHAR (50), IN pass VARCHAR (50), IN nm VARCHAR (30), IN sm VARCHAR (30), IN dob DATE, 
                              IN prem CHAR (1), IN creditcard BIGINT, IN expiryDate DATE, IN  creditcardCVV SMALLINT )
LANGUAGE SQL
BEGIN
 INSERT INTO BETTING.USERS(USERNAME, PASSWORD, NAME, SURNAME, DOB, PREMIUM, CREDIT_CARD, EXPIRY_DATE, CVV)
  VALUES (usrname, pass, nm, sm, dob, prem, creditcard, expiryDate, creditcardCVV);
END

/*
 * This procedure is used to set the LOCK_TIME of a user. This field shows the exact time at which
 * the user's profile is locked for 5 minutes. The account is locked if 3 invalid passwords were entered.
 * This can be executed by calling the following statement:
 * 
 * CALL SET_LOCK_TIME('<username>')
 */
CREATE PROCEDURE SET_LOCK_TIME (IN usrname VARCHAR (50))
LANGUAGE SQL
BEGIN
 UPDATE BETTING.USERS
 SET LOCK_TIME = CURRENT TIMESTAMP
 WHERE USERNAME = usrname;
END

/*
 * This procedure is used to set the INVALID_PASS_COUNT of a user. This field shows the number
 * of consecutive invalid passwords the user has entered while trying to login to his account.
 * This is executed by the following statement:
 * 
 * CALL SET_INVALID_PASS_COUNT('<username>', <count>)
 */
CREATE PROCEDURE SET_INVALID_PASS_COUNT (IN usrname VARCHAR (50), IN passcount SMALLINT)
LANGUAGE SQL
BEGIN
 UPDATE BETTING.USERS
 SET INVALID_PASS_COUNT = passcount
 WHERE USERNAME = usrname;
END


/*
 * This procedure is used to create and add a bet in BETTING.BETS table.
 * This is executed by the following statement:
 * 
 * CREATE_BET('<usrname>', riskLevel, amount)
 * 
 */
CREATE PROCEDURE CREATE_BET (IN usrname VARCHAR (50), IN riskLevel SMALLINT, IN amnt SMALLINT)
LANGUAGE SQL
BEGIN
 INSERT INTO BETTING.BETS(USERNAME, RISK_LEVEL, AMOUNT)
  VALUES (usrname, riskLevel, amnt);
END


/**
 * This procedure is used to select and return a particular user from BETTING.USERS specified
 * by the username. This procedure can be executed by:
 * 
 * CALL SELECT_USER('<username>');
 */
CREATE OR REPLACE PROCEDURE SELECT_USER (IN usrname VARCHAR (50)) 
 LANGUAGE SQL 
 DYNAMIC RESULT SETS 1 
 BEGIN 
  DECLARE C1 CURSOR WITH RETURN TO CLIENT FOR
   SELECT * FROM BETTING.USERS WHERE USERNAME = usrname;
 OPEN C1;
END

/**
 * This procedure is used to select and return the list of bets placed by a
 * specific user specified by the username. This procedure can be executed by:
 * 
 * CALL SELECT_USER_BETS('<username>');
 */
CREATE OR REPLACE PROCEDURE SELECT_USER_BETS (IN usrname VARCHAR (50)) 
 LANGUAGE SQL 
 DYNAMIC RESULT SETS 1 
 BEGIN 
  DECLARE C1 CURSOR WITH RETURN TO CLIENT FOR
   SELECT * FROM BETTING.BETS WHERE USERNAME = usrname;
 OPEN C1;
END

SELECT * from BETTING.BETS
