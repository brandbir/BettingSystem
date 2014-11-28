-- CREATING USERS

CALL CREATE_USER('brandbir', '<password>', 'Brandon', 'Birmingham', '1994-1-18', 0, 2, '2014-8-6', 3);
CALL CREATE_USER('andrsamm', '<password>', 'Andrew', 'Sammut', '1994-1-18', 0, 2, '2014-8-6', 3);

-- CREATING BETS

CALL CREATE_BET('brandbir', 1, 2);
CALL CREATE_BET('brandbir', 1, 3);
CALL CREATE_BET('andrsamm', 2, 4);
CALL CREATE_BET('andrsamm', 2, 4);
CALL CREATE_BET('andrsamm', 3, 5);

-- SELECTING
call SELECT_USER('andrsamm');
call SELECT_USER_BETS('brandbir');

