DELIMITER $$

CREATE PROCEDURE getTopPurchasingClient()
BEGIN
    SELECT c.ID AS client_id, c.NAME AS client_name, c.SURNAME AS client_surname, c.EMAIL AS client_email, 
           SUM(s.QUANTITY) AS total_units_purchased
    FROM CLIENT c
    INNER JOIN SALES s ON c.ID = s.CLIENT_ID
    GROUP BY c.ID
    ORDER BY total_units_purchased DESC
    LIMIT 1;
END$$

DELIMITER ;
