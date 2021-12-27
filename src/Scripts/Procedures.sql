DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_new_league`(IN league_id VARCHAR(36), IN league_name VARCHAR(32))
BEGIN
	START TRANSACTION;
	INSERT INTO leagues
		(`id`, `name`)
	VALUES 
		(league_id, league_name);
        
	SELECT 
		l.`id`,
        l.`name`
	FROM
		leagues l
	WHERE l.`id` = league_id;
    COMMIT;    
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_new_team`(IN team_id VARCHAR(36), IN team_name VARCHAR(32), IN league_id VARCHAR(36))
BEGIN
	START TRANSACTION;
		INSERT INTO teams
			(`id`, `name`, `league_id`)
		VALUES
			(team_id, team_name, league_id);

		SELECT
			t.`id`,
			t.`name`,
			t.`league_id`
		FROM
			teams t
		WHERE 
			t.`id` = team_id;
	COMMIT;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_team_by_id`(IN team_id VARCHAR(36))
BEGIN
	DELETE 
		FROM teams
	WHERE 
		`id` = team_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_all_leagues`()
BEGIN
	SELECT 
		l.`id`, l.`name`
	FROM
		`leagues` l;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_all_matches_cards`()
BEGIN
	SELECT 
		m.id,
		t1.name AS 'first_team',
		m.first_team_score,
		t2.name AS 'second_team',
		m.second_team_score,
		m.date
	FROM
		matches m
			LEFT JOIN
		teams t1 ON m.first_team_id = t1.id
			LEFT JOIN
		teams t2 ON m.second_team_id = t2.id
			LEFT JOIN
		leagues l ON t1.league_id = l.id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_all_teams`()
BEGIN
	SELECT 
		t.`id`,
		t.`name`, 
		t.`league_id`
	FROM
		teams t;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_all_teams_by_league_id`(IN league_id VARCHAR(36))
BEGIN
	SELECT 
		t.`id`,
		t.`name`,
        t.`league_id`
	FROM
		teams t
			LEFT JOIN
		leagues l ON t.`league_id` = l.`id`
	WHERE
		l.`id` = league_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_league_by_id`(IN league_id VARCHAR(36))
BEGIN
	SELECT 
		l.`id`, l.`name`
	FROM
		`leagues` l
	WHERE
		l.`id` = league_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_league_by_name`(IN league_name VARCHAR(32))
BEGIN
	SELECT 
		lg.`id`, lg.`name`
	FROM
		leagues lg
	WHERE
		lg.`name` = league_name;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_match_card_by_id`(IN match_id VARCHAR(36))
BEGIN
	SELECT 
		m.id,
		t1.name AS 'first_team',
		m.first_team_score,
		t2.name AS 'second_team',
		m.second_team_score,
		m.date
	FROM
		matches m
			LEFT JOIN
		teams t1 ON m.first_team_id = t1.id
			LEFT JOIN
		teams t2 ON m.second_team_id = t2.id
			LEFT JOIN
		leagues l ON t1.league_id = l.id
	WHERE
		m.id = match_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_match_card_by_team_id`(IN team_id VARCHAR(36))
BEGIN
	SELECT 
		m.id,
		t1.name AS 'first_team',
		m.first_team_score,
		t2.name AS 'second_team',
		m.second_team_score,
		m.date
	FROM
		matches m
			LEFT JOIN
		teams t1 ON m.first_team_id = t1.id
			LEFT JOIN
		teams t2 ON m.second_team_id = t2.id
	WHERE
		t1.id = team_id OR t2.id = team_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_matches_cards_by_year`(IN match_year VARCHAR(4))
