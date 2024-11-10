DELIMITER $$

CREATE PROCEDURE getMostPurchasedProduct()
BEGIN
    SELECT p.ID AS product_id, p.NAME AS product_name, p.PRICE AS product_price, 
           p.DESCRIPTION AS product_description, 
           SUM(s.QUANTITY) AS total_units_sold
    FROM PRODUCT p
    INNER JOIN SALES s ON p.ID = s.PRODUCT_ID
    GROUP BY p.ID
    ORDER BY total_units_sold DESC
    LIMIT 1;
END$$

DELIMITER ;
