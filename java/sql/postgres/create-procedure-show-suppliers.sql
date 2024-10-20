SELECT 'Dropping procedure SHOW_SUPPLIERS' AS ' ';

drop procedure if exists SHOW_SUPPLIERS;

SELECT 'Changing delimiter to pipe' AS ' ';

delimiter |

SELECT 'Creating procedure SHOW_SUPPLIERS' AS ' '|

CREATE OR REPLACE FUNCTION show_suppliers()
RETURNS TABLE(sup_name TEXT, cof_name TEXT) AS $$
BEGIN
    RETURN QUERY
    SELECT SUPPLIERS.SUP_NAME, COFFEES.COF_NAME
    FROM SUPPLIERS
    JOIN COFFEES ON SUPPLIERS.SUP_ID = COFFEES.SUP_ID
    ORDER BY SUP_NAME;
END;
$$ LANGUAGE plpgsql;

delimiter ;
