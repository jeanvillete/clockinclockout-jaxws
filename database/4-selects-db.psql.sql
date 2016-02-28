-- "select" statement showing how it is possible to figure out day by day each balance.
SELECT dbl.* FROM dayly_balance dbl;

-- select statement for total balance.
SELECT ( SUM( dbl.interval_day ) + ( SELECT prfl.initial_balance from profile prfl where prfl.description = 'profile default' ) ) AS total_balance
FROM dayly_balance dbl;

-- in order to discover the total balance until some specific month, the select statement is the same as the above but with a date_day as parameter.
SELECT ( SUM( dbl.interval_day ) + ( SELECT prfl.initial_balance from profile prfl where prfl.description = 'profile default' ) ) AS total_balance
FROM dayly_balance dbl
WHERE dbl.date_day <= '2015-08-27';

-- figure out dayly balance considering also 'manual entering'
SELECT dd.id AS id_day,
  dd.date,
  dd.expected_hours,
  dayly_balance.total_hours_day,
  dayly_balance.total_hours_day - dd.expected_hours AS interval_day,
  dayly_balance.record_type,
  dd.notes
FROM (
  SELECT d.id AS id_day, SUM( clk.clockout - clk.clockin ) AS total_hours_day, 'clockinclockout' AS record_type
  FROM day d RIGHT JOIN clockinclockout clk ON d.id = clk.id_day
  WHERE clk.clockin IS NOT NULL AND clk.clockout IS NOT NULL
  GROUP BY d.id
  UNION
  SELECT d.id AS id_day, me.time_interval AS total_hours_day, 'manual_entering' AS record_type
  FROM day d RIGHT JOIN manual_entering me ON d.id = me.id_day
) AS dayly_balance
LEFT JOIN day dd ON dayly_balance.id_day = dd.id
ORDER BY dd.date;