BEGIN
	SELECT 
		m.id,
		t1.name AS 'first_team',
		m.first_team_score,
		t2.name AS 'second_team',
		m.second_team_score,
		m.date
	FROM
		matches m
			LEFT JOIN
		teams t1 ON m.first_team_id = t1.id
			LEFT JOIN
		teams t2 ON m.second_team_id = t2.id
			LEFT JOIN
		leagues l ON t1.league_id = l.id
	WHERE
		YEAR(m.date) = match_year;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_matches_cards_reports`()
BEGIN
	SELECT 
		t1.id,
		t1.name,
		COUNT(m.id) AS 'total_matches',
		SUM(m.first_team_score > m.second_team_score) AS 'total_wins',
		SUM(m.first_team_score < m.second_team_score) AS 'total_losses',
		SUM(m.first_team_score = m.second_team_score) AS 'total_draws'
	FROM
		matches m
			LEFT JOIN
		teams t1 ON m.first_team_id = t1.id
			LEFT JOIN
		teams t2 ON m.second_team_id = t2.id
	GROUP BY t1.id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_team_by_id`(IN team_id VARCHAR(36))
BEGIN
	SELECT 
		t.`id`,
		t.`name`,
		t.`league_id`
	FROM
		teams t
	WHERE 
		t.`id` = team_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_team_by_name`(IN team_name VARCHAR(32))
BEGIN
	SELECT 
		t.`id`,
		t.`name`, 
		t.`league_id`
	FROM
		teams t
	WHERE
		t.`name` = team_name;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_team_card_report_by_id`(IN team_id VARCHAR(36))
BEGIN
	WITH team_report AS (
	SELECT 
		m.id,
		CASE
			WHEN t1.id = team_id THEN t1.id
			ELSE t2.id
		END AS 'first_team',
		CASE
			WHEN t1.id != team_id THEN t1.id
			ELSE t2.id
		END AS 'second_team',
		CASE
			WHEN t1.id = team_id THEN m.first_team_score
			ELSE m.second_team_score
		END AS 'first_score',
		CASE
			WHEN t1.id != team_id THEN m.first_team_score
			ELSE m.second_team_score
		END AS 'second_score'
	FROM
		matches m
			LEFT JOIN
		teams t1 ON m.first_team_id = t1.id
			LEFT JOIN
		teams t2 ON m.second_team_id = t2.id
	WHERE
		t1.id = team_id
			OR t2.id = team_id)
		SELECT
		id,
		SUM(first_score > second_score) AS 'total_wins', 
		SUM(first_score < second_score) AS 'total_losses',  
		SUM(first_score = second_score) AS 'total_draws' 
		from team_report;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_teams_by_league_id`(IN league_id VARCHAR(36))
BEGIN
	SELECT 
		t.`id`, 
		t.`name`, 
		t.`league_id`
	FROM
		teams t
	WHERE
		t.`league_id` = league_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_teams_with_league_name`()
BEGIN
	SELECT 
		t.`id` AS 'id', 
		t.`name` AS 'team', 
		l.`name` AS 'league_name'
	FROM
		leagues l
			LEFT JOIN
		teams t ON l.`id` = t.`league_id`
	ORDER BY t.`name` ASC;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_total_teams_by_leagues`()
BEGIN
SELECT 
    l.id,
    l.name,
    COUNT(t.id) AS 'total_teams'
FROM
    leagues l
        LEFT JOIN
    teams t ON l.id = t.league_id
GROUP BY l.id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `remove_league_by_id`(IN league_id VARCHAR(36))
BEGIN
	DELETE FROM `leagues`
    WHERE `leagues`.`id` = league_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_league_name_byId`(IN league_id VARCHAR(36), IN league_name VARCHAR(32))
BEGIN
	START TRANSACTION;
		UPDATE leagues 
		SET 
			leagues.`name` = league_name
		WHERE
			leagues.`id` = league_id;
		
        SELECT 
			lg.`id`, lg.`name`
		FROM
			leagues lg
		WHERE
			lg.`id` = league_id;
	COMMIT;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_team_name_by_id`(IN team_name VARCHAR(32), IN team_id VARCHAR(36))
BEGIN
	START TRANSACTION;
		UPDATE teams 
		SET 
			teams.`name` = team_name
		WHERE
			teams.`id` = team_id;
		
		SELECT 
			t.`id`,
			t.`name`,
			t.`league_id`
		FROM 
			teams t
		WHERE t.`id` = team_id; 
    COMMIT;
END$$
DELIMITER ;
