SELECT 'Dropping procedure SHOW_SUPPLIERS' AS ' ';
DROP PROCEDURE IF EXISTS SHOW_SUPPLIERS;

SELECT 'Dropping procedure GET_SUPPLIER_OF_COFFEE' AS ' ';
DROP PROCEDURE IF EXISTS GET_SUPPLIER_OF_COFFEE;

SELECT 'Dropping procedure RAISE_PRICE' AS ' ';
DROP PROCEDURE IF EXISTS RAISE_PRICE;

SELECT 'Creating procedure SHOW_SUPPLIERS' AS ' ';
CREATE OR REPLACE FUNCTION SHOW_SUPPLIERS()
  RETURNS TABLE(SUP_NAME varchar, COF_NAME varchar) AS $$
BEGIN
    RETURN QUERY
    SELECT SUPPLIERS.SUP_NAME, COFFEES.COF_NAME
    FROM SUPPLIERS, COFFEES
    WHERE SUPPLIERS.SUP_ID = COFFEES.SUP_ID
    ORDER BY SUP_NAME;
END;
$$ LANGUAGE plpgsql;

SELECT 'Creating procedure GET_SUPPLIER_OF_COFFEE' AS ' ';
CREATE OR REPLACE FUNCTION GET_SUPPLIER_OF_COFFEE(coffeeName varchar, OUT supplierName varchar)
  AS $$
BEGIN
    SELECT SUPPLIERS.SUP_NAME INTO supplierName
    FROM SUPPLIERS, COFFEES
    WHERE SUPPLIERS.SUP_ID = COFFEES.SUP_ID
    AND coffeeName = COFFEES.COF_NAME;
    RETURN supplierName;
END;
$$ LANGUAGE plpgsql;

SELECT 'Creating procedure RAISE_PRICE' AS ' ';
CREATE OR REPLACE FUNCTION RAISE_PRICE(coffeeName varchar, maximumPercentage float, newPrice numeric)
  RETURNS numeric AS $$
DECLARE
    maximumNewPrice numeric;
    oldPrice numeric;
BEGIN
    SELECT COFFEES.PRICE INTO oldPrice
    FROM COFFEES
    WHERE COFFEES.COF_NAME = coffeeName;

    maximumNewPrice := oldPrice * (1 + maximumPercentage);
    IF newPrice > maximumNewPrice THEN
        newPrice := maximumNewPrice;
    END IF;

    IF newPrice <= oldPrice THEN
        newPrice := oldPrice;
        RETURN newPrice;
    END IF;

    UPDATE COFFEES
    SET COFFEES.PRICE = newPrice
    WHERE COFFEES.COF_NAME = coffeeName;

    RETURN newPrice;
END;
$$ LANGUAGE plpgsql;

SELECT 'Listing stored procedures ...' AS ' ';
SHOW PROCEDURE STATUS;
